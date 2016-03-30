/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import sg.edu.ntu.hrms.service.EmployeeListService;

/**
 *
 * @author michael-PC
 */
public class EmployeeListAction extends BaseAction {
    
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
        if(dateRange==null||dateRange.isEmpty())
        {
            EmployeeListService svc = (EmployeeListService)ctx.getBean(EmployeeListService.class);
            String dtRange = svc.getInitialPrevMonths(480);
            setDateRange(dtRange);
            //return INPUT;
        }
        /*
        else
        {
            System.out.println("ajax triggered");
            EmployeeListService svc = (EmployeeListService)ctx.getBean(EmployeeListService.class);
            setEmpJson(svc.getEmpJSONFormat(dateRange));
            return null;
        }
        */
        return INPUT;
    }
    
    public String json() throws Exception
    {
        System.out.println("ajax triggered");
        EmployeeListService svc = (EmployeeListService)ctx.getBean(EmployeeListService.class);
        String jsonStr = svc.getEmpJSONFormat(dateRange);
         inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
        return SUCCESS;
    }
}
