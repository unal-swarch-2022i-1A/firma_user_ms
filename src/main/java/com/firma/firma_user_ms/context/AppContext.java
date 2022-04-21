/**
 * 
 */
package com.firma.firma_user_ms.context;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.firma.firma_user_ms.model.Token;
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
		User user= new User();
		user.setFirstName(jsonObject.get("firstName"));
		user.setLastName(jsonObject.get("lastName"));
		user.setEmail(jsonObject.get("email"));
		user.setPassword(jsonObject.get("password"));
		return user;
	}
	
	@Bean(name = "tokenBean")
	@Scope("prototype")
	public Token tokenBean(List<String> values) {
		Token token= new Token();
		token.setUserId(Integer.valueOf(values.remove(0)));
		token.setTokenStr(values.remove(0));
		return token;
	}
}
