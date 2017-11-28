package in.freakydevs.dhobiexpress.Beans;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ReferCodeMaker {

	public String getReferalCode(String email){

		String referalCode="";

		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 4) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();




		StringBuilder sb = new StringBuilder(email);
		for (int index = 0; index < 3; index++) {
			char c = sb.charAt(index);
			if (Character.isLowerCase(c)) {
				referalCode=referalCode+""+Character.toUpperCase(c);
			}else if (Character.isDigit(c)){
				referalCode=referalCode+""+c;
			}

		}

		referalCode=referalCode+saltStr;

		return referalCode;

	}

}
