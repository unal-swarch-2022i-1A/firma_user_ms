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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firma.firma_user_ms.context.AppContext;
import com.firma.firma_user_ms.dao.TokenRepository;
import com.firma.firma_user_ms.dao.UserRepository;
import com.firma.firma_user_ms.model.User;

/**
 * @author Marcos
 *
 */
@RestController
@RequestMapping("user-ms")
public class UserDataAPI {
	@Autowired
	private UserRepository usrRepo;
	@Autowired
	private TokenRepository toknRepo;

	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody Map<String, String> jsonObject) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppContext.class);
		User user = (User) appContext.getBean("userBean", jsonObject);
		((AnnotationConfigApplicationContext) appContext).close();
		usrRepo.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
}
