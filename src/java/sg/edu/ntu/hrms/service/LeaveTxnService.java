/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.LeaveDAO;
import sg.edu.ntu.hrms.dao.UserDAO;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.LeaveTxnDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.StatusDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
@Service("leaveTxnService")
public class LeaveTxnService extends BaseService{
    @Autowired
    LeaveDAO leaveDAO;
    @Autowired
    UserDAO userDAO;
    public Map<String,Object> list(HashMap accessTab, UserDTO userDTO)
    {
        Session session = sessionFactory.openSession();
        HashMap<String,Object> resultMap = new HashMap<String,Object>(); 
        try{
            session.beginTransaction();
            userDAO.setSession(session);
            leaveDAO.setSession(session);
            
            List<LeaveTxnDTO> txnList = null;
            HashMap map = new BeanHelper().getUserTab(userDAO);
            //HashMap accessTab = (HashMap)request.getSession().getAttribute("access");
            //UserDTO userDTO = (UserDTO)request.getSession().getAttribute("User");
            AccessDTO access = (AccessDTO)accessTab.get("Leave");
            int acr = access.getAccess();
            System.out.println("acr: "+acr);
            if(acr==1)
            {
                txnList = leaveDAO.getLeaveRecords(userDTO.getId());
                if(userDTO.isIsManager())
                {
                     txnList.addAll(leaveDAO.getTxnForApprover(userDTO.getId()));
                     resultMap.put("isManager", "Y");
                     //request.setAttribute("isManager", "Y");

                }
            }
            else if (acr==2)
            {
                txnList = leaveDAO.getAllTxn();
                //request.setAttribute("isManager", "Y");
            }   
            for(int i=0;i<txnList.size();i++)
            {
                         LeaveTxnDTO txn = txnList.get(i);
                         UserDTO mgr = (UserDTO)map.get(txn.getUser().getApprover());
                         System.out.println("in list aprover: "+txn.getUser().getApprover());
                         //UserDTO mgr = userBean.getUserFromId(txn.getUser().getApprover());
                         if(mgr!=null)
                         {
                           txn.getUser().setApproverName(mgr.getName());
                           //txn.getUser().setApproverEmail(mgr.getEmail());
                         }
            }
                 //request.setAttribute("leaveTxnlist", txnList);
                 resultMap.put("leaveTxnList", txnList);
            //page="/leaveList.jsp";
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }
        return resultMap;
        
    }
    
    public void approve(String txnId){
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            leaveDAO.setSession(session);
            StatusDTO status = leaveDAO.getStatus("approved");
            leaveDAO.approveLeave(Integer.parseInt(txnId), status.getId());
            txn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
        }finally{
            session.close();
        }
    }
    public void reject(String txnId, String typeId, String userId, String daysStr){
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
                txn = session.beginTransaction();
                leaveDAO.setSession(session);
                userDAO.setSession(session);
                UserDTO user = userDAO.getUserFromId(Integer.parseInt(userId));
                StatusDTO status = leaveDAO.getStatus("rejected");
                leaveDAO.approveLeave(Integer.parseInt(txnId), status.getId());
                //add on to the leave balance
                //String userId = request.getParameter("userId");
                double days = Double.parseDouble(daysStr);
                
                //LeaveEntDTO entDTO = leaveBean.getLeaveEnt(Integer.parseInt(typeId), Integer.parseInt(userId));
                LeaveTypeDTO typeDTO = new LeaveTypeDTO();
                typeDTO.setId(Integer.parseInt(typeId));
                
                UserDTO userDTO = new UserDTO();
                userDTO.setId(Integer.parseInt(userId));
                double bal = leaveDAO.getLeaveBalance(typeDTO, userDTO);

                leaveDAO.updateLeaveEnt(Integer.parseInt(typeId),Integer.parseInt(userId) , bal+days);
                txn.commit();
            }catch(Exception ex){
                ex.printStackTrace();
                txn.rollback();
            }finally{
              session.close();
            }
        }
    
    public double getBalance(UserDTO userDTO, LeaveTypeDTO typeDTO)
    {
        Session session = sessionFactory.openSession();
        double balance=0;
        try{
            session.beginTransaction();
            userDAO.setSession(session);
            leaveDAO.setSession(session);
        
                UserDTO mgrDTO = userDAO.getUserFromId(userDTO.getApprover());
                if(mgrDTO!=null)
                {
                    userDTO.setApproverName(mgrDTO.getApproverName());
                }
                balance = leaveDAO.getLeaveBalance(typeDTO, userDTO);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }
        return balance;        
    }
    
    public void applyLeave(LeaveTxnDTO leaveTxn, UserDTO user, String lvTypeId){
        
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            userDAO.setSession(session);
            leaveDAO.setSession(session);
        
            LeaveTypeDTO typeDTO = leaveDAO.getLeaveSetting(Integer.parseInt(lvTypeId));
            leaveTxn.setLeaveType(typeDTO);
        
            double bal = leaveDAO.getLeaveBalance(typeDTO, user);
        
            StatusDTO statusDTO = leaveDAO.getStatus("pending");
        
            leaveTxn.setStatus(statusDTO);
            leaveTxn.setUser(user);
        
            leaveDAO.applyLeave(leaveTxn);
            leaveDAO.updateLeaveEnt(typeDTO.getId(), user.getId(), bal-leaveTxn.getDays());
            
            txn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
        }finally{
            session.close();
        }      
    }
    
}
