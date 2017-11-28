package in.freakydevs.dhobiexpress.api.RestServices;

import in.freakydevs.dhobiexpress.Beans.*;
import in.freakydevs.dhobiexpress.Entity.DeviceId;
import in.freakydevs.dhobiexpress.Entity.ReferAndEarn;
import in.freakydevs.dhobiexpress.Entity.UserDetails;
import in.freakydevs.dhobiexpress.Pojo.LoginForm;
import in.freakydevs.dhobiexpress.Pojo.UserForm;
import in.freakydevs.dhobiexpress.api.RestController.User;
import in.freakydevs.dhobiexpress.api.RestRepository.DeviceIdRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.ReferAndEarnRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.UserDetailsRepo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpInService {

	@Autowired
	private TokenHandler tokenHandler;

	@Autowired
	private ReferCodeMaker referCodeMaker;

	@Autowired
	private VerifyOtpService verifyOtpService;

	@Autowired
	private SendSMS sendSMS;

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	@Autowired
	private CheckUserExistence checkUserExistence;

	@Autowired
	private LogInResponse logInResponse;

	@Autowired
	private SignUpResponse signUpResponse;

	@Autowired
	private DeviceIdRepo deviceIdRepo;

	public SignUpResponse signUp(UserForm form) {

		String email = form.getUserEmail();
		String phone = form.getPhoneNo();
		String deviceId = form.getDeviceId();

		UserDetails userDetails = new UserDetails();

		String verifyOtpRespoString = verifyOtpService.verifyOtp(form.getOtp(), form.getPhoneNo());

		try {
			if (verifyOtpRespoString.equals("success")) {
				userDetails.setUserEmail(email);
				userDetails.setUserPhone(phone);
				userDetails.setName(form.getName());
				userDetails.setPassword(form.getPassword());
				userDetails.setGender(form.getGender());

				DeviceId deviceIdObject = new DeviceId();
				deviceIdObject.setDeviceId(deviceId);
				deviceIdObject.setMail(email);

				userDetailsRepo.save(userDetails);
				deviceIdRepo.save(deviceIdObject);

			} else {
				signUpResponse.setSuccess(false);
				signUpResponse.setOtpCorrect(verifyOtpRespoString);
				return signUpResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		signUpResponse.setToken(tokenHandler.getToken(email));
		signUpResponse.setSuccess(true);
		return signUpResponse;
	}

	public LogInResponse signIn(LoginForm loginForm) {
		try {
			String pwd;
			boolean isUserExist = checkUserExistence.checkUser(loginForm.getUserName());
			logInResponse.setUserExist(isUserExist);
			boolean isPassMatched=false;
			UserDetails userDetails=null;

			if (loginForm.getUserName().contains("@") && isUserExist) {
				userDetails = userDetailsRepo.findOne(loginForm.getUserName());
				pwd = userDetails.getPassword();
				isPassMatched=pwd.equals(loginForm.getPassWord());
			} else if (isUserExist) {
				userDetails = userDetailsRepo.findByUserPhone(loginForm.getUserName());
				pwd = userDetails.getPassword();
				isPassMatched=pwd.equals(loginForm.getPassWord());
			}

			if (isPassMatched){
				logInResponse.setSuccess(true);
				logInResponse.setName(userDetails.getName());
				logInResponse.setMail(userDetails.getUserEmail());
				logInResponse.setGender(userDetails.getGender());
				logInResponse.setToken(tokenHandler.getToken(userDetails.getUserEmail()));
			}else {
				logInResponse.setSuccess(false);
				logInResponse.setUserExist(isUserExist);
				logInResponse.setPassWrong(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return logInResponse;
	}

	public String changePassword(String otp, LoginForm form) {
		try {
			String verifyOtpRespoString = verifyOtpService.verifyOtp(otp, form.getUserName());
			if (verifyOtpRespoString.equals("success")) {
				UserDetails userDetails = userDetailsRepo.findByUserPhone(form.getUserName());
				userDetails.setPassword(form.getPassWord());
				userDetailsRepo.save(userDetails);
				return "success";
			} else
				return "wrongotp";
		} catch (Exception e) {
			return "fail";
		}
	}
}



