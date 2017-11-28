package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Beans.OtpResponse;
import in.freakydevs.dhobiexpress.Pojo.LoginForm;
import in.freakydevs.dhobiexpress.Pojo.UserForm;
import in.freakydevs.dhobiexpress.api.RestServices.OtpGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("otp")
public class Otp {

	@Autowired
	private OtpGeneratorService otpGeneratorService;


	@RequestMapping(method = RequestMethod.POST, value = "/get")
	public OtpResponse sendOtp(@RequestBody UserForm form) {
		return otpGeneratorService.generateOTP(form);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/resend")
	public String resendOTP(@RequestHeader("phone") String phone) {
		return otpGeneratorService.resendOTP(phone);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getwithphone")
	public OtpResponse sendOTPPhone(@RequestHeader("phone") String phone) {
		return otpGeneratorService.generateOTPwithPhone(phone);
	}
}
