package in.freakydevs.dhobiexpress.Beans;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataBean {

	@Value("132635AveyHTgB584046a9")
	private String OTPAPIKEY;
	private Random random;
	public final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static OkHttpClient client = new OkHttpClient();

	public String getOTPAPIKEY() {
		return OTPAPIKEY;
	}

	public String getOtp() {
		random = new Random();
		return String.format("%04d", random.nextInt(10000));
	}

	public String getURLGETData(String url) throws Exception {

		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String getOrderId() {
		int num1, num2, num3; //3 numbers in area code
		int set2, set3; //sequence 2 and 3 of the phone number

		Random generator = new Random();


		num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin
		num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
		num3 = generator.nextInt(8);

		set2 = generator.nextInt(643) + 100;

		set3 = generator.nextInt(8999) + 1000;

		return "" + num1 + num2 + num3 + set2 + set3;
	}

}
