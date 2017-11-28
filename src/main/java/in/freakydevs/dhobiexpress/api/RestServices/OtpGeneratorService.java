package in.freakydevs.dhobiexpress.api.RestServices;

import in.freakydevs.dhobiexpress.Beans.CheckUserExistence;
import in.freakydevs.dhobiexpress.Beans.DataBean;
import in.freakydevs.dhobiexpress.Beans.OtpResponse;
import in.freakydevs.dhobiexpress.Pojo.UserForm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpGeneratorService {

	@Autowired
	private DataBean dataBean;

	@Autowired
	private CheckUserExistence checkUserExistence;

	public OtpResponse generateOTP(UserForm form) {

		String otp = dataBean.getOtp();
		String returnData = "fail";
		String sendOTPURL = "http://api.msg91.com/api/sendotp.php?authkey=" + dataBean.getOTPAPIKEY()
				+ "&mobile=91" + form.getPhoneNo()
				+ "&message=Your%20otp%20is%20"
				+ otp + "&sender=DHCart&otp=" + otp;
		boolean isPhoneExist = true, isEmailExist = true;
		OtpResponse otpResponse = new OtpResponse();

		try {

			isPhoneExist = checkUserExistence.checkPhone(form.getPhoneNo());
			isEmailExist = checkUserExistence.checkEmail(form.getUserEmail());

			if (!isEmailExist && !isPhoneExist) {
				String responseData = dataBean.getURLGETData(sendOTPURL);
				System.out.println("Response" + responseData);
				JSONObject jsonObject = new JSONObject(responseData);
				returnData = jsonObject.getString("type");
				otpResponse.setSuccess(true);
				otpResponse.setMailExist(false);
				otpResponse.setPhoneExist(false);
				otpResponse.setHasOtpSent(returnData);
			} else {
				otpResponse.setSuccess(false);
				otpResponse.setMailExist(isEmailExist);
				otpResponse.setPhoneExist(isPhoneExist);
				otpResponse.setHasOtpSent(returnData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return otpResponse;
		}
		return otpResponse;
	}

	public String resendOTP(String phone) {
		String returnData;
		String resendOTPURL = "https://control.msg91.com/api/retryotp.php?authkey=" + dataBean.getOTPAPIKEY() + "&mobile=91" + phone + "&retrytype=voice";
		try {
			String responseData = dataBean.getURLGETData(resendOTPURL);
			JSONObject jsonObject = new JSONObject(responseData);
			returnData = jsonObject.getString("type");
			return returnData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public OtpResponse generateOTPwithPhone(String phone) {

		String otp = dataBean.getOtp();
		String returnData = "fail";
		String sendOTPURL = "http://api.msg91.com/api/sendotp.php?authkey=" + dataBean.getOTPAPIKEY()
				+ "&mobile=91" + phone
				+ "&message=Your%20otp%20is%20"
				+ otp + "&sender=DHCart&otp=" + otp;
		boolean isPhoneExist = false;
		OtpResponse otpResponse = new OtpResponse();

		try {

			isPhoneExist = checkUserExistence.checkPhone(phone);

			System.out.println(phone + " IsPhoneexist" + isPhoneExist);

			if (isPhoneExist) {
				String responseData = dataBean.getURLGETData(sendOTPURL);
				System.out.println("" + responseData);
				JSONObject jsonObject = new JSONObject(responseData);
				returnData = jsonObject.getString("type");
				otpResponse.setSuccess(true);
				otpResponse.setPhoneExist(true);
				otpResponse.setHasOtpSent(returnData);
			} else {
				otpResponse.setSuccess(false);
				otpResponse.setPhoneExist(false);
				otpResponse.setHasOtpSent(returnData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return otpResponse;
		}
		return otpResponse;
	}


}
