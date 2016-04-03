/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sg.edu.ntu.hrms.service.EmployeeEditService;

/**
 *
 * @author michael-PC
 */
public class UploadEmpAction extends BaseAction implements ServletRequestAware{
       private HttpServletRequest request;
       private final EmployeeEditService svc = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
       

    public HttpServletRequest getRequest() {
        return request;
    }

   @Override
   public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
   
   public String getUploadForm(){
       
       return INPUT;
   }
   public String upLoad(){
        String action = request.getParameter("action");
        System.out.println("action: "+action);
        if(action==null || action.isEmpty())
        {
                FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//PriceDAO priceDAO = new PriceDAO();
                

		try {
			List<FileItem> fields = upload.parseRequest(request);
                        System.out.println("fields: "+fields);
                        svc.uploadEmp(fields);
                        
			//out.println("Number of fields: " + fields.size() + "<br/><br/>");
                        /*
			Iterator<FileItem> it = fields.iterator();
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				//store in webserver.
				String fileName = fileItem.getName();
				if(fileName!=null)
				{
					File file = new File(fileName);
					fileItem.write(file);
					System.out.println("File successfully saved as " + file.getAbsolutePath());
					
					//process file
					Reader in = new FileReader(file.getAbsolutePath());
					Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
					for (CSVRecord record : records) {
					    String name = record.get("<name>");
					    String login = record.get("<login>");
                                            String title = record.get("<title>");
                                            String email = record.get("<email>");
                                            String role  = record.get("<role>");
                                            String dept  = record.get("<department>");
                                            String joinDate = record.get("<joinDate>");
                                            String probDate = record.get("<probDate>");
                                            String annLeaveEnt = record.get("<leave_entitlement>");
                                            String annBal = record.get("<leave_bal>");
                                            String annMax = record.get("<leave_max>");
                                            String annCF = record.get("<leave_cf>");
                                            String med = record.get("<med_taken>");
                                            String oil = record.get("<oil_taken>");
                                            String unpaid = record.get("<unpaid_taken>");
                                            String child = record.get("<child_bal>");
                                            
                                            TitleDTO titleDto = titleBean.getTitleByName(title);
                                            RoleDTO roleDto = accessBean.getRole(role);
                                            DeptDTO deptDto = deptBean.getDepartment(dept);
                                            //create the user first
                                            UserDTO user = new UserDTO();
                                            user.setName(name);
                                            user.setLogin(login);
                                            user.setTitle(titleDto);
                                            user.setEmail(email);
                                            user.setDateJoin(Utility.format(joinDate,"dd/MM/yyyy"));
                                            user.setProbationDue(Utility.format(probDate, "dd/MM/yyyy"));
                                            //store in user table.
                                            userBean.createUser(user);
                                            //assign role
                                            userBean.assignRole(user, roleDto);
                                            //assign dept
                                            deptBean.assignEmployee(user, deptDto);
                                            
                                            //leave ent
                                            LeaveTypeDTO lvtypeDTO = leaveBean.getLeaveType("Annual");
                                            LeaveEntDTO  annualentDTO = new LeaveEntDTO();
                                            annualentDTO.setCurrent(Double.parseDouble(annLeaveEnt));
                                            annualentDTO.setBalance(Double.parseDouble(annBal));
                                            annualentDTO.setMax(Double.parseDouble(annMax));
                                            annualentDTO.setCarriedOver(Double.parseDouble(annCF));
                                            annualentDTO.setLeaveType(lvtypeDTO);
                                            //assign annual leave
                                            annualentDTO.setUser(user);
                                            leaveBean.addLeaveEnt(annualentDTO);
                                            //medical ent
                                            LeaveTypeDTO medTypeDTO = leaveBean.getLeaveType("Medical Leave");
                                            LeaveEntDTO  medentDTO = new LeaveEntDTO();
                                            medentDTO.setBalance(medTypeDTO.getDays()-Double.parseDouble(med));
                                            medentDTO.setCurrent(medTypeDTO.getDays());
                                            medentDTO.setUser(user);
                                            medentDTO.setLeaveType(medTypeDTO);
                                            leaveBean.addLeaveEnt(medentDTO);
                                            //oil ent
                                            LeaveTypeDTO oilTypeDTO = leaveBean.getLeaveType("Off-in-Lieu");
                                            LeaveEntDTO  oilentDTO = new LeaveEntDTO();
                                            oilentDTO.setBalance(oilTypeDTO.getDays()-Double.parseDouble(oil));
                                            oilentDTO.setCurrent(0);
                                            oilentDTO.setUser(user);
                                            oilentDTO.setLeaveType(oilTypeDTO);
                                            leaveBean.addLeaveEnt(oilentDTO);
                                            //unpaid
                                            LeaveTypeDTO unpaidTypeDTO = leaveBean.getLeaveType("Unpaid");
                                            LeaveEntDTO  unpaidentDTO = new LeaveEntDTO();
                                            unpaidentDTO.setBalance(unpaidTypeDTO.getDays()-Double.parseDouble(unpaid));
                                            unpaidentDTO.setCurrent(0);
                                            unpaidentDTO.setUser(user);
                                            unpaidentDTO.setLeaveType(unpaidTypeDTO);
                                            leaveBean.addLeaveEnt(unpaidentDTO);
                                            //child
                                            LeaveTypeDTO childTypeDTO = leaveBean.getLeaveType("Child Care");
                                            double cur = childTypeDTO.getDays();
                                            LeaveEntDTO  childentDTO = new LeaveEntDTO();
                                            childentDTO.setBalance(cur-Double.parseDouble(child));
                                            childentDTO.setCurrent(cur);
                                            childentDTO.setUser(user);
                                            childentDTO.setLeaveType(childTypeDTO);
                                            leaveBean.addLeaveEnt(childentDTO);
                                            
                                            
					}
                                    

				}
                                
                            
			}
                        */
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
       
       return SUCCESS;
   }

       

}
