/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.util.List;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.service.LeaveSettingsService;

/**
 *
 * @author michael-PC
 */
public class LeaveSettingsAction extends BaseAction {
    
    private String leaveTypeStr;
    private String ent;
    private String mandatory;
    private String annualIncre;
    private String cf;
    private List<LeaveTypeDTO>leaveTypelist;
    private String error;
    private String id;
    private LeaveTypeDTO leaveType;
    private LeaveSettingsService svc = (LeaveSettingsService)ctx.getBean(LeaveSettingsService.class);

    public LeaveTypeDTO getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeDTO leaveType) {
        this.leaveType = leaveType;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeaveTypeStr() {
        return leaveTypeStr;
    }

    public void setLeaveTypeStr(String leaveTypeStr) {
        this.leaveTypeStr = leaveTypeStr;
    }

    public String getEnt() {
        return ent;
    }

    public void setEnt(String ent) {
        this.ent = ent;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getAnnualIncre() {
        return annualIncre;
    }

    public void setAnnualIncre(String annualIncre) {
        this.annualIncre = annualIncre;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public List<LeaveTypeDTO> getLeaveTypelist() {
        return leaveTypelist;
    }

    public void setLeaveTypelist(List<LeaveTypeDTO> leaveTypelist) {
        this.leaveTypelist = leaveTypelist;
    }
    
    public String list()
    {
        leaveTypelist = svc.getAllLeaveTypes();
        
        return INPUT;
    }
    public String deleteSetting()
    {
        svc.deleteLeaveSetting(id);
        
        return SUCCESS;
    }
    public String getAdd()
    {
        
        return INPUT;
    }
    public String add()
    {
        LeaveTypeDTO type = prepare();
        try{
          svc.addLeaveSetting(type);
        }catch(Exception ex){
          error = ex.getMessage();
          return ERROR;
        }  
        return SUCCESS;
    }
    
    
    public String getUpdate()
    {
        leaveType = svc.getLeaveType(id);
        return INPUT;
    
    }
    public String update()
    {
        //String id = request.getParameter("id");
        System.out.println("id: "+id);
        LeaveTypeDTO leaveType = prepare();
        leaveType.setId(Integer.parseInt(id));
        svc.updateLeaveType(leaveType);
        return SUCCESS;
    }
    
    private LeaveTypeDTO prepare()
    {
        double cfVal=0,entVal=0,increVal=0;
        if(cf!=null&&!cf.isEmpty())
        {
            cfVal = Double.parseDouble(cf);
        }
        if(ent!=null&&!ent.isEmpty())
        {
            entVal = Double.parseDouble(ent);
        }
        if(annualIncre!=null&&!annualIncre.isEmpty())
        {
            increVal = Double.parseDouble(annualIncre);
        }
        LeaveTypeDTO type = new LeaveTypeDTO();
        type.setDescription(leaveTypeStr);
        type.setMandatory(mandatory);
        type.setDays(entVal);
        type.setAnnualIncre(increVal);
        type.setCarriedForward(cfVal);
        
        return type;
    }

}
