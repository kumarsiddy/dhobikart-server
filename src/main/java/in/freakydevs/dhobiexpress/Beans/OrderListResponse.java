package in.freakydevs.dhobiexpress.Beans;

import in.freakydevs.dhobiexpress.Entity.AddressList;
import in.freakydevs.dhobiexpress.Entity.CartList;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class OrderListResponse implements Serializable {
	private String orderId;
	private String time;
	private boolean isDelievered;
	private List<CartList> cartLists;
	private AddressList addressList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isDelievered() {
		return isDelievered;
	}

	public void setDelievered(boolean delievered) {
		isDelievered = delievered;
	}

	public List<CartList> getCartLists() {
		return cartLists;
	}

	public void setCartLists(List<CartList> cartLists) {
		this.cartLists = cartLists;
	}

	public AddressList getAddressList() {
		return addressList;
	}

	public void setAddressList(AddressList addressList) {
		this.addressList = addressList;
	}
}
