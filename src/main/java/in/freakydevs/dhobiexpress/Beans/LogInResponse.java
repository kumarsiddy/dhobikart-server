package in.freakydevs.dhobiexpress.Beans;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class LogInResponse implements Serializable {

	private boolean success;
	private boolean isUserExist;
	private boolean isPassWrong;
	private String token;
	private String name;
	private String mail;
	private String gender;


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isUserExist() {
		return isUserExist;
	}

	public void setUserExist(boolean userExist) {
		isUserExist = userExist;
	}

	public boolean isPassWrong() {
		return isPassWrong;
	}

	public void setPassWrong(boolean passWrong) {
		isPassWrong = passWrong;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
