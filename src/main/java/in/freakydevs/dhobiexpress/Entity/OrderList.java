package in.freakydevs.dhobiexpress.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_DETAILS")
public class OrderList {

	@Id
	@Column(name = "orderid")
	String orderId;
	@Column(name = "totalprice")
	int totalPrice;
	String time;
	@Column(name = "isdelivered")
	boolean isDelivered;
	@Column(name = "addressid")
	int addressId;
	@Column(name = "iscancelled")
	boolean isCancelled;

	public OrderList() {
	}

	public OrderList(String orderId, int totalPrice, String time, boolean isDelivered, int addressId) {
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.time = time;
		this.isDelivered = isDelivered;
		this.addressId = addressId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean delivered) {
		isDelivered = delivered;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}
}
