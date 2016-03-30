package sg.edu.ntu.hrms.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Michael Tan
 */
@Entity  
@Table(name= "Accessrights")
public class AccessRightDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6234770530735444362L;

	@Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name="descr")
    private String descr;
    
    @Column(name="value")
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    
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
    
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
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
