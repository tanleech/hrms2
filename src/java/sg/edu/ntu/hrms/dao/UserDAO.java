package sg.edu.ntu.hrms.dao;

import sg.edu.ntu.hrms.dto.RoleDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import java.util.Date;
import java.util.List;

public interface UserDAO extends BaseDAO{
	
    //UserDTO authenticate(String parameter,String password, boolean useLdap);

    void createUser(UserDTO parameter) throws Exception;

    List<UserDTO> getAllUsers(Date from, Date to) throws Exception;
    
    UserDTO getUser(String loginId) throws Exception;

    UserDTO getUserFromId(int id) throws Exception;

    void assignRole(UserDTO user, RoleDTO role) throws Exception;

    void updateUser(UserDTO userDTO) throws Exception;

    void updateRole(int userId, int roleId) throws Exception;

    List<UserDTO> getReporteeList(int userId) throws Exception;

}
