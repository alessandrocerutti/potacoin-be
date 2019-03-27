package it.pota.coin.potacoin.util;

import java.security.Key;
import java.sql.Timestamp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SecurityUtil {
	private static  Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	
	public static String prepareToken(String id, String tipologia) {
		return Jwts.builder().setSubject(id).claim("time", new Timestamp(System.currentTimeMillis())).claim("tipologia", tipologia).signWith(key).compact();
	}

	public static boolean controllaToken(String tkn) {
		Jws<Claims> jws;
		
		System.out.println(tkn);
		
		try {
			jws = Jwts.parser() // (1)
					.setSigningKey(key) // (2)
					.parseClaimsJws(tkn); // (3)
			return true;
			// we can safely trust the JWT

		} catch (JwtException e) { // (4)
			return false;
		}
	}

	public static int getTokenBody(String tkn) {
		Jws<Claims> jws;
		try {
			jws = Jwts.parser() // (1)
					.setSigningKey(key) // (2)
					.parseClaimsJws(tkn); // (3)
			return Integer.parseInt(jws.getBody().getSubject());
			// we can safely trust the JWT

		} catch (JwtException e) { // (4)
			return 0;
		}
	}

}
