/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import javax.servlet.http.HttpServletRequest;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;

/**
 *
 * @author michael-PC
 */
public class LeaveSettingsAction extends BaseAction {
    
    private String leaveType;
    private String ent;
    private String mandatory;
    private String annualIncre;
    private String cf;
    
    public String update()
    {
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
        type.setDescription(leaveType);
        type.setMandatory(mandatory);
        type.setDays(entVal);
        type.setAnnualIncre(increVal);
        type.setCarriedForward(cfVal);
        
        return type;
    }

}
