/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.hrms.dao.DeptDAO;
import sg.edu.ntu.hrms.dto.DeptDTO;

/**
 *
 * @author michael-PC
 */
@Service("employeeEditService")
public class EmployeeEditService {
    @Autowired
    private DeptDAO deptDAO;
    
    public List<DeptDTO> getAllDepts()
    {
        
        List<DeptDTO> results = null;    
        try
        {
            results = deptDAO.getAllDepts();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return results;
    }
            

    


}
