/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

import sg.edu.ntu.hrms.service.TitleService;

/**
 *
 * @author michael-PC
 */
public class TitleAddAction extends BaseAction{
    
    private String name;
    private final TitleService titleService = (TitleService)ctx.getBean(TitleService.class);
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getAddTitle()
    {
        return INPUT;
    }
    
    public String addTitle()
    {
        try{
            
         titleService.addTitle(name);
        }catch(Exception ex){
          //ex.printStackTrace();
          error = ex.getMessage();
          return ERROR;
        }
        return SUCCESS;
    }
}
