/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.LeaveTxnDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.StatusDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
@Repository
public class LeaveDAOImpl extends BaseDAOImpl implements LeaveDAO {

    @Override
    @Transactional
    public void saveLeaveSetting(LeaveTypeDTO leaveType) throws Exception {
        java.util.Date current = new java.util.Date();
        Session session = null;
        leaveType.setCreated(current);
        leaveType.setModified(current);
        session =  getSession();
        session.persist(leaveType);
    }

    @Override
    @Transactional
    public List<LeaveTypeDTO> getAllLeaveSettings() throws Exception {
        List results=null;
        Session session=null;
        session = getSession();
        results =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO leaveType").list();
        return results;
    }

    @Override
    @Transactional
    public void deleteLeaveSetting(int typeId) throws Exception {
       List results=null;
       Session session=null;
       LeaveTypeDTO lvlType = getLeaveSetting(typeId);
       session = getSession();
       Query delQry = session.createQuery("DELETE FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO where id = :typeId");
       delQry.setParameter("typeId", typeId);
       delQry.executeUpdate();
     }

    @Override
    @Transactional
    public LeaveTypeDTO getLeaveSetting(int id) throws Exception{
        Session session=null;
        LeaveTypeDTO result = null;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO WHERE id = :typeId");
        qry.setParameter("typeId", id);
        result = (LeaveTypeDTO)qry.list().get(0);
        return result;
    }

    @Override
    @Transactional
    public void updateLeaveSetting(LeaveTypeDTO type) throws Exception {
      Session session=null;
      java.util.Date current = new java.util.Date();
      session = getSession();
      Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveTypeDTO "
                    + " set description = :descr, days = :ent, mandatory = :option, annualIncre = :annual, cf = :cf, modified =:modified"
                    + " where id = :typeId");
      updateQry.setParameter("typeId", type.getId());
      updateQry.setParameter("descr", type.getDescription());
      System.out.println("days: "+type.getDays());
      updateQry.setParameter("ent", type.getDays());
      updateQry.setParameter("option", type.getMandatory());
      updateQry.setParameter("annual", type.getAnnualIncre());
      updateQry.setParameter("cf", type.getCarriedForward());
      updateQry.setParameter("modified", current);
      updateQry.executeUpdate();
     }

    @Override
    @Transactional
    public void applyLeave(LeaveTxnDTO leaveTxn) throws Exception{
        Session session=null;
        java.util.Date current = new java.util.Date();
        session = getSession();
        leaveTxn.setCreated(current);
        leaveTxn.setModified(current);
        session.persist(leaveTxn);            
    }

    @Override
    @Transactional
    public double getLeaveBalance(LeaveTypeDTO leaveType, UserDTO user) throws Exception{
        Session session=null;
        LeaveEntDTO result = null;
        double bal = -1.0;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveEntDTO ent WHERE ent.user.id = :userId and ent.leaveType.id = :typeId");
        qry.setParameter("userId",user.getId());
        System.out.println("leave typeId: "+leaveType.getId());
        qry.setParameter("typeId",leaveType.getId());
            
        List<LeaveEntDTO> entList = qry.list();
        if(entList==null||entList.isEmpty())
        {
            bal = 0;
        }
        else
        {
            result = (LeaveEntDTO)qry.list().get(0);
            bal = result.getBalance();
        }
        return bal;
    }

    @Override
    @Transactional
    public List<LeaveEntDTO> getLeaveEntList(String loginId) throws Exception{
       Session session=null;
        List<LeaveEntDTO> result = null;
        session = getSession();
        Query qry =  session.createQuery("SELECT ent FROM sg.edu.ntu.hrms.dto.LeaveEntDTO ent left join fetch ent.leaveType WHERE ent.user.login = :loginId");
        qry.setParameter("loginId", loginId);
        result = qry.list();
        return result;
     }

    @Override
    @Transactional
    public void addLeaveEnt(LeaveEntDTO leaveEnt) throws Exception{
        Session session=null;
        java.util.Date current = new java.util.Date();
        session = getSession();
        leaveEnt.setCreated(current);
        leaveEnt.setModified(current);
        session.persist(leaveEnt);
    }

    @Override
    @Transactional
    public LeaveTypeDTO getLeaveType(String leaveType) throws Exception {
       Session session=null;
        List<LeaveTypeDTO> results = null;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO type WHERE type.description = :descr");
        qry.setParameter("descr", leaveType);
        results = qry.list();
        if(results!=null&&!results.isEmpty())
        {
            return (LeaveTypeDTO)results.get(0); 
        }
        else
        {
            return null;
        }
    }

    @Override
    @Transactional
    public List<LeaveTypeDTO> getLeaveSettings(boolean mandatory) throws Exception{
        Session session=null;
        List<LeaveTypeDTO> results = null;
        String flag="N";
        if(mandatory)
        {
            flag="Y";
        }
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO WHERE mandatory = :man");
        qry.setParameter("man", flag);
        results = qry.list();
        return results;
    }

    @Override
    @Transactional
    public void deleteLeaveEnt(int entId, int userId) throws Exception{
        Session session=null;
        session = getSession();
        Query delQry = session.createQuery("DELETE FROM sg.edu.ntu.hrms.dto.LeaveEntDTO ent where id = :entId and ent.user.id=:userId");
        delQry.setParameter("entId", entId);
        delQry.setParameter("userId", userId);
        delQry.executeUpdate();
      
    }

    @Override
    @Transactional
    public List<LeaveTxnDTO> getAllTxn() throws Exception {
        List results=null;
        Session session=null;
        session = getSession();
        results =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn left join fetch txn.user").list();
        return results;
    }

    @Override
    @Transactional
    public List<LeaveTxnDTO> getTxnForApprover(int approver) throws Exception{
        List results=null;
        Session session=null;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn left join fetch txn.user WHERE txn.user.approver =:approverId");
        qry.setParameter("approverId", approver);
        results = qry.list();
        return results;
    }

    @Override
    @Transactional
    public List<LeaveTxnDTO> getLeaveRecords(int userId) throws Exception {
        List results=null;
        Session session=null;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn WHERE txn.user.id =:userId");
        qry.setParameter("userId", userId);
        results = qry.list();
        return results;
    }

    @Override
    @Transactional
    public StatusDTO getStatus(String descr) throws Exception {
        Session session=null;
        StatusDTO status = null;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.StatusDTO status WHERE status.description =:descr");
        qry.setParameter("descr", descr);
        status = (StatusDTO)qry.list().get(0);
        return status;
    }

    @Override
    @Transactional
    public void updateLeaveEnt(int leaveTypeId, int userId, double days) throws Exception {
        Session session=null;
        java.util.Date current = new java.util.Date();
        session = getSession();
        Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveEntDTO ent "
                    + " set  ent.balance = :bal, ent.modified =:modified "
                    + " where ent.leaveType.id = :typeId AND ent.user.id = :userId");
        System.out.println("typeId: "+leaveTypeId);
        updateQry.setParameter("typeId", leaveTypeId);
        updateQry.setParameter("userId", userId);
        updateQry.setParameter("bal", days);
        updateQry.setParameter("modified", current);
        updateQry.executeUpdate();
 
    }

    @Override
    @Transactional
    public void updateLeaveEntitlement(LeaveEntDTO entDTO, int userId) throws Exception {
        Session session=null;
        java.util.Date current = new java.util.Date();
        session = getSession();
        Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveEntDTO ent "
                    + " set  ent.balance = :bal, ent.current= :cur, ent.max = :max, ent.modified =:modified "
                    + " where ent.leaveType.id = :typeId AND ent.user.id = :userId");
        //System.out.println("typeId: "+leaveTypeId);
        updateQry.setParameter("typeId", entDTO.getLeaveType().getId());
        updateQry.setParameter("userId", userId);
        updateQry.setParameter("max",entDTO.getMax());
        updateQry.setParameter("cur", entDTO.getCurrent());
        updateQry.setParameter("bal", entDTO.getBalance());
        updateQry.setParameter("modified", current);
        updateQry.executeUpdate();
    }

    @Override
    @Transactional
    public void approveLeave(int txnId, int status) throws Exception {
        Session session=null;
        java.util.Date current = new java.util.Date();
        session = getSession();
        Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveTxnDTO txn "
                    + " set  txn.status.id = :status, txn.modified =:modified "
                    + " where txn.id = :txnId");
        updateQry.setParameter("status", status);
        updateQry.setParameter("txnId", txnId);
        updateQry.setParameter("modified", current);
        updateQry.executeUpdate();
    }

    @Override
    @Transactional
    public LeaveTxnDTO getTxn(int txnId) throws Exception{
        List results=null;
        Session session=null;
        LeaveTxnDTO txn = null;
        session = getSession();
        Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn WHERE txn.id =:txnId");
        qry.setParameter("txnId", txnId);
        results = qry.list();
        txn = (LeaveTxnDTO)results.get(0);
        return txn;
    }

    
}
