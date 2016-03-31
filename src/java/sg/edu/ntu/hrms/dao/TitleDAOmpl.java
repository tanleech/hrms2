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
import sg.edu.ntu.hrms.dto.TitleDTO;

/**
 *
 * @author michael-PC
 */
@Repository
public class TitleDAOmpl extends BaseDAOImpl implements TitleDAO{

    @Override
    @Transactional
    public List<TitleDTO> getAllTitles() throws Exception{
        List results=null;
        Session session=null;
        session = getSession();
        results = session.createQuery("SELECT DISTINCT title FROM sg.edu.ntu.hrms.dto.TitleDTO title left join fetch title.employees").list();
        return results;
    }

    @Override
    @Transactional
    public void addTitle(TitleDTO title) throws Exception{
        java.util.Date current = new java.util.Date();
        title.setCreated(current);
        title.setModified(current);
        Session session = null;
        session =  getSession();
        session.persist(title);
    }

    @Override
    @Transactional
    public TitleDTO getTitle(int id) throws Exception{
        List results=null;
        Session session=null;
        session = getSession();
        Query qry = session.createQuery("SELECT title FROM sg.edu.ntu.hrms.dto.TitleDTO title WHERE title.id = :id");
        qry.setParameter("id", id);
        results = qry.list();
        return (TitleDTO)results.get(0);
    }

    @Override
    @Transactional
    public TitleDTO getTitleByName(String descr) throws Exception{
        List results=null;
        Session session=null;
        TitleDTO data=null;
        session = getSession();
        Query qry = session.createQuery("SELECT title FROM sg.edu.ntu.hrms.dto.TitleDTO title WHERE title.description = :descr");
        qry.setParameter("descr", descr);
        results = qry.list();
        if(results!=null&&results.size()>0)
        {
            data = (TitleDTO)results.get(0);
        }
        return data ;
    }
    
}
