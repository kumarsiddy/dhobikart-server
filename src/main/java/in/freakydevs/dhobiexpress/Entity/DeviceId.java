package in.freakydevs.dhobiexpress.Entity;

import javax.persistence.*;

@Entity
@Table(name="DEVICE_ID")
public class DeviceId {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String mail;
	
	@Column(name = "deviceid")
	private String deviceId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
	

}
