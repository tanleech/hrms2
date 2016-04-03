/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.hrms.dao.AccessDAO;
import sg.edu.ntu.hrms.dao.ModuleDAO;
import sg.edu.ntu.hrms.dto.ModuleDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;

/**
 *
 * @author michael-PC
 */
@Service("accessService")
public class AccessService extends BaseService{
    @Autowired
    private AccessDAO accessDAO;
    @Autowired
    private ModuleDAO moduleDAO;
    
    
    public RoleDTO getRole(String descr){
        Session session = sessionFactory.openSession();
        RoleDTO roleData = null;
        try{
            accessDAO.setSession(session);
            roleData = accessDAO.getRole(descr);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }    
        return roleData;    
        
    }
    
    public void addRole(RoleDTO role)throws Exception{
        Session session = sessionFactory.openSession();
        Transaction txn = session.beginTransaction();
        try{
            accessDAO.setSession(session);
            if(accessDAO.getRole(role.getDescription())!=null)
            {
                throw new Exception("Duplicate role name not allowed.");
            }
            else
            {
                accessDAO.addRole(role);
                txn.commit();
            }  
        }catch(Exception ex){
            txn.rollback();
            ex.printStackTrace();
            throw ex;
        }finally{
            session.close();
        }

    }
    
        public void updateRole(RoleDTO role){
            Session session = sessionFactory.openSession();
            Transaction txn = session.beginTransaction();
            try{
                accessDAO.setSession(session);
                int id = accessDAO.getRole(role.getDescription()).getId();
               accessDAO.update(id, role.getAccessList());
               txn.commit();
            }catch(Exception ex){
                txn.rollback();
                ex.printStackTrace();
                //throw ex;
            }finally{
                session.close();
            }
    }
    public List<RoleDTO> getAllRoles()
    {
        Session session = sessionFactory.openSession();
        List<RoleDTO> roleList = null;
        try{
            accessDAO.setSession(session);
            roleList = accessDAO.getAllRoles();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }    
        return roleList;    
    }
    public List<ModuleDTO> getAllModules(){
        Session session = sessionFactory.openSession();
        List<ModuleDTO> moduleList = null;
        try{
            moduleDAO.setSession(session);
            moduleList = moduleDAO.getAllModules();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }    
        return moduleList;    
        
    }
            
}
