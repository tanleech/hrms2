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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.ntu.hrms.dto.AuditDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
@Repository
public class AuditDAOImpl extends BaseDAOImpl implements AuditDAO {

    @Override
    @Transactional
    public void log(String descr, UserDTO author) throws Exception{
        
        AuditDTO audit = create(descr,author);
        java.util.Date current = new java.util.Date();
        audit.setCreated(current);
        audit.setModified(current);
        Session session = null;
        session = getSession();
        session.persist(audit);
    }

    @Override
    @Transactional
    public List<AuditDTO> getAuditLog(Date from, Date to) throws Exception {
        List<AuditDTO> results = null;
        String hql = "FROM sg.edu.ntu.hrms.dto.AuditDTO U left join fetch U.login WHERE U.created BETWEEN :stDate "
                +    "AND :edDate";
        Session session = null;
        session = getSession();
        Query query = session.createQuery(hql);
        query.setParameter("stDate", from);
        query.setParameter("edDate", to);
        results = query.list();
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
