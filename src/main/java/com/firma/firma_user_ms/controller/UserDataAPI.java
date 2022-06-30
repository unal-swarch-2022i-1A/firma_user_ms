/**
 * 
 */
package com.firma.firma_user_ms.controller;

import java.util.Map;

import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private UserRepository usrRepository;

	/**
	 * Obtener usuario por email
	 * Sobre /users?email=[email]
	 * @param filters "email=kpassfield0@cocolog-nifty.com"
	 * @return {User}
	 */
    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestParam Map<String, String> filters) {
		try {
			if(filters.size()<1) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} 		
			String email = filters.get("email");
			if(email==null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} 				
			User user = usrRepository.selectByEmail(email);
			if(user!=null) {
				ResponseEntity<User> responseEntity = new ResponseEntity<User>(user, HttpStatus.OK);
				return responseEntity;
			} else {
				ResponseEntity<User> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				return responseEntity;
			}		
		} catch (Exception e) {
			//TODO: handle exception
			System.err.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
    }		
	
	/**
	 * 
	 * @param jsonObject
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody Map<String, String> jsonObject) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppContext.class);
		User user = (User) appContext.getBean("userBean", jsonObject);
		((AnnotationConfigApplicationContext) appContext).close();
		try {
			usrRepository.insert(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
			return new ResponseEntity<>(user, HttpStatus.CREATED);				
		} catch (DataIntegrityViolationException e) {
			System.err.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable int userId) {
		User user = usrRepository.select(userId);
		usrRepository.delete(user);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable int userId,  @RequestBody Map<String, String> jsonObject) {
		User user = usrRepository.select(userId);
		user.setFirstName(jsonObject.get("firstName"));
		user.setLastName(jsonObject.get("lastName"));
		user.setEmail(jsonObject.get("email"));
		user.setPassword(jsonObject.get("password"));
		usrRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserInfo(@PathVariable int userId){
		try {		
			User user = usrRepository.select(userId);
			if(user!=null) {
				ResponseEntity responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
				return responseEntity;
			} else {
				ResponseEntity responseEntity = new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
				return responseEntity;
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}


	
}
