package in.freakydevs.dhobiexpress.Entity;

import javax.persistence.*;

@Entity
@Table(name = "CART_LIST")
public class CartList {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "clothname")
	private String clothName;
	@Column(name = "clothprice")
	private int clothPrice;
	@Column(name = "totalnumber")
	private int totalNumber;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private UserDetails userDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}
