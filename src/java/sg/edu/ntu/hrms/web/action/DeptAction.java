/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;
import sg.edu.ntu.hrms.service.DeptService;
import sg.edu.ntu.hrms.service.EmployeeEditService;

/**
 *
 * @author michael-PC
 */
public class DeptAction extends BaseAction {
    
    private List<DeptDTO>deptList;
    private String dept;
    private String manager;
    private List<UserDTO>usrList;
    private List<UserDTO>employeeList;
    private InputStream inputStream;
    private UserDeptDTO userDept;
    private String empLogin;
    private String error;
    private String userId;
    private String action;
    private String oldName;

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
    
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEmpLogin() {
        return empLogin;
    }

    public void setEmpLogin(String empLogin) {
        this.empLogin = empLogin;
    }
    
    

    public UserDeptDTO getUserDept() {
        return userDept;
    }

    public void setUserDept(UserDeptDTO userDept) {
        this.userDept = userDept;
    }
    
    

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<UserDTO> getUsrList() {
        return usrList;
    }

    public void setUsrList(List<UserDTO> usrList) {
        this.usrList = usrList;
    }

    public List<UserDTO> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<UserDTO> employeeList) {
        this.employeeList = employeeList;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
    
    

    public List<DeptDTO> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptDTO> deptList) {
        this.deptList = deptList;
    }
    
    private EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
    private DeptService deptSvc = (DeptService)ctx.getBean(DeptService.class);
    public String deptList()
    {
        List<DeptDTO> deptList = svc.getAllDepts();//deptBean.getAllDepts();
        setDeptList(deptList);
        return SUCCESS;
    }
    
    public String deptEdit()
    {
        Map<String,Object> result = deptSvc.getDepartmentWithUsers(dept);
        userDept =  (UserDeptDTO)result.get("manager");
        setUsrList((List<UserDTO>)result.get("usrList"));
        setEmployeeList((List<UserDTO>)result.get("employeeList"));
        return SUCCESS;

    }
    
    public String getUpdateDept(){
        
        return INPUT;
    }
    
    public String assignMgr()throws Exception
    {
        System.out.println("mgr: "+manager);
        String jsonStr="";
        deptSvc.assignMgr(manager, dept);
        inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
        return SUCCESS;
       
    }
    public String getAssignEmp()
    {
        usrList = svc.getAllUsers();
        return INPUT;

    }
    
    public String assignEmp()
    {
        try{
          deptSvc.assignEmp(empLogin, dept);
        }catch(Exception ex){
            error = ex.getMessage();
            usrList = svc.getAllUsers();
            return ERROR;
        }
        return SUCCESS;
    }
    public String unassignEmp()
    {
        /*
        String userId = request.getParameter("userId");
        DeptDTO deptDto = deptBean.getDepartment(dept);
        deptBean.unassignEmployee(Integer.parseInt(userId), deptDto.getId());
        page = "/deptEdit?action=U&dept="+dept;                    
        */
        deptSvc.unassignEmp(userId, dept);
        return SUCCESS;
    }
    public String getAddDept()
    {
        return INPUT;
    }
    public String addDept()
    {
        try{
         deptSvc.addDept(dept);
        }catch(Exception ex){
            ex.printStackTrace();
            error = ex.getMessage();
            return ERROR;
        }
        return SUCCESS;    
    }
    
    public String updateDept()
    {
        try{
         deptSvc.updateDept(oldName,dept);
        }catch(Exception ex){
            ex.printStackTrace();
            error = ex.getMessage();
            return ERROR;
        }
        return SUCCESS;    
        
    }
}
