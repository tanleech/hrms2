package sg.edu.ntu.hrms.dao;

import org.hibernate.Session;

import sg.edu.ntu.hrms.dto.UserDTO;


public class BaseDAOImpl implements BaseDAO{
    //@Autowired	
    //SessionFactory sessionFactory;
    
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

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
