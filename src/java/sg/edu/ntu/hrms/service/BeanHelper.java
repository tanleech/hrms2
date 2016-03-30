/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import sg.edu.ntu.hrms.dto.UserDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.UserDAO;

/**
 *
 * @author michael-PC
 */
@Service("beanHelperService")
public class BeanHelper {
    
    public List<UserDTO> getAllUsers(UserDAO userBean)
    {
        List<UserDTO> userList=null;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

            Date from = formatter.parse("01/01/0000");
            Date to   = formatter.parse("12/31/9999");

            userList = userBean.getAllUsers(from, to);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return userList;
    }
    
    public HashMap getUserTab(UserDAO userBean)
    {
        HashMap map=new HashMap();
        List<UserDTO> allUsers = getAllUsers(userBean);
            for(int i=0;i<allUsers.size();i++)
            {
                UserDTO user = allUsers.get(i);
                map.put(user.getId(), user);
            }
            
       return map;
    }
}
