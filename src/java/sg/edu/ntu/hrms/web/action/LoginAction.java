package sg.edu.ntu.hrms.web.action;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.SessionAware;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.service.LoginService;


public class LoginAction extends BaseAction implements SessionAware{
	
    private static final long serialVersionUID = 7363651090561176952L;

    private String loginId;
    private String password;
    private String error;
    private Map<String, Object> sessionMap;
    
    //@Autowired
    //private LoginService loginSvc;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	
	public String execute(){
               
           if(loginId==null||loginId.isEmpty()
           ||password==null||password.isEmpty())
           {
	       return INPUT;
	   }
           else
           {
             LoginService loginSvc = (LoginService)ctx.getBean(LoginService.class);
             UserDTO auth = loginSvc.authenticate(loginId, password);
             
             if(auth==null)
             {
                   addActionError("Invalid User Credential");
                   return ERROR;
             }
             else
             {
                   HashMap<String,Object> acr = loginSvc.getAccessRights();
                   // add userName to the session
                   sessionMap.put("User", auth);
                   sessionMap.put("access", acr);
                   return SUCCESS;
             }
           }
        }
        
        public String logout(){
            //invalidate the session
            sessionMap.clear();
            
            return SUCCESS;
        }

    @Override
    public void setSession(Map<String, Object> map) {
         sessionMap = map;
    }


}
