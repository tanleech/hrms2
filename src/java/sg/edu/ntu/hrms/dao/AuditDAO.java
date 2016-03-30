/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.Date;
import java.util.List;
import sg.edu.ntu.hrms.dto.AuditDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
public interface AuditDAO extends BaseDAO{
    void log(String descr,UserDTO author);

    List<AuditDTO> getAuditLog(Date from, Date to);
}
