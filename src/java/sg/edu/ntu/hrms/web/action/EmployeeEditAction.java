/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;
import sg.edu.ntu.hrms.dto.TitleDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;
import sg.edu.ntu.hrms.dto.UserRoleDTO;
import sg.edu.ntu.hrms.service.AuditLogService;
import sg.edu.ntu.hrms.service.EmployeeEditService;

/**
 *
 * @author michael-PC
 */
public class EmployeeEditAction extends BaseAction implements ServletRequestAware{
    
    private String name;
    private String email;
    private String mgr;
    private String role;
    private String login;
    private String dateJoin;
    private String probDue;
    private String base;
    private String max;
    private String balance;
    private String action;

    private String mobile;
    private String office;
    
    private String title;
    private String dept;
    private List<DeptDTO>deptList;

    private List<TitleDTO>titleList;
    private List<UserDTO>mgrList;
    private List<RoleDTO>roleList;
    
    private UserDTO user;
    
    private LeaveEntDTO entAnnual;
    
    private String id;
    
    private String password;
    
    private EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
    private AuditLogService logSvc = (AuditLogService)ctx.getBean(AuditLogService.class);
    private HttpServletRequest request;
    
       public HttpServletRequest getRequest() {
        return request;
    }

   @Override
   public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    private String error;

    public LeaveEntDTO getEntAnnual() {
        return entAnnual;
    }

    public void setEntAnnual(LeaveEntDTO entAnnual) {
        this.entAnnual = entAnnual;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

        public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<UserDTO> getMgrList() {
        return mgrList;
    }

    public void setMgrList(List<UserDTO> mgrList) {
        this.mgrList = mgrList;
    }

    public List<RoleDTO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDTO> roleList) {
        this.roleList = roleList;
    }

    public List<TitleDTO> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<TitleDTO> titleList) {
        this.titleList = titleList;
    }

    public List<DeptDTO> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptDTO> deptList) {
        this.deptList = deptList;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getProbDue() {
        return probDue;
    }

    public void setProbDue(String probDue) {
        this.probDue = probDue;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
    
    public String execute()
    {
        System.out.println("name: "+name);
        return SUCCESS;
    }
    
    public String addEmp()
    {   
        String result="";
        System.out.println("Action: "+action);
        if(action==null||action.isEmpty())
        {
            action = "A";
            //EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
            populateDropDown(svc);
            result = INPUT;
        }
        else if(action.equals("A"))
        {
            System.out.println("Add");
            UserDTO emp = prepare();
            //EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
            try
            {
                svc.addEmployee(emp);
                //audit log
                UserDTO author = (UserDTO)request.getSession().getAttribute("User");
                logSvc.createLogRecord("Create Employee record: "+emp.getLogin(), author);
                result = SUCCESS;

            }catch (Exception ex)
            {
                System.out.println("error redirect back");
                setError(ex.getMessage());
                result = ERROR;
            }
            
        }
        return result;
    }
    private void populateDropDown(EmployeeEditService svc)
    {
        setDeptList(svc.getAllDepts());
        setTitleList(svc.getAllTitles());
        setRoleList(svc.getAllRoles());
        setMgrList(svc.getAllUsers());

    }
    public String updateEmp()
    {
        System.out.println("login: "+login);
        System.out.println("action: "+action);
        String result="";
        if(action==null||action.isEmpty())
        {
                //retrieve the user
                //EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
                UserDTO user = svc.getUser(login);
                setUser(user);
                populateDropDown(svc);
                setEntAnnual(user.getLeaveEnt().get(0));
                setAction("U");
                result= INPUT;
                
        }
        else if(action.equals("U"))
        {
            UserDTO emp = prepare();
            //EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
            emp.setId(Integer.parseInt(id));
            svc.updateEmployee(emp);
            //audit log
            UserDTO author = (UserDTO)request.getSession().getAttribute("User");
            logSvc.createLogRecord("Update Employee record: "+emp.getLogin(), author);
            result = SUCCESS;
        }
        
        
        return result;
    }
    
    private UserDTO prepare()
    {

        UserDTO user = new UserDTO();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(mobile);
        user.setOffice(office);
        user.setLogin(login);
        user.setPassword(password);
        //System.out.println("password: "+password);
        /*
        user.setMax(Double.parseDouble(max));
        user.setBase(Double.parseDouble(base));
        user.setBalance(Double.parseDouble(balance));
        */
        
        UserDTO mgrDto = new UserDTO();
        mgrDto.setId(Integer.parseInt(mgr));
        user.setApprover(mgrDto.getId());
        try
        {
           Date join = Utility.format(dateJoin,"MM/dd/yyyy");
           Date prob = Utility.format(probDue, "MM/dd/yyyy");
           user.setDateJoin(join);
           user.setProbationDue(prob);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        //TitleDTO titleDto = titleBean.getTitle(Integer.parseInt(title));
        TitleDTO titleDTO = new TitleDTO();
        titleDTO.setId(Integer.parseInt(title));
        user.setTitle(titleDTO);
        
        
        DeptDTO deptDto = new DeptDTO();
        deptDto.setId(Integer.parseInt(dept));
        UserDeptDTO userDept = new UserDeptDTO();
        userDept.setDept(deptDto);
        
        RoleDTO roleDto = new RoleDTO();
        System.out.println("selected Role: "+role);
        roleDto.setId(Integer.parseInt(role));
        UserRoleDTO userRole = new UserRoleDTO();
        userRole.setRole(roleDto);
        
        
        LeaveEntDTO ent = new LeaveEntDTO();
        ent.setCurrent(Double.parseDouble(base));
        ent.setMax(Double.parseDouble(max));
        double bal=0;
        if(balance!=null&&!balance.isEmpty())
        {
            bal = Double.parseDouble(balance);
        }
        ent.setBalance(bal);
        
        /*
        LeaveTypeDTO leaveType = .getLeaveType("Annual");
        ent.setLeaveType(leaveType);
        */
        
        ArrayList<LeaveEntDTO> entList = new ArrayList();
        entList.add(ent);
        user.setLeaveEnt(entList);
         
        user.setDept(userDept);
        user.setRole(userRole);
        
        return user;
        
    }

    
}
