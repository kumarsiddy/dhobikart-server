package in.freakydevs.dhobiexpress.Beans;

import in.freakydevs.dhobiexpress.Entity.UserDetails;
import in.freakydevs.dhobiexpress.api.RestRepository.UserDetailsRepo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

@Component
public class SendSMS {

	@Autowired
	private DataBean dataBean;

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	//Multiple mobiles numbers separated by comma
	String mobiles;
	//Sender ID,While using route4 sender id should be 6 characters long.
	String senderId = "DHCart";
	//Your message to send, Add URL encoding here.
	String message = "";
	//define route
	String route = "4";

	//Prepare Url
	URLConnection myURLConnection = null;
	URL myURL = null;
	BufferedReader reader = null;

	@Async
	public String sendNow(String user, String message) throws Exception {

		if (user.contains("@")) {
			mobiles = "91" + getPhoneNo(user);
		} else {
			mobiles = "91" + user;
		}

		//encoding message
		String encoded_message = URLEncoder.encode(message);

		//Send SMS API
		String mainUrl = "https://control.msg91.com/api/sendhttp.php?";

//		https://control.msg91.com/api/sendhttp.php?authkey=
		// YourAuthKey&mobiles=919999999990,919999999999&message=message&sender=ABCDEF&route=4&country=0

		//Prepare parameter string
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("authkey=" + dataBean.getOTPAPIKEY())
				.append("&mobiles=" + mobiles)
				.append("&message=" + encoded_message)
				.append("&sender=" + senderId)
				.append("&route=" + route)
				.append("&country=91");

		//final string
		mainUrl = sbPostData.toString();

		return dataBean.getURLGETData(mainUrl);
	}

	private String getPhoneNo(String mail) {
		try {
			return userDetailsRepo.findOne(mail).getUserPhone();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


}
