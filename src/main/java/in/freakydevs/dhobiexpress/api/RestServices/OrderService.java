package in.freakydevs.dhobiexpress.api.RestServices;


import in.freakydevs.dhobiexpress.Beans.DataBean;
import in.freakydevs.dhobiexpress.Beans.OrderListResponse;
import in.freakydevs.dhobiexpress.Beans.SendMailSMSAfterOrder;
import in.freakydevs.dhobiexpress.Entity.*;
import in.freakydevs.dhobiexpress.Pojo.MyCart;
import in.freakydevs.dhobiexpress.api.RestRepository.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private OrderedClothListRepo orderedClothListRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private DataBean dataBean;

	@Autowired
	private SendMailSMSAfterOrder sendMailSMSAfterOrder;

	@Autowired
	private OrderListRepo orderListRepo;


	@Transactional
	public String setOrder(String mail, Integer addressId, Boolean isDHPoint) {
		try {
			String orderId = dataBean.getOrderId();
			UserDetails userDetails = userDetailsRepo.findOne(mail);
			List<Object[]> result = cartRepo.getCartListsByUserEmail(mail);

			Date date = new Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("IST"));
			String currentTime = sdf.format(date);

			if (result != null && !result.isEmpty()) {
				int totalPrice = 0;

				List<MyCart> myCarts = new ArrayList<>();

				for (Object[] object : result) {
					String clothName = String.valueOf(object[0]);
					int clothPrice = Integer.parseInt(String.valueOf(object[1]));
					int totalNumber = Integer.parseInt(String.valueOf(object[2]));

					totalPrice = totalPrice + (clothPrice * totalNumber);
					myCarts.add(new MyCart(clothName, clothPrice, totalNumber));

					OrderedClothList orderedClothList = new OrderedClothList(orderId, currentTime, false, clothName, totalNumber, clothPrice);
					orderedClothListRepo.save(orderedClothList);
				}

				OrderList orderList = new OrderList(orderId, totalPrice, currentTime, false, addressId);
				userDetails.getOrderList().add(orderList);
				userDetails.getCartList().clear();
				sendMailSMSAfterOrder.send(orderId, totalPrice, currentTime, addressRepo.findOne(addressId), myCarts);
				userDetailsRepo.save(userDetails);
				return "success";
			}

			return "fail";

		} catch (Exception e) {
			return "fail";
		}
	}

	public Collection<OrderList> getOrderDetails(String mail) {
		try {
			return userDetailsRepo.findOne(mail).getOrderList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Collection<OrderedClothList> getAllClothesByOrderId(String orderId) {
		try {
			return orderedClothListRepo.findAllByOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String cancelOrder(String mail, String orderId, Integer addressId) {
		try {
			UserDetails userDetails = userDetailsRepo.findOne(mail);
			OrderList orderList = orderListRepo.findByOrderId(orderId);
			if (isCanclable(orderList.getTime())) {
				userDetails.getOrderList().remove(orderList);
				userDetails.getOrderList().add(orderList);
				orderList.setCancelled(true);
				orderList.setDelivered(true);
				sendMailSMSAfterOrder.sendCancelMessage(orderId, orderList.getTotalPrice(), addressRepo.findOne(addressId));
				userDetailsRepo.save(userDetails);
				Collection<OrderedClothList> clothLists = orderedClothListRepo.findAllByOrderId(orderId);
				for (OrderedClothList orderedClothList : clothLists) {
					orderedClothList.setCancelled(true);
					orderedClothListRepo.save(orderedClothList);
				}
				return "success";
			} else {
				return "ErrorFromServer";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "ExceptionFromServer";
		}
	}

	private boolean isCanclable(String time) {
		DateTimeZone zone = DateTimeZone.forID("Asia/Kolkata");
		DateTime startDate = DateTime.parse(time,
				DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss"));
		DateTime dateTime = new DateTime(zone);
		int minutes = (Minutes.minutesBetween(startDate, dateTime).getMinutes()+330);
		System.out.println("Start Time " + time + " Minutes " + minutes + " Today time" + dateTime.toString() + minutes);
		return Math.abs(minutes) < 11;
	}
}
