/**
 * 
 */
package com.firma.firma_user_ms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.firma.firma_user_ms.model.User;

/**
 * @author Marcos
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findUserByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.userId = ?1")
	public User findUserById(int userId);
}
