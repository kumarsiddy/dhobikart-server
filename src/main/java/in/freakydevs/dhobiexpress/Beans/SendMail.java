package in.freakydevs.dhobiexpress.Beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendMail {

	@Autowired
	private JavaMailSender sender;

	@Async
	public void sendNow(String subject,String to,String messageString) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(to);
		helper.setText(messageString);
		helper.setSubject(subject);
		sender.send(message);
	}
}
