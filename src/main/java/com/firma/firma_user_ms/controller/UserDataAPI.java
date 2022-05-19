/**
 * 
 */
package com.firma.firma_user_ms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.firma_user_ms.context.AppContext;
import com.firma.firma_user_ms.dao.UserRepository;
import com.firma.firma_user_ms.model.User;

/**
 * @author Marcos
 *
 */
@RestController
@RequestMapping("users")
public class UserDataAPI {
	@Autowired
	private UserRepository usrRepo;

	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody Map<String, String> jsonObject) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppContext.class);
		User user = (User) appContext.getBean("userBean", jsonObject);
		((AnnotationConfigApplicationContext) appContext).close();
		usrRepo.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable int userId) {
		User user = usrRepo.findUserById(userId);
		usrRepo.delete(user);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable int userId,  @RequestBody Map<String, String> jsonObject) {
		User user = usrRepo.findUserById(userId);
		user.setFirstName(jsonObject.get("firstName"));
		user.setLastName(jsonObject.get("lastName"));
		user.setEmail(jsonObject.get("email"));
		user.setPassword(jsonObject.get("password"));
		usrRepo.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserInfo(@PathVariable int userId){
		return new ResponseEntity<>(usrRepo.findUserById(userId), HttpStatus.OK);
	}
	
}
