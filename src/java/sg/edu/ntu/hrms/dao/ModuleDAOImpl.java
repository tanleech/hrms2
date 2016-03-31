/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.ntu.hrms.dto.ModuleDTO;

/**
 *
 * @author michael-PC
 */
@Repository
public class ModuleDAOImpl extends BaseDAOImpl implements ModuleDAO{

    @Override
    @Transactional
    public List<ModuleDTO> getAllModules() throws Exception{
        List results=null;
        Session session=null;
        session = getSession();
        results =  session.createQuery("SELECT DISTINCT module FROM sg.edu.ntu.hrms.dto.ModuleDTO module left join fetch module.accessList").list();
        return results;
    }
    
}
