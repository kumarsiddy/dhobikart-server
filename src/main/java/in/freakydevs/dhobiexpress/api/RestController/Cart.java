package in.freakydevs.dhobiexpress.api.RestController;

import com.google.gson.Gson;
import in.freakydevs.dhobiexpress.Beans.TokenHandler;
import in.freakydevs.dhobiexpress.Entity.CartList;
import in.freakydevs.dhobiexpress.Entity.UserDetails;
import in.freakydevs.dhobiexpress.Pojo.MyCart;
import in.freakydevs.dhobiexpress.api.RestRepository.CartRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.UserDetailsRepo;
import in.freakydevs.dhobiexpress.api.RestServices.CartService;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("cart")
public class Cart {

	@Autowired
	private CartService cartService;

	@Autowired
	private TokenHandler tokenHandler;

	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public String addToCart(@RequestHeader("token") String token, @RequestBody CartList cartList) {
		if (tokenHandler.isValidToken(token)) {
			return cartService.addtoCart(cartList, tokenHandler.getmail(token));
		}
		return "user Invalid";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/total")
	public String getTotalNoOfClothes(@RequestHeader("token") String token) {
		if (tokenHandler.isValidToken(token)) {
			return cartService.getTotalNoOfClothes(tokenHandler.getmail(token));
		}

		return "user Invalid";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get")
	public List<MyCart> getAllCartLists(@RequestHeader("token") String token) {
		if (tokenHandler.isValidToken(token)) {

			return cartService.getAllClothLists(tokenHandler.getmail(token));

		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addone")
	public String addOneToCart(@RequestHeader("token") String token, @RequestHeader("clothname") String clothname) {
		if (tokenHandler.isValidToken(token)) {
			return cartService.addOnetoCart(tokenHandler.getmail(token), clothname);
		}

		return "user Invalid";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/removeone")
	public String removeOneFromCart(@RequestHeader("token") String token, @RequestHeader("clothname") String clothname) {
		if (tokenHandler.isValidToken(token)) {
			return cartService.removeOneFromCart(tokenHandler.getmail(token), clothname);
		}

		return "user Invalid";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete")
	public String deleteFromCart(@RequestHeader("token") String token, @RequestHeader("clothname") String clothname) {
		if (tokenHandler.isValidToken(token)) {
			return cartService.deleteFromCart(tokenHandler.getmail(token), clothname);
		}

		return "user Invalid";
	}


}