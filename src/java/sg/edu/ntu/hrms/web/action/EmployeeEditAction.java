/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.web.action;

/**
 *
 * @author michael-PC
 */
public class EmployeeEditAction extends BaseAction{
    
    private String name;
    private String email;
    private String mgr;
    private String role;
    private String login;
    private String dateJoin;
    private String probDue;
    private String base;
    private String max;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getProbDue() {
        return probDue;
    }

    public void setProbDue(String probDue) {
        this.probDue = probDue;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
    
    public String execute()
    {
        System.out.println("name: "+name);
        return SUCCESS;
    }
    
}