package sg.edu.ntu.hrms.dao;

import org.hibernate.SessionFactory;
import sg.edu.ntu.hrms.dto.UserDTO;

public interface BaseDAO {
	public UserDTO getAuthor();
    public void setAuthor(UserDTO author);    

}
