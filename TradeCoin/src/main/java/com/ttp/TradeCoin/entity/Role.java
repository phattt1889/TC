/**
 * 
 */
package com.ttp.TradeCoin.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author phattt
 *
 */
@Entity
@Table(name="role")
public class Role {

	private Long id;
    private String name;
    private Set<User> users;
	/**
	 * @return the id
	 */
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	@ManyToMany(mappedBy="roles")
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
