package com.face.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6604666667476391713L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String password;
	@Transient
	private long expires;
	@JSONField(serialize = false)
	@NotNull
	@Column(name = "ACCOUNT_EXPIRED", nullable = false)
	private boolean accountExpired;
	@JSONField(serialize = false)
	@NotNull
	@Column(name = "ACCOUNT_LOCKED", nullable = false)
	private boolean accountLocked;
	@JSONField(serialize = false)
	@NotNull
	@Column(name = "CREDENTIALS_EXPIRED", nullable = false)

	private boolean credentialsExpired;
	@JSONField(serialize = false)
	@NotNull
	@Column(name = "ACCOUNT_ENABLED", nullable = false)
	private boolean accountEnabled;
	@JSONField(serialize = false)
	@NotNull
	@Column(name = "SECRET", nullable = false)
	private boolean secret;
	@Transient
	private String newPassword;
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<UserAuthority> authorities;

	@JSONField(serialize = false)
	@OneToOne(mappedBy = "user",cascade = {CascadeType.ALL})
	private UserDetailInfoEntity userDetailInfoEntity;


//	@Column(name = "COMPANY_ID", nullable = true)
//	private Long companyId;

	public User() {
		this.userDetailInfoEntity=new UserDetailInfoEntity(this);
	}
	public User(String username) {
		this.username = username;
		this.userDetailInfoEntity=new UserDetailInfoEntity(this);
	}


	public User(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
		this.userDetailInfoEntity=new UserDetailInfoEntity(this);

	}
	@JsonIgnore
	public UserDetailInfoEntity getUserDetailInfoEntity() {
		return userDetailInfoEntity;
	}
	public void setUserDetailInfoEntity(UserDetailInfoEntity userDetailInfoEntity) {
		this.userDetailInfoEntity = userDetailInfoEntity;
	}
	//	private TabCompany company;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	@Override
	@JsonIgnore
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}

	public void  setAuthorities(Set<UserAuthority> userAuthorities){
		authorities=userAuthorities;
	}
	// Use Roles as external API
	public Set<UserRole> getRoles() {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				roles.add(UserRole.valueOf(authority));
			}
		}
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		for (UserRole role : roles) {
			grantRole(role);
		}
	}

	public void grantRole(UserRole role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(role.asAuthorityFor(this));
	}

	public void revokeRole(UserRole role) {
		if (authorities != null) {
			authorities.remove(role.asAuthorityFor(this));
		}
	}

	public boolean hasRole(UserRole role) {
		return authorities.contains(role.asAuthorityFor(this));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}
	@JsonIgnore
	public boolean isSecret() {
		return secret;
	}

	public void setSecret(boolean secret) {
		this.secret = secret;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return !accountEnabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

//	public Long getCompanyId() {
//		return companyId;
//	}
//
//	public void setCompanyId(Long companyId) {
//		this.companyId = companyId;
//	}

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//	@JoinColumn(name = "COMPANY_ID_F", nullable = false, insertable = false, updatable = false)
//	public TabCompany getCompany() {
//		return company;
//	}
//
//	public void setCompany(TabCompany company) {
//		this.company = company;
//	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUsername();
	}

}
