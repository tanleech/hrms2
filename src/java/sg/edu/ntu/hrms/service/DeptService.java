/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.DeptDAO;
import sg.edu.ntu.hrms.dao.UserDAO;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;

/**
 *
 * @author michael-PC
 */
@Service("deptService")

public class DeptService extends BaseService{
    @Autowired
    private DeptDAO deptDAO;
    @Autowired
    private UserDAO userDAO;
    public void updateDept(String oldname, String dept)throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            deptDAO.setSession(session);
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setDescription(dept);
            if(deptDAO.getDepartment(dept)==null)
            {
                deptDAO.updateDept(oldname, dept);
                txn.commit();
            }
            else
            {

                throw new Exception("Duplicate department name.");
                //duplicate name
                /*
                request.setAttribute("error", "Duplicate department name.");
                if(action!=null&&action.equals("U"))
                {
                   page="/editDeptName.jsp?dept="+oldName;
                }
                */
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
            txn.rollback();
            throw ex;
        }

    }

    
    public void addDept(String dept)throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            deptDAO.setSession(session);
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setDescription(dept);
            if(deptDAO.getDepartment(dept)==null)
            {
                deptDAO.addDept(deptDTO);
                txn.commit();
            }
            else
            {

                throw new Exception("Duplicate department name.");
                //duplicate name
                /*
                request.setAttribute("error", "Duplicate department name.");
                if(action!=null&&action.equals("U"))
                {
                   page="/editDeptName.jsp?dept="+oldName;
                }
                */
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
            txn.rollback();
            throw ex;
        }

    }
    public void unassignEmp(String userId, String dept)
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            deptDAO.setSession(session);
        
            DeptDTO deptDto = deptDAO.getDepartment(dept);
            deptDAO.unassignEmployee(Integer.parseInt(userId), deptDto.getId());
            txn.commit();
        //page = "/deptEdit?action=U&dept="+dept;
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
        }finally{
            session.close();
        }

    }

    public void assignEmp(String empLogin, String dept)throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            userDAO.setSession(session);
            deptDAO.setSession(session);
            UserDTO empDto = userDAO.getUser(empLogin);
            //get dept name
            //String deptName = request.getParameter("name");
            DeptDTO deptDto = deptDAO.getDepartment(dept);
            if(deptDAO.getUserDept(empDto.getId(), deptDto.getId())!=null)
            {
                //request.setAttribute("error", "Employee already assigned.");
                throw new Exception("Employee already assigned.");
            }
            else
            {
                
                if(deptDAO.updateEmployee(empDto.getId(), deptDto.getId()) <=0)
                {    
                  System.out.println("Nothing to update");
                  deptDAO.assignEmployee(empDto, deptDto);
                  //page = "/deptEdit?action=U&dept="+dept;
                }  
                txn.commit();
            }
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
            throw ex;
        }
        finally{
            session.close();
        }
            
            
        
    }
    public void assignMgr(String mgr, String dept)
    {
        Session session = sessionFactory.openSession();
        Transaction txn1 = null;
        Transaction txn2 = null;
        try{ 
            txn1 = session.beginTransaction();
            userDAO.setSession(session);
            deptDAO.setSession(session);
            UserDTO empDto = userDAO.getUser(mgr);
            empDto.setIsManager(true);
            //get dept name
            DeptDTO deptDto = deptDAO.getDepartment(dept);
            deptDAO.unassignManager(deptDto.getId());
            txn1.commit();
            
            txn2 = session.beginTransaction();
            int result = deptDAO.assignManager(empDto.getId(),deptDto.getId());
            if(result==0)
                deptDAO.assignEmployee(empDto, deptDto);
            txn2.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn1.rollback();
            txn2.rollback();
        }
        finally{
            session.close();
        }
    }
    public Map<String,Object> getDepartmentWithUsers(String dept)
    {
        Session session = sessionFactory.openSession();
        Map map = new HashMap();
        try{
            session.beginTransaction();
            userDAO.setSession(session);
            deptDAO.setSession(session);

            DeptDTO deptData = deptDAO.getDepartment(dept);
            //retrieve the manager
            List<UserDeptDTO> employees = new ArrayList<UserDeptDTO>(deptData.getEmployees());
            //employees = deptData.getEmployees();
            boolean found=false;
            int i=0;
            while(!found && employees!=null && !employees.isEmpty() &&i<employees.size() )
            {
                UserDeptDTO emp = employees.get(i);
                if(emp.getManager()!=null && emp.getManager().equals("Y"))
                {
                    found=true;
                    //request.setAttribute("manager", emp );
                    //System.out.println("mg: "+emp.getUser().getName());
                    map.put("manager",emp);
                    employees.remove(i);
                }
                i++;
            }
            List<UserDTO> userList = new BeanHelper().getAllUsers(userDAO);
            //request.setAttribute("usrList", userList);
            map.put("usrList", userList);
            //request.setAttribute("employeeList", employees);
            map.put("employeeList",employees);
            //request.setAttribute("dept", dept);
            //page="/deptEdit.jsp";
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            session.close();
        }
        
        
        return map;

    }
}
