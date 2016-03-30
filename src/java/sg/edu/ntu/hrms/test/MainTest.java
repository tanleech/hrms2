package sg.edu.ntu.hrms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.ntu.hrms.config.AppConfig;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.service.LoginService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class MainTest {

 @Autowired
 private LoginService loginService;
 
 @Test
 public void testAuth()throws Exception
 {
	 //UserDTO user = userDAO.getUser("michael.tan");
	 //assertThat(user.getPassword(),is("pass"));
	 UserDTO auth = loginService.authenticate("michael.tan", "pass");
	 if(auth != null){
		 System.out.println("auth success");
	 }
	 else{
		 System.out.println("auth failed");
	 }
 }
 
 

}
