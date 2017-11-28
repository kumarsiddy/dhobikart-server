package in.freakydevs.dhobiexpress.Pojo;

import java.io.Serializable;

public class MyAddress implements Serializable {
	private int id;
	private String name;
	private String phone;
	private String pin;
	private String city;
	private String state;
	private String area;
	private String landmark;
	private String alterphone;

	public MyAddress(String name, String phone, String pin, String city, String state, String area, String landmark, String alterphone) {
		this.name = name;
		this.phone = phone;
		this.pin = pin;
		this.city = city;
		this.state = state;
		this.area = area;
		this.landmark = landmark;
		this.alterphone = alterphone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getAlterphone() {
		return alterphone;
	}

	public void setAlterphone(String alterphone) {
		this.alterphone = alterphone;
	}
}
