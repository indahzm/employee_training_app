package co.id.employeetrainingsecurity.entity;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_role")
//        uniqueConstraints = { 
//                @UniqueConstraint( 
//                        name = "role_name_and_type", 
//                        columnNames = {"type", "name"} 
//                ) 
//        } 
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    private List<RolePath> rolePaths; 
 
    @JsonIgnore 
    @ManyToMany(targetEntity = User.class, mappedBy = "roles",fetch = FetchType.LAZY) 
    private List<User> users; 

	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RolePath> getRolePaths() {
		return rolePaths;
	}

	public void setRolePaths(List<RolePath> rolePaths) {
		this.rolePaths = rolePaths;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
