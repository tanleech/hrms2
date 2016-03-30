package sg.edu.ntu.hrms.dao;

import sg.edu.ntu.hrms.dto.RoleDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import java.util.Date;
import java.util.List;

public interface UserDAO extends BaseDAO{
	
    //UserDTO authenticate(String parameter,String password, boolean useLdap);

    void createUser(UserDTO parameter);

    List<UserDTO> getAllUsers(Date from, Date to);
    
    UserDTO getUser(String loginId);

    UserDTO getUserFromId(int id);

    void assignRole(UserDTO user, RoleDTO role);

    void updateUser(UserDTO userDTO);

    void updateRole(int userId, int roleId);

    List<UserDTO> getReporteeList(int userId);

}
