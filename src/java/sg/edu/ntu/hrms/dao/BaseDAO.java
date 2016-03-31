package sg.edu.ntu.hrms.dao;

import org.hibernate.Session;
import sg.edu.ntu.hrms.dto.UserDTO;

public interface BaseDAO {
    public UserDTO getAuthor();
    public void setAuthor(UserDTO author);    
    public void setSession(Session session);

}
