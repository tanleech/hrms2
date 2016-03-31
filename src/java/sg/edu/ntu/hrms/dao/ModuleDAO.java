/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dao;

import java.util.List;
import sg.edu.ntu.hrms.dto.ModuleDTO;

/**
 *
 * @author michael-PC
 */
public interface ModuleDAO extends BaseDAO {
        List<ModuleDTO> getAllModules() throws Exception;
}
