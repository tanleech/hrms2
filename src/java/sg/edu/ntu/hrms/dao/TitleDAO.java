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
    
    List<TitleDTO> getAllTitles();

    void addTitle(TitleDTO title);

    TitleDTO getTitle(int id);

    TitleDTO getTitleByName(String descr);

    
}
