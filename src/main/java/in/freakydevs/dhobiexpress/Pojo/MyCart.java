package in.freakydevs.dhobiexpress.Pojo;

import java.io.Serializable;

public class MyCart implements Serializable {
	private String clothName;
	private int clothPrice;
	private int totalNumber;

	public MyCart(String clothName, int clothPrice, int totalNumber) {
		this.clothName = clothName;
		this.clothPrice = clothPrice;
		this.totalNumber = totalNumber;
	}

	public String getClothName() {
		return clothName;
	}

	public void setClothName(String clothName) {
		this.clothName = clothName;
	}

	public int getClothPrice() {
		return clothPrice;
	}

	public void setClothPrice(int clothPrice) {
		this.clothPrice = clothPrice;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
}
