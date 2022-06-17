/**
 * 
 */
package com.firma.firma_user_ms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.firma.firma_user_ms.model.User;

/**
 * @author FredyRosero
 * La palabra `user` esta reservada, por lo que siempre es necesario las comillas dobles.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value="SELECT * FROM \"user\" u WHERE u.email = ?1",nativeQuery=true)
	public User findUserByEmail(String email);
	
	@Query(value="SELECT * FROM \"user\" u WHERE u.user_id = ?1",nativeQuery=true)
	public User findUserById(int userId);
}
