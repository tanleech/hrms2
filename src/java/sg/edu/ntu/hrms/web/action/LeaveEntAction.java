/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.service.LeaveEntService;

/**
 *
 * @author michael-PC
 */
public class LeaveEntAction extends BaseAction{
    
    private String annualAccrued;
    private String cf;
    private String bal;
    private String computedCF;
    
    private UserDTO user;
    private LeaveEntDTO entAnnual;
    private String login;
    private List<LeaveTypeDTO> typeList;
    private String typeId;
    private String days;
    private String userId;
    private String entId;

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }
    
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
    
    
    
    

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    
    public List<LeaveTypeDTO> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<LeaveTypeDTO> typeList) {
        this.typeList = typeList;
    }
    
    

    public String getAnnualAccrued() {
        return annualAccrued;
    }

    public void setAnnualAccrued(String annualAccrued) {
        this.annualAccrued = annualAccrued;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public String getComputedCF() {
        return computedCF;
    }

    public void setComputedCF(String computedCF) {
        this.computedCF = computedCF;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LeaveEntDTO getEntAnnual() {
        return entAnnual;
    }

    public void setEntAnnual(LeaveEntDTO entAnnual) {
        this.entAnnual = entAnnual;
    }

    public List<LeaveEntDTO> getEntList() {
        return entList;
    }

    public void setEntList(List<LeaveEntDTO> entList) {
        this.entList = entList;
    }
    private List<LeaveEntDTO> entList;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    private LeaveEntService svc = (LeaveEntService)ctx.getBean(LeaveEntService.class);
    public String execute()
    {
        //retrieve leave entitlement
        Map map = svc.getLeaveEntitlement(getLogin());
        entAnnual=(LeaveEntDTO)map.get("entAnnual");
        System.out.println("bal: "+entAnnual.getBalance());
        user = (UserDTO)map.get("user");
        entList = (List<LeaveEntDTO>)map.get("entList");
        annualAccrued = ((Double)map.get("accured")).toString();
        return INPUT;
    }
    public String getAddEnt()
    {
        System.out.println("action is A");
        Map<String,Object> map = svc.getLeaveEntAdd(login);
        setUser((UserDTO)map.get("user"));
        setTypeList((List<LeaveTypeDTO>)map.get("typeList"));
        
        return INPUT;

    }
    public String getLeaveDaysJson()throws Exception
    {
        //String typeId = request.getParameter("typeId");
        String jsonStr;             
        int id = (int) Double.parseDouble(typeId);
        if(id!=0)
        {    
            LeaveTypeDTO type = svc.getLeaveSetting(id);//leaveBean.getLeaveSetting(id);
            jsonStr = String.valueOf(type.getDays());
            
        }
        else
        {
            //out.write("");
            jsonStr ="";
        }
        inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
        return SUCCESS;
        
    }
    
    public String assign()
    {
        svc.assign(login, typeId, Double.parseDouble(days));
        return SUCCESS;
    }
    
    public String unassign()
    {
       svc.unassign(Integer.parseInt(entId), Integer.parseInt(userId));
       return SUCCESS; 
    }
    
    
}
