/**
 * 
 */
package com.firma.firma_userdatamanagement_ms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.firma_userdatamanagement_ms.model.Token;

/**
 * @author Marcos
 *
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{

}
