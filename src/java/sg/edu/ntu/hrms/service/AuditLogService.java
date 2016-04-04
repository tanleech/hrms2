/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.AuditDAO;
import sg.edu.ntu.hrms.dto.AuditDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
@Service("auditService")
public class AuditLogService extends BaseService {
    
    @Autowired
    private AuditDAO auditDAO;
    
    public void createLogRecord(String descr, UserDTO author){
        Session session = sessionFactory.openSession();
        Transaction txn = null;
	try {
            txn = session.beginTransaction();
            auditDAO.setSession(session);
            auditDAO.log(descr, author);
            txn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
        }finally{
            session.close();
        }
    }
    
     public List<AuditDTO> getAuditLog(Date from, Date to)
     {
        Session session = sessionFactory.openSession();
        List<AuditDTO>results = null;
	try {
            auditDAO.setSession(session);
            results = auditDAO.getAuditLog(from, to);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }
        return results;
         
     }
}
