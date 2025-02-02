package co.id.employeetrainingsecurity.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_user")
public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@Column(name = "not_expired")
	private boolean accountNonExpired = true;
	
	@JsonIgnore
	@Column(name = "not_locked")
	private boolean accountNonLocked = true;
	
	@JsonIgnore
	@Column(name = "credential_not_expired")
	private boolean credentialsNonExpired;
	
	@JsonIgnore
	@Column(name = "enabled")
	private boolean enabled = false;
	
	@JsonIgnore
	@Column(name = "expired_verify_token")
	private Date expiredVerifyToken;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "otp")
	private String otp;
	
	@Column(name = "otp_expired_date")
	private Date otpExpiredDate;
	
	@JsonIgnore
	@Column(name = "password")
	private String password;
	
	@Column(name = "username", unique = true)
	private String username;
	
	@JsonIgnore
	@Column(name = "verify_token")
	private String verifyToken;

	@ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
    @JoinTable(name = "oauth_user_role", 
            joinColumns = {@JoinColumn(name = "user_id")}, 
            inverseJoinColumns = {@JoinColumn(name = "role_id")}) 
    private List<Role> roles = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getExpiredVerifyToken() {
		return expiredVerifyToken;
	}

	public void setExpiredVerifyToken(Date expiredVerifyToken) {
		this.expiredVerifyToken = expiredVerifyToken;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getOtpExpiredDate() {
		return otpExpiredDate;
	}

	public void setOtpExpiredDate(Date otpExpiredDate) {
		this.otpExpiredDate = otpExpiredDate;
	}

	public String getVerifyToken() {
		return verifyToken;
	}

	public void setVerifyToken(String verifyToken) {
		this.verifyToken = verifyToken;
	}

}
