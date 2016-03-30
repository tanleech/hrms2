/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;

/**
 *
 * @author michael-PC
 */
public interface AccessDAO extends BaseDAO{
    
    List<RoleDTO> getAllRoles();

    void addRole(RoleDTO roleDTO);

    RoleDTO getRole(String descr);

    void update(int roleId, List<AccessDTO>accessList);

    List<AccessDTO> getAccessRights(int roleId);
}
