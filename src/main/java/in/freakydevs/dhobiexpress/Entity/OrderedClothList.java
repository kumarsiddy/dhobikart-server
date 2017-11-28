package in.freakydevs.dhobiexpress.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDERED_CLOTH_LIST")
public class OrderedClothList {

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "orderid")
	private String orderId;
	private String date;
	@Column(name = "isdelivered")
	private boolean isDelivered;
	private String name;
	private int number;
	private int price;
	@Column(name = "iscancelled")
	boolean isCancelled;

	public OrderedClothList() {
	}

	public OrderedClothList(String orderId, String date, boolean isDelivered, String name, int number, int price) {
		this.orderId = orderId;
		this.date = date;
		this.isDelivered = isDelivered;
		this.name = name;
		this.number = number;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean delivered) {
		isDelivered = delivered;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}
}
