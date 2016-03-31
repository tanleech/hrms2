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
    
    List<DeptDTO> getAllDepts() throws Exception;

    void addDept(DeptDTO deptDTO) throws Exception;

    DeptDTO getDepartment(String deptDescr) throws Exception;

    UserDeptDTO getUserDept(int userId, int deptId) throws Exception;

    void unassignManager(int deptId) throws Exception;

    void assignEmployee(UserDTO userDTO, DeptDTO deptDTO) throws Exception; 

    void unassignEmployee(int userId, int deptId) throws Exception;

    int assignManager(int userId, int deptId) throws Exception;

    int updateEmployee(int userId, int deptId) throws Exception;

    int updateDept(String oldName, String newName) throws Exception;
    
}
