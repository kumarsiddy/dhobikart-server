package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Beans.TokenHandler;
import in.freakydevs.dhobiexpress.Entity.AddressList;
import in.freakydevs.dhobiexpress.api.RestServices.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("address")
public class Address {

	@Autowired
	private AddressService addressService;

	@Autowired
	private TokenHandler tokenHandler;

	@RequestMapping(method = RequestMethod.GET, value = "/get")
	public Collection<AddressList> getAddress(@RequestHeader("token") String token) {

		if (tokenHandler.isValidToken(token)) {
			return addressService.getAllAddresses(tokenHandler.getmail(token));
		}

		return null;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public String setAddress(@RequestHeader("token") String token, @RequestBody AddressList address) {

		if (tokenHandler.isValidToken(token)) {
			return addressService.addAddress(tokenHandler.getmail(token), address);
		}

		return "user Invalid";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public String updateAddress(@RequestHeader("token") String token, @RequestBody AddressList address) {

		if (tokenHandler.isValidToken(token)) {
			return addressService.updateAddress(tokenHandler.getmail(token), address);
		}

		return "user Invalid";
	}


	@RequestMapping(method = RequestMethod.GET, value = "/delete")
	public String deleteAddress(@RequestHeader("token") String token, @RequestHeader("addressId") Integer addressId) {
		if (tokenHandler.isValidToken(token)) {
			return addressService.deleteAddress(tokenHandler.getmail(token), addressId);
		}
		return "user Invalid";
	}
}
