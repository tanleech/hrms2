/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.ModuleDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;
import sg.edu.ntu.hrms.service.AccessService;

/**
 *
 * @author michael-PC
 */
public class AccessAction extends BaseAction implements ServletRequestAware {
    
    private String action;
    //private String name;
    private List<RoleDTO> roleList;
    private List<ModuleDTO> moduleList;
    private final AccessService svc = (AccessService)ctx.getBean(AccessService.class);
    private String error;
    private String role;
    private RoleDTO roleData;
    private String sys;

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }
    
    

    public RoleDTO getRoleData() {
        return roleData;
    }

    public void setRoleData(RoleDTO roleData) {
        this.roleData = roleData;
    }

    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    
    public List<ModuleDTO> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleDTO> moduleList) {
        this.moduleList = moduleList;
    }

    
    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }

    
    public String getAllRoles(){
        
        roleList = svc.getAllRoles();
        
        return SUCCESS;
    }
    
    public String getAddAccess(){
        
        moduleList = svc.getAllModules();
        action = "A";
        return INPUT;
    }
    
    public String addAccess(){
        
        RoleDTO role = prepare(request);
        try{
           svc.addRole(role);
           return SUCCESS;
           //page =  "/roleList"; 
        }catch(Exception ex){
            ex.printStackTrace();
           error = "Duplicate role name not allowed.";
           return ERROR;
            //page = "/roleEdit.jsp";
        }
    }
    public String getUpdateAccess(){
        
        //get the role description
        //String description = request.getParameter("role");
        //RoleDTO roleDTO = accessBean.getRole(description);
        RoleDTO roleDTO = svc.getRole(role);
        //request.setAttribute("roleData", roleDTO);
        roleData = roleDTO;
        String flag ="N";
        for(int i=0; i<roleDTO.getAccessList().size();i++)
        {
            AccessDTO entry = roleDTO.getAccessList().get(i);
            if(entry.getAccess()==1)
            {
               flag="Y";
            }
        }
       sys = flag;
       action= "U";
        //request.setAttribute("sys", flag);
        //clear the modulelist in session.
        //request.getSession().removeAttribute("moduleList");
        //page = "/roleEdit.jsp?action=U";
       return INPUT;  
    }
    
    public String updateAccess()
    {
         RoleDTO role = prepare(request);
         svc.updateRole(role);
            //accessBean.update(role);
         return SUCCESS;  
    }
    
    private RoleDTO prepare(HttpServletRequest request)
    {
            List<ModuleDTO> moduleList = (List)request.getSession().getAttribute("moduleList");
            if(moduleList==null)
            {
                moduleList = svc.getAllModules();//moduleBean.getAllModules();
            }
            List<AccessDTO> accessList = new ArrayList();
            RoleDTO role = new RoleDTO();
            String roleName = request.getParameter("name");
            role.setDescription(roleName);
            
            //system access
            //String acr = request.getParameter("system");
            System.out.println("module: "+moduleList);
            for(int i=0;i<moduleList.size();i++)
            {
                ModuleDTO module = moduleList.get(i);
                AccessDTO access = new AccessDTO();
                /*
                if(acr.equals("0"))
                {
                  access.setAccess(0);
                }
                */
                //else
                {
                    String rights = request.getParameter(module.getName());
                    System.out.println("rights: "+rights);
                    System.out.println("module name: "+module.getName());
                    access.setAccess(Integer.parseInt(rights));
                }
                access.setRole(role);
                access.setModule(module);
                accessList.add(access);
            }//end for
            role.setAccessList(accessList);
            return role;

    }
    

   private HttpServletRequest request;
   @Override
   public void setServletRequest(HttpServletRequest request) {
		this.request = request;
    }

    public HttpServletRequest getServletRequest() {
		return this.request;
    }
    
    
 
    
}
