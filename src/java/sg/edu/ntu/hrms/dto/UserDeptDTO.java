package sg.edu.ntu.hrms.dto;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author Michael Tan
 */
@Entity  
@Table(name= "UserDept")  
public class UserDeptDTO implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8401819335811466167L;

	@Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="Dept_id")
    private DeptDTO dept;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDTO user;
    
    @Column(name="manager")
    private String manager;
    
    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeptDTO getDept() {
        return dept;
    }

    public void setDept(DeptDTO dept) {
        this.dept = dept;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
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

