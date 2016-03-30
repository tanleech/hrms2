package sg.edu.ntu.hrms.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.ntu.hrms.dto.UserDTO;


public class BaseDAOImpl implements BaseDAO{
    @Autowired	
    SessionFactory sessionFactory;

    @Override
	public UserDTO getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAuthor(UserDTO author) {
		// TODO Auto-generated method stub
		
	}

	
}
