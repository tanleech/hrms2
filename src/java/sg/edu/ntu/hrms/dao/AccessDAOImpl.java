/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;

/**
 *
 * @author michael-PC
 */
@Repository
public class AccessDAOImpl extends BaseDAOImpl implements AccessDAO{
    @Override
    @Transactional
    public List<RoleDTO> getAllRoles() throws Exception{
        List results=null;
        results = getSession().createQuery("SELECT DISTINCT role FROM sg.edu.ntu.hrms.dto.RoleDTO role left join fetch role.userRoleList").list();
        return results;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    @Transactional
    public void addRole(RoleDTO roleDTO) throws Exception{
        java.util.Date current = new java.util.Date();
        roleDTO.setCreated(current);
        roleDTO.setModified(current);
        //set all the accesslist
        List<AccessDTO> accessList = roleDTO.getAccessList();
        for(int i=0;i<accessList.size();i++)
        {
            accessList.get(i).setCreated(current);
            accessList.get(i).setModified(current);
        }
        getSession().save(roleDTO);
    }

    @Override
    @Transactional
    public RoleDTO getRole(String descr) throws Exception {
        List results=null;
        Session session=null;
        RoleDTO data = null;
        session = getSession();
        String qry = "SELECT DISTINCT role FROM sg.edu.ntu.hrms.dto.RoleDTO role left join fetch role.accessList WHERE role.description = :descr";
        Query query = session.createQuery(qry);
        query.setParameter("descr", descr);
        results = query.list();
        if(results!=null&&results.size()>0)
        {
                data = (RoleDTO)results.get(0);
        }
        return data;
    }

    @Override
    @Transactional
    public void update(int roleId, List<AccessDTO>accessList) throws Exception {
        java.util.Date current = new java.util.Date();
        Session session = null;
        session =  getSession();
        String hql = "UPDATE sg.edu.ntu.hrms.dto.AccessDTO acr set acr.access = :acr, acr.modified = :modify WHERE acr.role.id = :roleId and acr.module.id=:moduleId";
        Query qry = session.createQuery(hql);
        //set all the accesslist
        for(int i=0;i<accessList.size();i++)
        {
            AccessDTO access = (AccessDTO) accessList.get(i);
            System.out.println("acr: "+access.getAccess());
            qry.setParameter("acr", access.getAccess());
            qry.setParameter("modify", current);
            qry.setParameter("roleId", roleId);
            qry.setParameter("moduleId", access.getModule().getId());
            System.out.println("role: "+roleId);
            int result =qry.executeUpdate();
                //txn.commit();
        }
        
    }

    @Override
    @Transactional
    public List<AccessDTO> getAccessRights(int roleId) throws Exception{
        List results=null;
        Session session=null;
        session = getSession();
        String qry = "SELECT access FROM sg.edu.ntu.hrms.dto.AccessDTO access WHERE access.role.id = :roleId";
        Query query = session.createQuery(qry);
        query.setParameter("roleId", roleId);
        results = query.list();
        return results;
    }
    
    
}
