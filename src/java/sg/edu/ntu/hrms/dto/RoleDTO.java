package sg.edu.ntu.hrms.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Michael Tan
 */
@Entity  
@Table(name= "Role") 
public class RoleDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2757274394410569293L;

	@Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "descr")
    private String description;
    
    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="Role_id")
    private List<UserRoleDTO> userRoleList;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="Role_id")
    private List<AccessDTO> accessList;


    
    

    
    public List<AccessDTO> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<AccessDTO> accessList) {
        this.accessList = accessList;
    }


    public int getId() {
        return id;
    }

    public List<UserRoleDTO> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRoleDTO> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }


    
}

