package in.freakydevs.dhobiexpress.Beans;

import in.freakydevs.dhobiexpress.Entity.UserDetails;
import in.freakydevs.dhobiexpress.api.RestRepository.UserDetailsRepo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckUserExistence {

	@Autowired
	UserDetailsRepo userDetailsRepo;

	public boolean checkEmail(String mail) throws Exception {
		UserDetails userDetails = userDetailsRepo.findOne(mail);
		return userDetails != null;
	}

	public boolean checkPhone(String phone) throws Exception {
		UserDetails userDetails = userDetailsRepo.findByUserPhone(phone);
		return userDetails != null;
	}


	public boolean checkUser(String user) throws Exception {

		if (user.contains("@")) {
			return checkEmail(user);
		}

		return checkPhone(user);

	}


}
