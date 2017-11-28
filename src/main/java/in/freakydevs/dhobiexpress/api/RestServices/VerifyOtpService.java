package in.freakydevs.dhobiexpress.api.RestServices;

import in.freakydevs.dhobiexpress.Beans.DataBean;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyOtpService {

	@Autowired
	private DataBean dataBean;

	public String verifyOtp(String otp, String phone) {

		String returnData = "error";
		String verifyOTPURL = "http://api.msg91.com/api/verifyRequestOTP.php?authkey=" + dataBean.getOTPAPIKEY() + "&mobile=91" + phone + "&otp=" + otp;

		try {
			String responseData = dataBean.getURLGETData(verifyOTPURL);
			System.out.println("Response" + responseData);
			JSONObject jsonObject = new JSONObject(responseData);
			returnData = jsonObject.getString("type");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}

}
