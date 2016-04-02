/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.AccessDAO;

import sg.edu.ntu.hrms.dao.DeptDAO;
import sg.edu.ntu.hrms.dao.LeaveDAO;
import sg.edu.ntu.hrms.dao.TitleDAO;
import sg.edu.ntu.hrms.dao.UserDAO;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;
import sg.edu.ntu.hrms.dto.TitleDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;
import sg.edu.ntu.hrms.dto.UserRoleDTO;
import sg.edu.ntu.hrms.web.action.Utility;

/**
 *
 * @author michael-PC
 */
@Service("employeeEditService")
public class EmployeeEditService extends BaseService {
    @Autowired
    private DeptDAO deptDAO;
    @Autowired
    private TitleDAO titleDAO;
    @Autowired
    private AccessDAO accessDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LeaveDAO leaveDAO;
    
    
    
    public List<DeptDTO> getAllDepts()
    {
        List<DeptDTO> results = null;    
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try
        {
            deptDAO.setSession(session);
            results = deptDAO.getAllDepts();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return results;
    }
    
    public List<TitleDTO>getAllTitles()
    {
        List<TitleDTO> results = null;    
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        try
        {
            titleDAO.setSession(session);
            results = titleDAO.getAllTitles();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally{
            session.close();
        }
        return results;
    }
    
    public List<RoleDTO>getAllRoles()
    {
        List<RoleDTO> results = null;    
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            accessDAO.setSession(session);
            results = accessDAO.getAllRoles();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return results;
    }

    public List<UserDTO>getAllUsers()
    {
        List<UserDTO> results = null;    
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            userDAO.setSession(session);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

            Date from = formatter.parse("01/01/0000");
            Date to   = formatter.parse("12/31/9999");

            results = userDAO.getAllUsers(from, to);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return results;
    }
    
    public UserDTO getUser(String login)
    {
        UserDTO result = null;    
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Map hash = new HashMap();
        try
        {
            userDAO.setSession(session);
            leaveDAO.setSession(session);
            result = userDAO.getUser(login);
            List<LeaveEntDTO> entList = leaveDAO.getLeaveEntList(login);
            List<LeaveEntDTO> shortList = new ArrayList<LeaveEntDTO>();
            boolean found = false;
            int i =0;
            while(!found && i < entList.size())
            {
                
                     LeaveEntDTO ent = entList.get(i);
                     if(ent.getLeaveType().getDescription().equals("Annual"))
                     {
                         found=true;
                         //user.setLeaveEnt(entList);
                         //annualEnt = ent;
                         //ent.setBalance(entitlement);
                         //hash.put("entAnnual", ent);
                         shortList.add(ent);
                     }
                     i++;
            }
            result.setLeaveEnt(shortList);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return result;
    }

    
    private LeaveTypeDTO getLeaveType(String leaveTypeStr, Session session)throws Exception
    {
        LeaveTypeDTO result = null;    
        leaveDAO.setSession(session);
        result = leaveDAO.getLeaveType(leaveTypeStr);
        return result;
    }
    
    public void addEmployee(UserDTO emp)throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction txn  = session.beginTransaction();
        try
        {
            //txn.begin();
            userDAO.setSession(session);
            deptDAO.setSession(session);
            leaveDAO.setSession(session);
            if(userDAO.getUser(emp.getLogin())!=null)
            {
                throw new Exception("Duplicate user is not allowed");
            }
            else
            {
                //emp.getLeaveEnt().get(0).setLeaveType(this.getLeaveType("Annual", session));
                UserDeptDTO deptDto = emp.getDept();
                UserRoleDTO userRoleDto = emp.getRole();
                //getting annual leave ent
                LeaveEntDTO entDto = emp.getLeaveEnt().get(0);
                emp.setDept(null);
                emp.setRole(null);
                emp.setLeaveEnt(null);
                //userDto.setAuthor(loginUser);

                userDAO.createUser(emp);

                deptDAO.assignEmployee(emp, deptDto.getDept());
                userDAO.assignRole(emp,userRoleDto.getRole());
                double ent = computeLeave(emp.getDateJoin(), entDto.getCurrent());
                entDto.setUser(emp);
                entDto.setBalance(ent);
                entDto.setLeaveType(getLeaveType("Annual", session));
                leaveDAO.addLeaveEnt(entDto);
                txn.commit();
            }
        }
        catch(Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
            throw ex;
        }
        finally
        {
            System.out.println("session closing");
            session.close();
        }
    }
    public void updateEmployee(UserDTO userDto)
    {
        //UserDTO userDto = prepare(request);
        //int id = Integer.parseInt(request.getParameter("userId"));
        Session session = sessionFactory.openSession();
        Transaction txn  = session.beginTransaction();
        try
        {
            userDAO.setSession(session);
            leaveDAO.setSession(session);
            deptDAO.setSession(session);

            UserDeptDTO deptDto = userDto.getDept();
            UserRoleDTO userRoleDto = userDto.getRole();
            //getting annual leave ent
            LeaveEntDTO entDto = userDto.getLeaveEnt().get(0);
            //get annual leave type
            entDto.setLeaveType(getLeaveType("Annual", session));
            userDto.setDept(null);
            userDto.setRole(null);
            userDto.setLeaveEnt(null);
            //update user
            //System.out.println("LoginUSer:"+loginUser);
            //userDto.setAuthor(loginUser);
            //userBean.setAuthor(loginUser);
            System.out.printf("user name: "+userDto.getName());
            userDAO.updateUser(userDto);
            userDAO.updateRole(userDto.getId(), userRoleDto.getRole().getId());
            //update dept
            int res = deptDAO.updateEmployee(userDto.getId(), deptDto.getDept().getId());
            if(res==0)
            {
                deptDAO.assignEmployee(userDto, deptDto.getDept());
            }
           leaveDAO.updateLeaveEntitlement(entDto,userDto.getId());
           txn.commit();
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
    
     private double computeLeave(Date joinDate, double ent )
    {
                                 //compute
        Date end = Utility.getYearEndTime();
        //Date begin = user.getDateJoin();
        double days = Utility.computeDaysBetween(joinDate, end);
        System.out.println("days between: "+days);
        double entitlement = 0;
        if(days>=365)
        {
            entitlement = ent;
        }
        else
        {
            entitlement = (days/365.0) * ent;
        }
        return entitlement;
    }

}
