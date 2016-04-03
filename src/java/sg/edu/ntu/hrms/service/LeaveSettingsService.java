/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.LeaveDAO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;

/**
 *
 * @author michael-PC
 */
@Service("leaveSettings")
public class LeaveSettingsService extends BaseService {
    @Autowired
    LeaveDAO leaveDAO;
    
    public void updateLeaveType(LeaveTypeDTO leaveType)
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            leaveDAO.setSession(session);
            leaveDAO.updateLeaveSetting(leaveType);
            txn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
        }finally{
            session.close();
        }
    }
    public LeaveTypeDTO getLeaveType(String id)
    {
        Session session = sessionFactory.openSession();
        LeaveTypeDTO leaveType = null;
        try{
            leaveDAO.setSession(session);
            leaveType = leaveDAO.getLeaveSetting(Integer.parseInt(id));
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }    
        return leaveType;    
    }
    
    public List<LeaveTypeDTO> getAllLeaveTypes(){
        
        Session session = sessionFactory.openSession();
        List<LeaveTypeDTO> leaveTypeList = null;
        try{
            leaveDAO.setSession(session);
            leaveTypeList = leaveDAO.getAllLeaveSettings();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }    
        return leaveTypeList;    

    }
    public void deleteLeaveSetting(String id)
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            leaveDAO.setSession(session);
            leaveDAO.deleteLeaveSetting(Integer.parseInt(id));

            txn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
        }finally{
            session.close();
        }

    }
    public void addLeaveSetting(LeaveTypeDTO type)throws Exception
    {
        //LeaveTypeDTO type = prepare(request);
        Session session = sessionFactory.openSession();
        List<LeaveTypeDTO> leaveTypeList = null;
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            leaveDAO.setSession(session);
            if(leaveDAO.getLeaveType(type.getDescription())!=null)
            {
                throw new Exception ("Duplicate name not allowed");
            }
            else
            {
                leaveDAO.saveLeaveSetting(type);
                txn.commit();
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
            txn.rollback();
            throw ex;
            
        }finally{
            session.close();
        }
    }
    
}
