/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author michael-PC
 */
@Service("leaveTxnService")
public class LeaveTxnService extends BaseService{
    
    public void list()
    {
       /*
       List<LeaveTxnDTO> txnList = null;
       HashMap map = new BeanHelper().getUserTab(userBean);
       HashMap accessTab = (HashMap)request.getSession().getAttribute("access");
       UserDTO userDTO = (UserDTO)request.getSession().getAttribute("User");
       AccessDTO access = (AccessDTO)accessTab.get("Leave");
       int acr = access.getAccess();
       System.out.println("acr: "+acr);
       if(acr==1)
       {
           txnList = leaveBean.getLeaveRecords(userDTO.getId());
           if(userDTO.isIsManager())
           {
                txnList.addAll(leaveBean.getTxnForApprover(userDTO.getId()));
                request.setAttribute("isManager", "Y");
                        
           }
       }
       else if (acr==2)
       {
           txnList = leaveBean.getAllTxn();
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
            request.setAttribute("leaveTxnlist", txnList);
            page="/leaveList.jsp";

        */
    }
    
}
