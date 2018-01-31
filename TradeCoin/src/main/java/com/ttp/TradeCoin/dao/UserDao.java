/**
 * 
 */
package com.ttp.TradeCoin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttp.TradeCoin.entity.User;

/**
 * @author phattt
 *
 */
public interface UserDao extends JpaRepository<User, Long> {

	public User findUserByUserId(String userId);
}
