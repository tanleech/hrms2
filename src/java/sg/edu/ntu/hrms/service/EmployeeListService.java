/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.hrms.dao.UserDAO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author michael-PC
 */
@Service("employeeService")
public class EmployeeListService {
    @Autowired
    private UserDAO userDAO;
    
    public String getInitialPrevMonths(int months)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date current = new Date();  
        Calendar frmCal = Calendar.getInstance();
        frmCal.add(Calendar.MONTH, -1*months);
        String frmDate = formatter.format(frmCal.getTime());
        String toDate  = formatter.format(current);
        StringBuilder sb = new StringBuilder();
        sb.append(frmDate).append(" - ").append(toDate);
        
        return sb.toString();
    }
            
    public String getEmpJSONFormat(String dtRange)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        StringTokenizer st = new StringTokenizer(dtRange,"-");
        String stDate = st.nextToken().trim();
        String edDate = st.nextToken().trim();
	try {

	    Date fromDate = formatter.parse(stDate);
            Date toDate = formatter.parse(edDate);

            List<UserDTO> userList = userDAO.getAllUsers(fromDate,toDate);
                //convert to json array
            String json = convertToJson(userList,new BeanHelper().getUserTab(userDAO));
            /*
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.write(json);
            out.flush();
            */
            return json;
  	  } catch (ParseException e) {
		e.printStackTrace();
                return null;
	  }
    }
    
    private String convertToJson(List<UserDTO> userList, HashMap map)
    {
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<userList.size();i++)
        {
            UserDTO user = userList.get(i);
            Date datejoin = user.getDateJoin();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String joinDateStr = formatter.format(datejoin);
            
            String approver = "";
            
             if(map.containsKey(user.getApprover()))
             {
                 UserDTO mgr = (UserDTO)map.get(user.getApprover());
                 approver = mgr.getName();
             }
            String deptDescr=""; 
            if(user.getDept()!=null)
            {
                deptDescr = user.getDept().getDept().getDescription();
            }
            System.out.println("approver: "+approver);
            array.add(
            Json.createObjectBuilder()
                    .add("id", user.getLogin())
                    .add("name",  user.getName())
                    .add("email", user.getEmail())
                    .add("dept", deptDescr)
                    //.add("dept", user.getDept().getDept().getDescription())
                    .add("title", user.getTitle().getDescription())
                    //.add("category","coming")
                    .add("manager",approver)
                    .add("datejoin",joinDateStr)
            );
            
        }
        return array.build().toString();
    }

    


}
