package sg.edu.ntu.hrms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.AccessDAO;

import sg.edu.ntu.hrms.dao.UserDAO;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

@Service("loginService")
public class LoginService {
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private AccessDAO accessDAO;

    private HashMap<String,Object> accessRights;

    public HashMap<String, Object> getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(HashMap<String, Object> accessRights) {
        this.accessRights = accessRights;
    }
    
	public UserDTO authenticate(String loginName, String password){
            //userDAO = new UserDAOImpl();
            UserDTO auth = null;
            
            if(userDAO!=null){
                auth = userDAO.getUser(loginName);
            }
            if(auth!=null&&!password.equals(auth.getPassword()))
            {
                auth=null;
            }
            else if(auth!=null&&password.equals(auth.getPassword()))
            {
                
                //List<AccessDTO> list = accessDAO.getAccessRights(auth.getRole().getRole().getId());
                //setAccessRights(convertToACRMap(list));
                // session.setAttribute("access", convertToACRMap(list));
                //determine manager
                UserDTO mgr = userDAO.getUserFromId(auth.getApprover());
                if(mgr!=null)
                {
                    auth.setApproverEmail(mgr.getEmail());
                    auth.setApproverName(mgr.getName());
                }
                List<UserDTO> resultList = userDAO.getReporteeList(auth.getId());
                //System.out.println("auth id: "+auth.getId());
                if(resultList!=null&&resultList.size()>0)
                {
                   System.out.println("isMgr");
                   auth.setIsManager(true);
                }
                List<AccessDTO> list = accessDAO.getAccessRights(auth.getRole().getRole().getId());
                setAccessRights(convertToACRMap(list));

            }
            return auth;
	}
    private HashMap convertToACRMap(List<AccessDTO>accessList)
    {
        HashMap map = new HashMap();
        for(int i=0;i<accessList.size();i++)
        {
            AccessDTO access = accessList.get(i);
            map.put(access.getModule().getName(), access);
        }
        return map;
    }

}
