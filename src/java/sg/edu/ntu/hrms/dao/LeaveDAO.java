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
    
    void saveLeaveSetting(LeaveTypeDTO leaveType);

    List<LeaveTypeDTO> getAllLeaveSettings();

    void deleteLeaveSetting(int typeId);

    LeaveTypeDTO getLeaveSetting(int id);

    void updateLeaveSetting(LeaveTypeDTO type);

    void applyLeave(LeaveTxnDTO leaveTxn);

    double getLeaveBalance(LeaveTypeDTO leaveType, UserDTO user);

    List<LeaveEntDTO> getLeaveEntList(String loginId);

    void addLeaveEnt(LeaveEntDTO leaveEnt);

    LeaveTypeDTO getLeaveType(String leaveType);

    List<LeaveTypeDTO> getLeaveSettings(boolean mandatory);

    void deleteLeaveEnt(int entId, int userId);

    List<LeaveTxnDTO> getAllTxn();

    List<LeaveTxnDTO> getTxnForApprover(int approver);

    List<LeaveTxnDTO> getLeaveRecords(int userId);

    StatusDTO getStatus(String descr);

    void updateLeaveEnt(int leaveTypeId, int userId, double days);
    
    void updateLeaveEntitlement(LeaveEntDTO entDo, int userId);

    void approveLeave(int txnId, int status);

    LeaveTxnDTO getTxn(int txnId);

    
}
