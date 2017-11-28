package in.freakydevs.dhobiexpress.Beans;

import org.springframework.stereotype.Component;

@Component
public class OtpResponse {
	private boolean success;
	private boolean phoneExist;
	private boolean mailExist;
	private String hasOtpSent;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isPhoneExist() {
		return phoneExist;
	}

	public void setPhoneExist(boolean phoneExist) {
		this.phoneExist = phoneExist;
	}

	public boolean isMailExist() {
		return mailExist;
	}

	public void setMailExist(boolean mailExist) {
		this.mailExist = mailExist;
	}

	public String getHasOtpSent() {
		return hasOtpSent;
	}

	public void setHasOtpSent(String hasOtpSent) {
		this.hasOtpSent = hasOtpSent;
	}
}