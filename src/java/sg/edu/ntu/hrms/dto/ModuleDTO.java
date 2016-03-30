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
@Table(name= "Module")  
public class ModuleDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3186920052180165525L;

	@Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name="name")
    private String name;
    
    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="Module_id")
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
