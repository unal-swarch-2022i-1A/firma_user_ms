/**
 * 
 */
package com.firma.firma_user_ms.context;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.firma.firma_user_ms.model.Token;
import com.firma.firma_user_ms.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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

	@Bean(name = "tokenBean")
	@Scope("prototype")
	public Token tokenBean(int userId, String tokenStr) {
		Token token = new Token();
		token.setUserId(userId);
		token.setTokenStr(tokenStr);
		return token;
	}

	@Bean(name = "tokenStringBean")
	@Scope("prototype")
	public String generateRandomToken(String email) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		return Jwts.builder().setId("softtekJWT").setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

	}
}
