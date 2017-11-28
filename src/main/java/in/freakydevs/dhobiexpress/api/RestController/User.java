package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Beans.LogInResponse;
import in.freakydevs.dhobiexpress.Beans.SignUpResponse;
import in.freakydevs.dhobiexpress.Pojo.LoginForm;
import in.freakydevs.dhobiexpress.Pojo.UserForm;
import in.freakydevs.dhobiexpress.api.RestServices.SignUpInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class User {

	@Autowired
	SignUpInService signUpInService;

	@RequestMapping(method = RequestMethod.POST, value = "/old")
	public LogInResponse getInfo(@RequestBody LoginForm loginForm) {
		return signUpInService.signIn(loginForm);
	}


	@RequestMapping(method = RequestMethod.POST, value = "/new")
	public SignUpResponse createAccount(@RequestBody UserForm userform) {
		return signUpInService.signUp(userform);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/verify/changepwd")
	public String changePassword(@RequestHeader("otp") String otp, @RequestBody LoginForm form) {
		return signUpInService.changePassword(otp,form);
	}
}
