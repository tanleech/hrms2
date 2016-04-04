/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import sg.edu.ntu.hrms.dto.AuditDTO;
import sg.edu.ntu.hrms.service.AuditLogService;

/**
 *
 * @author michael-PC
 */
public class AuditLogAction extends BaseAction {
    
    private final AuditLogService logSvc = (AuditLogService)ctx.getBean(AuditLogService.class);
    
        private String dateRange;
    private InputStream inputStream;

    public String getDateRange() {
        return dateRange;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }
    
    public String execute()
    {
        System.out.println("dateRange: "+dateRange);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if(dateRange==null||dateRange.isEmpty())
        {
          Date current = new Date();  
          Calendar frmCal = Calendar.getInstance();
          frmCal.add(Calendar.MONTH, -480);
          String frmDate = formatter.format(frmCal.getTime());
          String toDate  = formatter.format(current);
          StringBuilder sb = new StringBuilder();
          //sb.append(frmDate+" - "+toDate);
          sb.append(frmDate).append(" - ").append(toDate);
          dateRange = sb.toString();
          /*
          request.setAttribute("dateRange",sb.toString());
          RequestDispatcher view = getServletContext().getRequestDispatcher("/auditLogList.jsp"); 
          view.forward(request,response);           
          */
          //return INPUT;
        }
        return INPUT;
    }
    
        public String json(){
            
          SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
          //json
          //Get the list 
          StringTokenizer st = new StringTokenizer(dateRange,"-");
          String stDate = st.nextToken().trim();
          String edDate = st.nextToken().trim();
	  try {

		Date fromDate = formatter.parse(stDate);
                Date toDate = formatter.parse(edDate);

                List<AuditDTO> auditList = logSvc.getAuditLog(fromDate,toDate);
                //convert to json array
                String jsonStr = convertToJson(auditList);
                /*
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                System.out.println("json: "+json);
                out.write(json);
                out.flush();
                 */
                 inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
  	  } catch (Exception e) {
		e.printStackTrace();
	  }

            return SUCCESS;
        }

     private String convertToJson(List<AuditDTO> auditList)
    {
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<auditList.size();i++)
        {
            AuditDTO audit = auditList.get(i);
            Date subDate = audit.getCreated();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String subDateStr = formatter.format(subDate);
            array.add(
            Json.createObjectBuilder()
                    .add("date",subDateStr)
                    .add("description",  audit.getDescr())
                    .add("employee", audit.getLogin().getName())
            );
            
        }
        return array.build().toString();
    }
}
    
