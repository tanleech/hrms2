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
import javax.persistence.Transient;

/**
 *
 * @author Michael Tan
 */

@Entity  
@Table(name= "Title")  
public class TitleDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8270374548642111723L;

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
    @JoinColumn(name="Title_id")
    private List<UserDTO> employees;

    @Transient
    private DeptDTO dept;
    



    

    public DeptDTO getDept() {
        return dept;
    }

    public void setDept(DeptDTO dept) {
        this.dept = dept;
    }
    
    public List<UserDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserDTO> employees) {
        this.employees = employees;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public int getId() {
        return id;
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

}

