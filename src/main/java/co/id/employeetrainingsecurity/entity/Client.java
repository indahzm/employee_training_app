package co.id.employeetrainingsecurity.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

//import org.springframework.security.core.GrantedAuthority;

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

@SuppressWarnings("deprecation")
@Entity
@Table(name = "oauth_client")
public class Client implements ClientDetails, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Integer getAccessTokenExpired() {
		return accessTokenExpired;
	}

	public Integer getRefreshTokenExpired() {
		return refreshTokenExpired;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "access_token_expired")
	private Integer accessTokenExpired;
	
	@Column(name = "refresh_token_expired")
	private Integer refreshTokenExpired;
	
	@Column(name = "approved")
	private Boolean approved;
	
	@Column(name = "clientId")
	private String clientId;
	
	@Column(name = "client_secret")
	private String clientSecret;
	
	@Column(name = "grant_types")
	private String grantTypes;
	
	@Column(name = "redirect_uris")
	private String redirectUris;
	
	@Column(name = "scopes")
	private String scopes;
	
	@ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "oauth_client_role", 
		joinColumns = {@JoinColumn(name = "client_id") }, 
		inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<GrantedAuthority> authorities = new HashSet<>();

    public void setClientId(String clientId) { 
        this.clientId = clientId; 
    } 
    
	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		Set<String> resources = new HashSet<>(); 
        resources.add("oauth2-resource"); 
 
        return resources; 
	}

	public boolean isSecretRequired() {
		 return !StringUtils.isEmpty(clientSecret);
	}
	
	public void setClientSecret(String clientSecret) { 
        this.clientSecret = clientSecret; 
    }

	@Override
	public String getClientSecret() {
		return clientSecret;
	}
	public String getScopes() {
		return scopes;
	}
	
	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	@Override
	public boolean isScoped() {
		return !StringUtils.isEmpty(scopes); 
	}

	@Override
	public Set<String> getScope() {
		Set<String> scope = new HashSet<>(); 
		 
        if (isScoped()) { 
            scope = new HashSet<>(Arrays.asList(scopes.split("\\s"))); 
        } 
 
        return scope; 
	}
	
	public String getGrantTypes() {
		return grantTypes;
	}
	
	public void setGrantTypes(String grantTypes) {
		this.grantTypes = grantTypes;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		if (null != grantTypes) { 
            return new HashSet<>(Arrays.asList(grantTypes.split("\\s"))); 
        } 
        return null;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
        if (null != redirectUris) { 
            return new HashSet<>(Arrays.asList(redirectUris.split("\\s"))); 
        } 
        return null; 
	}
	
	public void setAuthorities(Set<GrantedAuthority> authorities) { 
		this.authorities = authorities;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
    public String getRedirectUris() { 
        return redirectUris; 
    }

    public void setRedirectUris(String redirectUris) { 
        this.redirectUris = redirectUris; 
    } 

    public void setAccessTokenExpired(Integer accessTokenExpired) { 
        this.accessTokenExpired = accessTokenExpired; 
    }
    
	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenExpired;
	}
	
    public void setRefreshTokenExpired(Integer refreshTokenExpired) { 
        this.refreshTokenExpired = refreshTokenExpired; 
    } 
    
    public boolean isApproved() { 
        return approved; 
    } 
 
    public void setApproved(boolean approved) { 
        this.approved = approved; 
    }

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenExpired;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return approved;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return new HashMap<>();
	}
	
}
