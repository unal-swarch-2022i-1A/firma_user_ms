/**
 * 
 */
package com.firma.firma_user_ms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Marcos
 *
 */
@Entity
@Table(name = "TOKN")
public class Token implements Serializable {

	static final long serialVersionUID = -3105051330553655401L;
	@Id
	@Column(name = "token_id")
	private Integer tokenId;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "token_str")
	private String tokenStr;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTokenStr() {
		return tokenStr;
	}

	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}

	public Integer getTokenId() {
		return tokenId;
	}

}
