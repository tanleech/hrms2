/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.LeaveTxnDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.StatusDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
public interface LeaveDAO extends BaseDAO{
    
    void saveLeaveSetting(LeaveTypeDTO leaveType) throws Exception;

    List<LeaveTypeDTO> getAllLeaveSettings() throws Exception;

    void deleteLeaveSetting(int typeId) throws Exception;

    LeaveTypeDTO getLeaveSetting(int id) throws Exception;

    void updateLeaveSetting(LeaveTypeDTO type) throws Exception;

    void applyLeave(LeaveTxnDTO leaveTxn) throws Exception;

    double getLeaveBalance(LeaveTypeDTO leaveType, UserDTO user) throws Exception;

    List<LeaveEntDTO> getLeaveEntList(String loginId) throws Exception;

    void addLeaveEnt(LeaveEntDTO leaveEnt) throws Exception;

    LeaveTypeDTO getLeaveType(String leaveType) throws Exception;

    List<LeaveTypeDTO> getLeaveSettings(boolean mandatory) throws Exception;

    void deleteLeaveEnt(int entId, int userId) throws Exception;

    List<LeaveTxnDTO> getAllTxn() throws Exception;

    List<LeaveTxnDTO> getTxnForApprover(int approver) throws Exception;

    List<LeaveTxnDTO> getLeaveRecords(int userId) throws Exception;

    StatusDTO getStatus(String descr) throws Exception;

    void updateLeaveEnt(int leaveTypeId, int userId, double days) throws Exception;
    
    void updateLeaveEntitlement(LeaveEntDTO entDo, int userId) throws Exception;

    void approveLeave(int txnId, int status) throws Exception;

    LeaveTxnDTO getTxn(int txnId) throws Exception;

    
}
