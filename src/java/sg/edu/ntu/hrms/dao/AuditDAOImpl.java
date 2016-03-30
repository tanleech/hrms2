/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sg.edu.ntu.hrms.dto.AuditDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
public class AuditDAOImpl extends BaseDAOImpl implements AuditDAO {

    @Override
    public void log(String descr, UserDTO author) {
        
        AuditDTO audit = create(descr,author);
        java.util.Date current = new java.util.Date();
        audit.setCreated(current);
        audit.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session = sessionFactory.getCurrentSession();
            txn = session.beginTransaction();
            session.persist(audit);
            txn.commit(); 

        }
        catch (Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public List<AuditDTO> getAuditLog(Date from, Date to) {
        List<AuditDTO> results = null;
        String hql = "FROM com.sapuraglobal.hrms.dto.AuditDTO U left join fetch U.login WHERE U.created BETWEEN :stDate "
                +    "AND :edDate";
        Session session = null;
        try
        {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("stDate", from);
            query.setParameter("edDate", to);

            results = query.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return results;
    }
    
    private AuditDTO create(String descr,UserDTO author)
    {
            AuditDTO audit = new AuditDTO();
            audit.setDescr(descr);
            audit.setLogin(author);
            return audit;
    }    
}
