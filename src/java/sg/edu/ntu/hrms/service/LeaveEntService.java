/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.LeaveDAO;
import sg.edu.ntu.hrms.dao.UserDAO;
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.web.action.Utility;

/**
 *
 * @author michael-PC
 */
@Service("leaveEntService")
public class LeaveEntService extends BaseService{
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LeaveDAO leaveDAO;
    public void unassign(int entId, int userId)
    {
        Session session = sessionFactory.openSession();
        Transaction txn = session.beginTransaction();
        leaveDAO.setSession(session);
        try
        {
            leaveDAO.deleteLeaveEnt(entId,userId);
            txn.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            txn.rollback();
        }
    }
    public void assign(String login, String typeIdStr, double days)
    {
 
        Session session = sessionFactory.openSession();
        Transaction txn = session.beginTransaction();
        userDAO.setSession(session);
        leaveDAO.setSession(session);
        try
        {   
            int typeId = Integer.parseInt(typeIdStr);
            
            LeaveTypeDTO typeDTO = leaveDAO.getLeaveSetting(typeId);

            UserDTO user = userDAO.getUser(login);

            LeaveEntDTO entDto = new LeaveEntDTO();
            entDto.setCurrent(days);
            entDto.setCarriedOver(0);
            entDto.setMax(0);
            entDto.setBalance(days);
            entDto.setLeaveType(typeDTO);
            entDto.setUser(user);
            //leaveBean.setAuthor(loginUser);
            leaveDAO.addLeaveEnt(entDto);
            txn.commit();
            System.out.println("commit in leaveEnt Assign");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            txn.rollback();
        }
        finally
        {
            session.close();
        }
 
    }
    public LeaveTypeDTO getLeaveSetting(int id)
    {
        LeaveTypeDTO result = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        leaveDAO.setSession(session);
        try
        {
          result = leaveDAO.getLeaveSetting(id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return result;

    }
    public Map<String, Object> getLeaveEntAdd(String login)
    {
        HashMap<String,Object> hash = new HashMap();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        userDAO.setSession(session);
        leaveDAO.setSession(session);
        try
        {
          UserDTO user = userDAO.getUser(login);
          hash.put("user",user);
          List<LeaveTypeDTO>typeList = leaveDAO.getAllLeaveSettings();
          hash.put("typeList", typeList);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return hash;
    }        
    public Map<String,Object> getLeaveEntitlement(String login)
    {
        HashMap<String,Object> hash = new HashMap();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        userDAO.setSession(session);
        leaveDAO.setSession(session);
        try
        {
        //String login = request.getParameter("id");
            UserDTO user = userDAO.getUser(login);
            List<LeaveEntDTO> entList = leaveDAO.getLeaveEntList(login);
            boolean found = false;
            int i =0;
            LeaveEntDTO annualEnt=null;
            while(!found && i < entList.size())
            {
                LeaveEntDTO ent = entList.get(i);
                if(ent.getLeaveType().getDescription().equals("Annual"))
                {
                    found=true;
                    //user.setLeaveEnt(entList);
                    annualEnt = ent;
                    //request.setAttribute("entAnnual", ent);
                    hash.put("entAnnual", ent);
                }
                     i++;
            }
            //compute Annual Accured
            Date now = new Date();
            Date begin = Utility.getYearBeginTime();
            double days = Utility.computeDaysBetween(begin, now);
            System.out.println("days between: "+days);
            double accured = (days/365.0) * annualEnt.getCurrent();
                 
            //request.setAttribute("typeList", leaveBean.getAllLeaveSettings());
            hash.put("typeList", leaveDAO.getAllLeaveSettings());
            //request.setAttribute("user", user);
            hash.put("user", user);
            //request.setAttribute("entList", entList);
            hash.put("entList", entList);
            //request.setAttribute("accured", accured);
            hash.put("accured", new Double(accured));
                 
            //page="/employeeLeaveDetl.jsp";
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return hash;

    }
    
    
}
