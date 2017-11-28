package in.freakydevs.dhobiexpress.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REFER_EARN")
public class ReferAndEarn {
	
	@Id
	private String email;
	
	@Column(name = "refercode")
	private String referCode;
	
	@Column(name = "walletmoney")
	private int walletMoney;
	
	@Column(name = "noofreferal")
	private int noOfReferal;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReferCode() {
		return referCode;
	}

	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}

	public int getWalletMoney() {
		return walletMoney;
	}

	public void setWalletMoney(int walletMoney) {
		this.walletMoney = walletMoney;
	}

	public int getNoOfReferal() {
		return noOfReferal;
	}

	public void setNoOfReferal(int noOfReferal) {
		this.noOfReferal = noOfReferal;
	}

}
