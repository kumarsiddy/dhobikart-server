package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Beans.TokenHandler;
import in.freakydevs.dhobiexpress.Entity.OrderList;
import in.freakydevs.dhobiexpress.Entity.OrderedClothList;
import in.freakydevs.dhobiexpress.api.RestServices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("order")
public class Orders {

	@Autowired
	private OrderService orderService;

	@Autowired
	private TokenHandler tokenHandler;

	@RequestMapping(method = RequestMethod.GET, value = "/place")
	public String setOrder(@RequestHeader("token") String token, @RequestHeader("addressId") Integer addressId, @RequestHeader("isDHPoint") Boolean isDHPoint) {
		if (tokenHandler.isValidToken(token)) {
			return orderService.setOrder(tokenHandler.getmail(token), addressId, isDHPoint);
		}
		return "invalid user";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get")
	public Collection<OrderList> getCartlists(@RequestHeader("token") String token) {
		if (tokenHandler.isValidToken(token)) {
			return orderService.getOrderDetails(tokenHandler.getmail(token));
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cloth")
	public Collection<OrderedClothList> getAllClothesByOrderId(@RequestHeader("token") String token, @RequestHeader("orderId") String orderId) {
		if (tokenHandler.isValidToken(token))
			return orderService.getAllClothesByOrderId(orderId);
		else
			return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cancel")
	public String cancelOrder(@RequestHeader("token") String token, @RequestHeader("orderId") String orderId, @RequestHeader("addressId") Integer addressId) {
		if (tokenHandler.isValidToken(token))
			return orderService.cancelOrder(tokenHandler.getmail(token), orderId, addressId);
		else
			return "fail";
	}

}
