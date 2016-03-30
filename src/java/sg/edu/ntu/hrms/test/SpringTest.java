/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sg.edu.ntu.hrms.config.AppConfig;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.service.LoginService;

/**
 *
 * @author michael-PC
 */
public class SpringTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   ApplicationContext ctx = 
   new AnnotationConfigApplicationContext(AppConfig.class);
        //appContext.register(AppConfig.class);
        
        LoginService login = (LoginService)ctx.getBean(LoginService.class);
        UserDTO auth = login.authenticate("derique", "pass");
         if(auth != null){
		 System.out.println("auth success");
	 }
	 else{
		 System.out.println("auth failed");
	 }
    }
    
}
