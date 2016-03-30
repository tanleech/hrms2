/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import org.hibernate.Session;
import sg.edu.ntu.hrms.dto.ModuleDTO;

/**
 *
 * @author michael-PC
 */
public class ModuleDAOImpl extends BaseDAOImpl implements ModuleDAO{

    @Override
    public List<ModuleDTO> getAllModules() {
        List results=null;
        Session session=null;
        try
        {
            session = sessionFactory.getCurrentSession();
            results =  session.createQuery("SELECT DISTINCT module FROM com.sapuraglobal.hrms.dto.ModuleDTO module left join fetch module.accessList").list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return results;
    }
    
}
