package in.freakydevs.dhobiexpress.Beans;

import in.freakydevs.dhobiexpress.Entity.AddressList;
import in.freakydevs.dhobiexpress.Pojo.MyCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Component
public class SendMailSMSAfterOrder {

	@Autowired
	private SendSMS sendSMS;

	@Autowired
	private SendMail sendMail;

	public void send(String orderId, int totalPrice, String currentTime, AddressList address, List<MyCart> myCarts) throws Exception {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ORDER ID ").append(orderId).append(" \nCloth Details")
				.append(getClothesString(myCarts))
				.append("\nAddress is\n" + getAddressString(address))
				.append("\nTotal Price is:" + totalPrice);
		sendSMS.sendNow(address.getPhone(), getStringSMS(orderId, address, totalPrice));
		sendMail.sendNow("Order Details", "kumarsiddy@gmail.com", stringBuilder.toString());
	}

	private String getClothesString(List<MyCart> myCarts) {

		StringBuilder stringBuilder = new StringBuilder();

		for (MyCart cart : myCarts) {
			stringBuilder.append("\n" + cart.getClothName() + "  Price " + cart.getClothPrice() + "  TotalNo. " + cart.getTotalNumber());
		}

		return stringBuilder.toString();
	}

	private String getAddressString(AddressList address) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Name: " + address.getName()
				+ "\nPhone:" + address.getPhone()
				+ "\nAlternate Phone:" + address.getAlterphone()
				+ "\nArea:" + address.getArea()
				+ "\nLandmark:" + address.getLandmark()
				+ "\nPin:" + address.getPin()
				+ "\nCity:" + address.getCity());

		return stringBuilder.toString();

	}

	private String getStringSMS(String orderId, AddressList addressList, int total) {

		String message;

		int start = 600;
		int stop = 1020;

		Calendar calender = Calendar.getInstance();

		calender.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		calender.setTimeInMillis(calender.getTimeInMillis());
		int hour = calender.get(Calendar.HOUR_OF_DAY);
		int minute = calender.get(Calendar.MINUTE);

		int currentMinutes = (hour * 60) + minute;

		System.out.println("Time:" + currentMinutes);


		if (currentMinutes > start && currentMinutes < stop) {

			message = "Thank you "
					+ addressList.getName() +
					" for placing your Order.Your order Id is "
					+ orderId +
					" and payable amount is Rs."
					+ total +
					".Your order will be picked up within 2 hours.";

		} else if (currentMinutes > 0 && currentMinutes < start) {


			message = "Thank you "
					+ addressList.getName() +
					" for placing your Order.Your order Id is "
					+ orderId +
					" and payable amount is Rs."
					+ total +
					".Your order will be picked up today after 10 AM.";

		} else {

			message = "Thank you "
					+ addressList.getName() +
					" for placing your Order.Your order Id is "
					+ orderId +
					" and payable amount is Rs."
					+ total +
					".Your order will be picked up tomorrow after 10 AM.";

		}
		return message;
	}

	public void sendCancelMessage(String orderId, int totalPrice, AddressList address) throws Exception {

		String message = "Dear "
				+ address.getName() +
				". Your Order having Order Id "
				+ orderId +
				" and payable amount of Rs."
				+ totalPrice +
				" has been Cancelled.";

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ORDER CANCELLED ORDER ID ").append(orderId)
				.append("\nAddress is\n" + getAddressString(address))
				.append("\nTotal Price is:" + totalPrice);

		sendSMS.sendNow(address.getPhone(), message);
		sendMail.sendNow("ORDER CANCELLED ", "kumarsiddy@gmail.com", stringBuilder.toString());
	}
}
