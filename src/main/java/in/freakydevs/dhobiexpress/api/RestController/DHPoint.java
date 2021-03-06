package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Beans.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dhpoint")
public class DHPoint {

	@Autowired
	private TokenHandler tokenHandler;

	@RequestMapping(method = RequestMethod.GET, value = "/get")
	protected String getDHPoint(@RequestHeader("token") String token) {
		if (tokenHandler.isValidToken(token)) {
			return "0";
		}
		return "0";
	}
}
