/**
 * 
 */
package com.ttp.TradeCoin.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author phattt
 *
 */

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="userId")
    private String userId;
	
	@Column(name="userName")
    private String userName;
	
	@Column(name="password")
    private String password;
	
    @Transient
    private String passwordConfirm;
    
    @ElementCollection(targetClass=Role.class)
    private Set<Role> roles;
    
    @Column(name = "tokenId")
    private Long tokenId;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "tokenId")
    private TokenCurrency tokenCurrency;
    
    @Embedded
    private ChangeInfo changeInfo;
    
	/**
	 * @return the id
	 */
    @Column(name="user_id")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the passwordConfirm
	 */
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	/**
	 * @param passwordConfirm the passwordConfirm to set
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	/**
	 * @return the roles
	 */
	@ManyToMany
	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	public Set<Role> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the changeInfo
	 */
	public ChangeInfo getChangeInfo() {
		return changeInfo;
	}
	/**
	 * @param changeInfo the changeInfo to set
	 */
	public void setChangeInfo(ChangeInfo changeInfo) {
		this.changeInfo = changeInfo;
	}
	/**
	 * @return the tokenId
	 */
	public Long getTokenId() {
		return tokenId;
	}
	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	/**
	 * @return the tokenCurrency
	 */
	public TokenCurrency getTokenCurrency() {
		return tokenCurrency;
	}
	/**
	 * @param tokenCurrency the tokenCurrency to set
	 */
	public void setTokenCurrency(TokenCurrency tokenCurrency) {
		this.tokenCurrency = tokenCurrency;
	}
}
