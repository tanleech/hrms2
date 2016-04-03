/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import java.util.List;
import sg.edu.ntu.hrms.dto.TitleDTO;
import sg.edu.ntu.hrms.service.TitleService;

/**
 *
 * @author michael-PC
 */
public class TitleListAction extends BaseAction {
    
    private List<TitleDTO>titleList;
    private final TitleService titleService = (TitleService)ctx.getBean(TitleService.class);

    public List<TitleDTO> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<TitleDTO> titleList) {
        this.titleList = titleList;
    }

    
    public String execute()
    {
        titleList = titleService.getAllTitles();
        return SUCCESS;
    }
}
