package in.freakydevs.dhobiexpress.api.RestController;

import in.freakydevs.dhobiexpress.Beans.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public String test(@RequestHeader("name") String name) {
		return "Hello " + name;
	}

}
