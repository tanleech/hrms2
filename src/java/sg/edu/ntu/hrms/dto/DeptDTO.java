package sg.edu.ntu.hrms.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
/**
 *
 * @author Michael Tan
 */
@Entity  
@Table(name= "Dept")  
public class DeptDTO implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7901254653498282267L;

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
    
    @OneToMany(cascade=CascadeType.MERGE)
    @JoinColumn(name="Dept_id")
    private List<UserDeptDTO> employees;
    

    public List<UserDeptDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserDeptDTO> employees) {
        this.employees = employees;
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

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    
    
}

