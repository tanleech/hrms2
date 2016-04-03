/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import sg.edu.ntu.hrms.dto.LeaveTxnDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.service.LeaveSettingsService;
import sg.edu.ntu.hrms.service.LeaveTxnService;

/**
 *
 * @author michael-PC
 */
public class LeaveTxnAction extends BaseAction implements ServletRequestAware {
    
   private HttpServletRequest request;
   private String isManager;
   private List<LeaveTxnDTO> leaveTxnList;
   private List<LeaveTypeDTO>typeList;
   private InputStream inputStream;

   private final LeaveTxnService svc = (LeaveTxnService)ctx.getBean(LeaveTxnService.class);
   private final LeaveSettingsService leaveTypeSvc = (LeaveSettingsService)ctx.getBean(LeaveSettingsService.class);
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

   
    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public List<LeaveTxnDTO> getLeaveTxnList() {
        return leaveTxnList;
    }

    public void setLeaveTxnList(List<LeaveTxnDTO> leaveTxnList) {
        this.leaveTxnList = leaveTxnList;
    }

   
   @Override
   public void setServletRequest(HttpServletRequest request) {
		this.request = request;
    }

    public HttpServletRequest getServletRequest() {
		return this.request;
    }
    public String list()
    {
        HashMap accessTab = (HashMap)request.getSession().getAttribute("access");
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute("User");
        Map result = svc.list(accessTab, userDTO);
        isManager = (String)result.get("isManager");
        leaveTxnList = (List<LeaveTxnDTO>)result.get("leaveTxnList");
        System.out.println("txn size: "+leaveTxnList.size());
        request.setAttribute("leaveTxnlist", leaveTxnList); 
        request.setAttribute("isManager",isManager);
        request.setAttribute("typeList", typeList);
        typeList = leaveTypeSvc.getAllLeaveTypes();
        return INPUT;
    }
    public String getLeaveForm(){
        typeList = leaveTypeSvc.getAllLeaveTypes();
        return INPUT;
    }
    
    public String getLeaveBalance()throws Exception{
        
                String typeIdStr = request.getParameter("typeId");
                int typeId = (int) Double.parseDouble(typeIdStr);
                System.out.println("typeId: "+typeId);
                LeaveTypeDTO typeDTO= new LeaveTypeDTO();
                typeDTO.setId(typeId);
                UserDTO userDTO = (UserDTO)request.getSession().getAttribute("User");
                //HashMap map = new BeanHelper().getUserTab(userBean);
                System.out.println("approver: "+userDTO.getApprover());
                double bal = svc.getBalance(userDTO, typeDTO);
                
                //UserDTO mgrDTO = empSvc.getUser(userDTO.getApprover());
                //String appr = (String)map.get(userDTO.getApprover());
                //userDTO.setApproverName(appr);
                /*
                UserDTO mgrDTO = userBean.getUserFromId(userDTO.getApprover());
                if(mgrDTO!=null)
                {
                    userDTO.setApproverName(mgrDTO.getApproverName());
                }
                double balance = leaveBean.getLeaveBalance(typeDTO, userDTO);
                PrintWriter out = response.getWriter();
                out.write(String.valueOf(balance));
                out.flush();
                page="";
                */
                String jsonStr = String.valueOf(bal);
                inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
        
        return SUCCESS;
    }
    public String applyLeave(){
        
        String lvTypeId = request.getParameter("leaveType");
        String startDate = request.getParameter("startDate");
        Date startDt=null,endDt=null;
        try
        {
           startDt = Utility.format(startDate, "MM/dd/yyyy");
           String endDate = request.getParameter("endDate");
           endDt = Utility.format(endDate, "MM/dd/yyyy");
 
        }catch(ParseException pe)
        {
            pe.printStackTrace();
        }
  
        String startSlot = request.getParameter("start_slot");

        String endSlot = request.getParameter("end_slot");

        String days = request.getParameter("taken");
        
        Double daysTaken = Double.parseDouble(days);
        UserDTO user = (UserDTO)request.getSession().getAttribute("User");
        LeaveTxnDTO txn = new LeaveTxnDTO();
        txn.setDays(daysTaken);
        
        txn.setStart(startDt);
        txn.setEnd(endDt);
        
        txn.setStart_slot(startSlot);
        txn.setEnd_slot(endSlot);
        
        System.out.println("leaveTypeId="+lvTypeId);
        svc.applyLeave(txn, user, lvTypeId);
        return SUCCESS;
    }
    
    public String approveLeave(){

        String action = request.getParameter("action");
        System.out.println("action: "+action);

        String page = "/leaveTxn?action=list";

        String txnId = request.getParameter("txn");
        String userId = request.getParameter("userId");

        if(action!=null)
        {
            
            //UserDTO user = userBean.getUserFromId(Integer.parseInt(userId));
            if(action.equals("APPRV"))
            {
                //StatusDTO status = leaveBean.getStatus("approved");
                //leaveBean.approveLeave(Integer.parseInt(txnId), status.getId());
                svc.approve(txnId);
            }
            else if(action.equals("REJ"))
            {
                //add on to the leave balance
                String typeId = request.getParameter("typeId");
                //String userId = request.getParameter("userId");
                String daysStr = request.getParameter("days");
                
                //LeaveEntDTO entDTO = leaveBean.getLeaveEnt(Integer.parseInt(typeId), Integer.parseInt(userId));
                LeaveTypeDTO typeDTO = new LeaveTypeDTO();
                typeDTO.setId(Integer.parseInt(typeId));
                
                UserDTO userDTO = new UserDTO();
                userDTO.setId(Integer.parseInt(userId));
                svc.reject(txnId, typeId, userId, daysStr);
            }
        }
        return SUCCESS;
    }

    
}
