/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author michael-PC
 */
public class BaseAction extends ActionSupport {
    WebApplicationContext ctx ;
    
    public BaseAction()
    {
    	ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        
    }
}
