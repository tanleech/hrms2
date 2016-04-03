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
import sg.edu.ntu.hrms.dao.TitleDAO;
import sg.edu.ntu.hrms.dto.TitleDTO;

/**
 *
 * @author michael-PC
 */
@Service("titleService")
public class TitleService extends BaseService{
    @Autowired
    private TitleDAO titleDAO;
    
    public List<TitleDTO> getAllTitles()
    {
        Session session = sessionFactory.openSession();
        List<TitleDTO> titleList = null;
        try{
            titleDAO.setSession(session);
            titleList = titleDAO.getAllTitles();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            session.close();
        }    
        return titleList;    
    }
    public void addTitle(String name)throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            titleDAO.setSession(session);
            TitleDTO titleDTO = new TitleDTO();
            titleDTO.setDescription(name);
            if(titleDAO.getTitleByName(name)!=null)
            {
                throw new Exception ("Duplicate Title name is not allowed");
            }
            else
            {
                    titleDAO.addTitle(titleDTO);
            }
            txn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            txn.rollback();
            throw ex;
        }finally{
            session.close();
        } 
    }
}
