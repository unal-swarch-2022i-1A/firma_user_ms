/**
 * 
 */
package com.firma.firma_user_ms.context;

import java.util.Map;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


import com.firma.firma_user_ms.model.User;


/**
 * @author Marcos
 *
 */
@Configuration
public class AppContext {

	@Bean(name = "userBean")
	@Scope("prototype")
	public User userBean(Map<String, String> jsonObject) {
		User user = new User();
		user.setFirstName(jsonObject.get("firstName"));
		user.setLastName(jsonObject.get("lastName"));
		user.setEmail(jsonObject.get("email"));
		user.setPassword(jsonObject.get("password"));
		return user;
	}

}
