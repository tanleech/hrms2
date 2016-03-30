/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;

/**
 *
 * @author michael-PC
 */
public interface DeptDAO extends BaseDAO{
    
    List<DeptDTO> getAllDepts();

    void addDept(DeptDTO deptDTO);

    DeptDTO getDepartment(String deptDescr);

    UserDeptDTO getUserDept(int userId, int deptId);

    void unassignManager(int deptId);

    void assignEmployee(UserDTO userDTO, DeptDTO deptDTO); 

    void unassignEmployee(int userId, int deptId);

    int assignManager(int userId, int deptId);

    int updateEmployee(int userId, int deptId);

    int updateDept(String oldName, String newName);
    
}
