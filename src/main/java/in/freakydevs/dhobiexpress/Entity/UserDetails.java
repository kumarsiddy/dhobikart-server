package in.freakydevs.dhobiexpress.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {
	@Id
	@Column(name = "useremail")
	public String userEmail;
	@Column(name = "userphone", unique = true)
	public String userPhone;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}


	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "USER_ADDRESS",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID")
	)

	private Collection<AddressList> addressList = new ArrayList<AddressList>();

	public Collection<AddressList> getAddressList() {
		return addressList;
	}

	public void setAddressList(Collection<AddressList> addressList) {
		this.addressList = addressList;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "USER_ORDER",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ORDER_ID")
	)

	private Collection<OrderList> orderList = new ArrayList<OrderList>();

	public Collection<OrderList> getOrderList() {
		return orderList;
	}

	public void setOrderList(Collection<OrderList> orderList) {
		this.orderList = orderList;
	}


	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "USER_CART",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "CART_ID")
	)

	private Collection<CartList> cartList = new ArrayList<CartList>();

	public Collection<CartList> getCartList() {
		return cartList;
	}

	public void setCartList(Collection<CartList> cartList) {
		this.cartList = cartList;
	}


	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
