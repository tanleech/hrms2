/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import sg.edu.ntu.hrms.dto.TitleDTO;

/**
 *
 * @author michael-PC
 */
public interface TitleDAO extends BaseDAO{
    
    List<TitleDTO> getAllTitles() throws Exception;

    void addTitle(TitleDTO title) throws Exception;

    TitleDTO getTitle(int id) throws Exception;

    TitleDTO getTitleByName(String descr) throws Exception;

    
}
