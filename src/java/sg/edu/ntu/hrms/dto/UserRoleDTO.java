package sg.edu.ntu.hrms.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Michael Tan
 */
@Entity  
@Table(name= "UserRole")  
public class UserRoleDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6870319220097937028L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name="created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name="modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="Role_id")
    private RoleDTO role;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDTO user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    
    

}
