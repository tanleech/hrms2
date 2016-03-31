/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;

/**
 *
 * @author michael-PC
 */
@Repository
public class DeptDAOImpl extends BaseDAOImpl implements DeptDAO {

    @Override
    @Transactional
    public List<DeptDTO> getAllDepts() throws Exception{
        List results=null;
        Session session=null;
        session = getSession();
        results =  session.createQuery("SELECT DISTINCT dept FROM sg.edu.ntu.hrms.dto.DeptDTO dept left join fetch dept.employees").list();
        return results;
    }

    @Override
    @Transactional
    public void addDept(DeptDTO deptDTO) throws Exception{
        java.util.Date current = new java.util.Date();
        deptDTO.setCreated(current);
        deptDTO.setModified(current);
        Session session = null;
        session =  getSession();
        session.persist(deptDTO);
    }

    @Override
    @Transactional
    public DeptDTO getDepartment(String deptDescr) throws Exception {
        Session session = null;
        DeptDTO deptData = null;
        session =  getSession();
        String deptl = "SELECT DISTINCT dept FROM sg.edu.ntu.hrms.dto.DeptDTO dept left join fetch dept.employees WHERE dept.description = :descr";
        Query deptQuery = session.createQuery(deptl);
        deptQuery.setParameter("descr", deptDescr);
        List deptResults = deptQuery.list();
        if(deptResults!=null && !deptResults.isEmpty())
        {
           deptData = (DeptDTO) deptResults.get(0);
        }
        return deptData;
    }

    @Override
    @Transactional
    public UserDeptDTO getUserDept(int userId, int deptId) throws Exception {
        Session session = null;
        UserDeptDTO data = null;
        session =  getSession();
        String hql = "FROM sg.edu.ntu.hrms.dto.UserDeptDTO WHERE User_id=:userId AND Dept_id=:deptId";
        Query qry = session.createQuery(hql);
        qry.setParameter("userId", userId);
        qry.setParameter("deptId", deptId);
           
        List results = qry.list();
           
        if(results!=null && !results.isEmpty())
        {
            data = (UserDeptDTO) results.get(0);
        }
            
        return data;
    }

    @Override
    @Transactional
    public void unassignManager(int deptId) throws Exception{
        Session session = null;
        session =  getSession();
        String hql = "UPDATE sg.edu.ntu.hrms.dto.UserDeptDTO userDept SET userDept.manager='' WHERE userDept.dept.id=:deptId AND manager = 'Y'";
        Query qry = session.createQuery(hql);
        qry.setParameter("deptId", deptId);
        qry.executeUpdate();
    }

    @Override
    @Transactional
    public void assignEmployee(UserDTO userDTO, DeptDTO deptDTO) throws Exception{
        java.util.Date current = new java.util.Date();
        Session session = null;
        session =  getSession();
        UserDeptDTO userDept = new UserDeptDTO();
        if(userDTO.isIsManager())
        {
            userDept.setManager("Y");
        }
        userDept.setCreated(current);
        userDept.setModified(current);
        userDept.setDept(deptDTO);
        userDept.setUser(userDTO);
        session.persist(userDept);
    }

    @Override
    @Transactional
    public void unassignEmployee(int userId, int deptId) throws Exception {
        Session session = null;
        session =  getSession();
        String hql = "DELETE FROM sg.edu.ntu.hrms.dto.UserDeptDTO WHERE Dept_id=:deptId AND User_id = :userId ";
        Query qry = session.createQuery(hql);
        qry.setParameter("userId",userId);
        qry.setParameter("deptId", deptId);
        qry.executeUpdate();
    }

    @Override
    @Transactional
    public int assignManager(int userId, int deptId) throws Exception{
        Session session = null;
        int result=-1;
        session =  getSession();
        String hql = "UPDATE sg.edu.ntu.hrms.dto.UserDeptDTO userDept SET userDept.manager='Y' WHERE userDept.dept.id=:deptId AND userDept.user.id = :userId ";
        Query qry = session.createQuery(hql);
        qry.setParameter("deptId", deptId);
        qry.setParameter("userId", userId);
        result = qry.executeUpdate();
        return result;
    }

    @Override
    @Transactional
    public int updateEmployee(int userId, int deptId) throws Exception{
        Session session = null;
        int result=0;
        session =  getSession();
        //retrieve the full data from db.
        //retrieve from UserDept
        String hql = "UPDATE sg.edu.ntu.hrms.dto.UserDeptDTO userDept SET userDept.dept.id=:deptId WHERE userDept.user.id = :userId ";
        Query qry = session.createQuery(hql);
        System.out.println("deptId: "+deptId);
        System.out.println("userId: "+userId);
        qry.setParameter("deptId", deptId);
        qry.setParameter("userId", userId);
        result = qry.executeUpdate();
        return result;
    }

    @Override
    @Transactional
    public int updateDept(String oldName, String newName) throws Exception {
        Session session = null;
        int result=0;
        session =  getSession();
        String hql = "UPDATE sg.edu.ntu.hrms.dto.DeptDTO dept SET dept.description=:descr WHERE dept.description = :oldDescr ";
        Query qry = session.createQuery(hql);
        System.out.println("old descr: "+oldName);
        System.out.println("new descr: "+newName);
        qry.setParameter("descr", newName);
        qry.setParameter("oldDescr", oldName);
        result = qry.executeUpdate();
        return result;
    }
    
}
