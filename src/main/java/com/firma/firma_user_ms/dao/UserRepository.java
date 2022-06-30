/**
 * 
 */
package com.firma.firma_user_ms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.firma.firma_user_ms.model.User;

/**
 * @author FredyRosero
 * La palabra `user` esta reservada, por lo que siempre es necesario las comillas dobles.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value="SELECT * FROM \"user\" u WHERE u.email = ?1",nativeQuery=true)
	public User selectByEmail(String email);
	
	@Query(value="SELECT * FROM \"user\" u WHERE u.user_id = ?1",nativeQuery=true)
	public User select(int userId);

	@Modifying
	@Transactional
	@Query(value="INSERT INTO \"user\"  (first_name, last_name, email, password) VALUES (?1, ?2, ?3, ?4);",nativeQuery=true)
	public Integer insert(String firstName, String lastName, String email, String password);
}
