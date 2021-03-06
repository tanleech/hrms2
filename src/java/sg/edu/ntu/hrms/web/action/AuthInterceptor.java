/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import com.opensymphony.xwork2.Action;
import java.util.Map;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
public class AuthInterceptor extends AbstractInterceptor{
    
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
            Map<String, Object> session = invocation.getInvocationContext().getSession();

            UserDTO user = (UserDTO)session.get("User");
            HashMap accessList = (HashMap)session.get("access");
            System.out.println("auth Interceptor");
           if(session!=null && user !=null)
           {
                   //authenticated
                   //check the access rights
                   HttpServletRequest request = ServletActionContext.getRequest();
                   String url = request.getRequestURL().toString();
                   System.out.println("url: "+url);
                   String module = getModuleName(url);
                   /*
                   String queryString = ((HttpServletRequest)request).getQueryString();
                   System.out.println("qry: "+queryString);
                   */        
                   System.out.println("module: "+module);
                   if(!hasAccess(module,null,accessList,user.isIsManager()))
                   {
                       System.out.println("no access"); 
                       return "noaccess";
                       
                   }
                   else
                   {
                       return invocation.invoke();
                   }

           }
           else
           {
                   return Action.LOGIN;
                   //HttpServletResponse httpResponse = (HttpServletResponse) response;
                   //httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/login");
           }
           //return Action.SUCCESS;
    }
    
    
     private boolean hasAccess(String module,String action, HashMap accessTab, boolean isManager)
    {
        boolean hasAccess=false;
        System.out.println("module: "+module);
        if(module.equals("employee")||module.equals("employee.action"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Employee");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        }
        else if (module.startsWith("employeeAdd")||module.startsWith("employeeUpdate")||module.startsWith("leaveEnt")||module.startsWith("leaveEntAdd")
                ||module.startsWith("leaveEntAssign")||module.startsWith("leaveEntUnassign"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Employee");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.startsWith("deptList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Department");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        }
        else if (module.startsWith("deptEdit")||module.startsWith("addDept")
                ||module.startsWith("assignMgr")||module.startsWith("getAssignEmp")
                ||module.startsWith("deptAssignEmp")||module.startsWith("UnAssignEmp")
                ||module.startsWith("getUpdateDept")||module.startsWith("updateDept")
                )
        {
            AccessDTO access =(AccessDTO)accessTab.get("Department");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.startsWith("roleList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("User Roles");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
            
        }
        else if (module.startsWith("getAddAccess")||module.startsWith("addAccess")
                ||module.startsWith("getUpdateAccess")||module.startsWith("updateAccess"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("User Roles");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.startsWith("titleList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Job Title");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        }
        else if (module.startsWith("addTitle")||module.startsWith("getAddTitle"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Job Title");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.startsWith("leaveSettings"))
        {
           AccessDTO access =(AccessDTO)accessTab.get("Leave Setting");
           if(action==null&&access.getAccess()>=1)
           {
                hasAccess=true;
           }
           /*
           if(action!=null)
           {
            System.out.println("actionStr: "+action);
            if(action.contains("action=U"))
            {
                 if(access.getAccess()==2)
                 {
                     hasAccess=true;
                 }
            }
            else if(action.contains("action=A")||action.contains("action=D")
                    ||action.contains("action=E"))
            {
                 if(access.getAccess()==2)
                 {
                     hasAccess=true;
                 }

            }    
           }*/
        }
        else if (module.startsWith("getAdd")||module.startsWith("add")||module.startsWith("getUpdate")
                ||module.startsWith("update")||module.startsWith("deleteSetting"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Leave Setting");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
        }    
        else if(module.startsWith("leaveTxn"))
        {
           AccessDTO access =(AccessDTO)accessTab.get("Leave");
           if(action!=null)
           {
            System.out.println("actionStr: "+action);
            if(action.contains("action=list")||action.contains("action=Q"))
            {
                 if(access.getAccess()>=1)
                 {
                     hasAccess=true;
                 }
            }
           }
           else
           {
                if(access.getAccess()>=1)
                 {
                     hasAccess=true;
                 }
           }
        }
        else if(module.startsWith("leaveTxnAdd")||module.startsWith("getLeaveForm")
                ||module.startsWith("applyLeave")||module.startsWith("approveLeave"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Leave");
            if(access.getAccess()>=1)
            {
                     hasAccess=true;
            }

        }
        else if(module.startsWith("leaveTxnApprove"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Leave");
            if(access.getAccess()>=1 && isManager)
            {
                     hasAccess=true;
            }

        }
        else if(module.startsWith("getAuditLog")||module.startsWith("auditList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("System Log");
            if(access.getAccess()>=1 )
            {
                     hasAccess=true;
            }
            
        }
        else if (module.startsWith("uploadEmp"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Upload");
            if(access.getAccess()>=1 )
            {
                     hasAccess=true;
            }
            
        }
        
        
            
        return hasAccess;
    }
    private String getModuleName(String url)
    {

        String last = url.substring(url.lastIndexOf('/') + 1);
        return last; 
        
    }
}
