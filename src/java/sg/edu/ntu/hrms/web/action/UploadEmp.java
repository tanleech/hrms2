/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.service.EmployeeEditService;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/uploadEmp"}
)
public class UploadEmp extends HttpServlet {
    /*
    @EJB(beanName="UserBean")
    private UserBeanLocal userBean;
    
    @EJB(beanName="TitleBean")
    private TitleBeanLocal titleBean;
    
    @EJB(beanName="DeptBean")
    private DeptBeanLocal deptBean;
    
    @EJB(beanName="AccessBean")
    private AccessBeanLocal accessBean;
    
    @EJB(beanName="LeaveBean")
    private LeaveBeanLocal leaveBean;
    */ 
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        boolean hasAccess = false;
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        System.out.println("action: "+action);
        HttpSession session = request.getSession();
        HashMap accessTab = (HashMap)session.getAttribute("access");
        AccessDTO access =(AccessDTO)accessTab.get("System Log");
        if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        if(!hasAccess)
        {
             RequestDispatcher dispatcher = request.getRequestDispatcher("/noAccess.jsp");
   	     dispatcher.forward(request, response);
        }
        else
        {
        if(action==null || action.isEmpty())
        {
                FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//PriceDAO priceDAO = new PriceDAO();
                WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());


		try {
			List<FileItem> fields = upload.parseRequest(request);
                        EmployeeEditService svc  = (EmployeeEditService)ctx.getBean(EmployeeEditService.class);
                        svc.uploadEmp(fields);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
                        //System.out.println("redirect to employee");
                        response.sendRedirect("employee.action");
                        /*
			RequestDispatcher dispatcher = request.getRequestDispatcher("/employee");
			//request.setAttribute(Constants.TITLE, "Home");
			dispatcher.forward(request, response);
                        */
                        
		}
        }
        else
        {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/uploadEmp.jsp");
			//request.setAttribute(Constants.TITLE, "Home");
			dispatcher.forward(request, response);
            
        }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
