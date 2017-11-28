package in.freakydevs.dhobiexpress.Beans;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SignUpResponse implements Serializable {

	private boolean success;
	private String otpCorrect;
	private String token;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOtpCorrect() {
		return otpCorrect;
	}

	public void setOtpCorrect(String otpCorrect) {
		this.otpCorrect = otpCorrect;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
