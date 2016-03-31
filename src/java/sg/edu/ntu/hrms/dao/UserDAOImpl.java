package sg.edu.ntu.hrms.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.ntu.hrms.dto.RoleDTO;

import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserRoleDTO;

@Repository
public class UserDAOImpl extends BaseDAOImpl implements UserDAO {


    
	@Override
        @Transactional
	public void createUser(UserDTO user) throws Exception{
                    // TODO Auto-generated method stub
            java.util.Date current = new java.util.Date();
            user.setCreated(current);
            user.setModified(current);
            Session session = null;
            session = getSession();
            user.setDeleted("N");
            session.persist(user);
                //audit log
                /*
                if(getAuthor()!=null)
                {
                    String descr = "Employee:Add Employee: "+user.getName();
                    //auditLog(descr, user.getAuthor());
                    auditBean.log(descr, getAuthor());
                }
                */       
	}

	@Override
        @Transactional
	public List<UserDTO> getAllUsers(Date from, Date to) throws Exception {
            List<UserDTO> results = null;
            String hql = "FROM sg.edu.ntu.hrms.dto.UserDTO U left join fetch U.dept WHERE U.dateJoin BETWEEN :stDate "
                    +    "AND :edDate AND U.deleted='N'";
            Session session = null;
            session = getSession();
            Query query = session.createQuery(hql);
            query.setParameter("stDate", from);
            query.setParameter("edDate", to);

            results = query.list();
            return results;
	}

	@Override
	@Transactional
	public UserDTO getUser(String loginId) throws Exception {
		// TODO Auto-generated method stub
             UserDTO data=null;
	     String hql = "FROM sg.edu.ntu.hrms.dto.UserDTO U left join fetch U.role WHERE U.login = :userLogin";
	     Session session=null;
	         session = getSession();
	         Query query = session.createQuery(hql);
                 query.setParameter("userLogin", loginId);
	         List<UserDTO> results = query.list();
	         if(results!=null && !results.isEmpty())
	         {
	            data = (UserDTO) results.get(0);
	         }
		return data;
	}

	@Override
        @Transactional
	public UserDTO getUserFromId(int id) throws Exception{
            UserDTO data=null;
            String hql = "FROM sg.edu.ntu.hrms.dto.UserDTO U left join fetch U.role WHERE U.id = :Id";
            Session session=null;
            session = getSession();
            Query query = session.createQuery(hql);
            query.setParameter("Id", id);
            List results = query.list();
            if(results!=null && !results.isEmpty())
            {
                data = (UserDTO) results.get(0);
            }
            return data;
	}

    @Override
    @Transactional
    public List<UserDTO> getReporteeList(int userId) throws Exception{
            Session session = null;
            Transaction txn = null;
            List<UserDTO> results = null;
            session = getSession();
            //txn = session.beginTransaction();
            Query qry = session.createQuery("FROM sg.edu.ntu.hrms.dto.UserDTO user WHERE user.approver=:userId");
            qry.setParameter("userId", userId);
            results = qry.list();
            return results;
    }

    @Override
    @Transactional
    public void assignRole(UserDTO userDto, RoleDTO roleDto) throws Exception{
       java.util.Date current = new java.util.Date();
        UserRoleDTO userRole = new UserRoleDTO();
        userRole.setCreated(current);
        userRole.setModified(current);
        Session session = null;
        session = getSession();
        userRole.setUser(userDto);
        userRole.setRole(roleDto);
        session.persist(userRole);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) throws Exception {
       java.util.Date current = new java.util.Date();
        Session session = null;
        session = getSession();
        System.out.println("id: "+userDTO.getId());
        UserDTO user = (UserDTO) session.get(UserDTO.class, userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setOffice(userDTO.getOffice());
        user.setLogin(userDTO.getLogin());
        user.setDateJoin(userDTO.getDateJoin());
        user.setProbationDue(userDTO.getProbationDue());
        user.setTitle(userDTO.getTitle());
        user.setModified(current);
        user.setApprover(userDTO.getApprover());
        session.saveOrUpdate(user);
        /*
            //audit log
            if(getAuthor()!=null)
            {
                String descr = "Employee:Update Employee: "+user.getName();
                //auditLog(descr, userDTO.getAuthor());
                //auditBean.log(descr, getAuthor());
            }
          */      
     }

    @Override
    @Transactional
    public void updateRole(int userId, int roleId) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
