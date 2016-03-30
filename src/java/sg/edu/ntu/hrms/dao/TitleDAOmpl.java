/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sg.edu.ntu.hrms.dto.TitleDTO;

/**
 *
 * @author michael-PC
 */
public class TitleDAOmpl extends BaseDAOImpl implements TitleDAO{

    @Override
    public List<TitleDTO> getAllTitles() {
        List results=null;
        Session session=null;
        try
        {
            session = sessionFactory.getCurrentSession();
            results = session.createQuery("SELECT DISTINCT title FROM com.sapuraglobal.hrms.dto.TitleDTO title left join fetch title.employees").list();
            

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return results;
    }

    @Override
    public void addTitle(TitleDTO title) {
        java.util.Date current = new java.util.Date();
        title.setCreated(current);
        title.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session =  sessionFactory.getCurrentSession();
            txn = session.beginTransaction();
            session.persist(title);
            txn.commit();
            
        }catch (Exception ex)
        {
            if(txn!=null)
            {
                txn.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public TitleDTO getTitle(int id) {
        List results=null;
        Session session=null;
        try
        {
            session = sessionFactory.getCurrentSession();
            Query qry = session.createQuery("SELECT title FROM com.sapuraglobal.hrms.dto.TitleDTO title WHERE title.id = :id");
            qry.setParameter("id", id);
            results = qry.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return (TitleDTO)results.get(0);
    }

    @Override
    public TitleDTO getTitleByName(String descr) {
        List results=null;
        Session session=null;
        TitleDTO data=null;
        try
        {
            session = sessionFactory.getCurrentSession();
            Query qry = session.createQuery("SELECT title FROM com.sapuraglobal.hrms.dto.TitleDTO title WHERE title.description = :descr");
            qry.setParameter("descr", descr);
            results = qry.list();
            if(results!=null&&results.size()>0)
            {
               data = (TitleDTO)results.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return data ;
    }
    
}
