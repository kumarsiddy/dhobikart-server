package in.freakydevs.dhobiexpress.Beans;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenHandler {

	public String getmail(String token) {

		final String secret = "{{MythForSecretSID}}";
		Map<String, Object> claims = null;
		final JWTVerifier verifier = new JWTVerifier(secret);
		try {

			claims = verifier.verify(token);

		} catch (Exception e) {

		}

		return (String) claims.get("username");
	}

	public String getToken(String mail) {
		final String secret = "{{MythForSecretSID}}";

		final JWTSigner signer = new JWTSigner(secret);
		final HashMap<String, Object> claims = new HashMap<String, Object>();

		claims.put("username", mail);
		claims.put("verify", "isFreaky");

		final String jwt = signer.sign(claims);
		return jwt;
	}

	public boolean isValidToken(String token) {
		String s = null;
		final String secret = "{{MythForSecretSID}}";
		Map<String, Object> claims = null;
		final JWTVerifier verifier = new JWTVerifier(secret);
		try {

			claims = verifier.verify(token);
			s = (String) claims.get("verify");
		} catch (Exception e) {
			return false;
		}

		return s.equals("isFreaky");

	}


}
