package com.soft6creators.futurespace.app.user;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.soft6creators.futurespace.app.account.Account;
import com.soft6creators.futurespace.app.account.AccountService;
import com.soft6creators.futurespace.app.mailsender.MailSenderService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	AccountService accountService;
	@Autowired
	MailSenderService mailSenderService;

	public User addUser(User user) {
		if (checkUser(user.getEmail())) {
			return new User();
		}
		String randomCode = RandomString.make(6);
		user.setVerificationCode(randomCode);
		user.setReferralId(user.getFullName().trim() + "-" + RandomString.make(6));
		if (user.getReferral() != null) {
			User userReferral = userRepository.findByReferralId(user.getReferral().getReferralId());
			if (userReferral != null) {
				user.setReferral(userReferral);
			} else {
				User wrongReferral = new User();
				wrongReferral.setReferral(user.getReferral());
				return wrongReferral;
			}
		}

		try {
			sendVerificationEmail(user);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Account account = new Account();
		if (user.getReferral() != null) {
			account.setAccountBalance(20);
		} else {
			account.setAccountBalance(20);
		}
		accountService.addAccount(account);
		user.setAccount(account);
		return userRepository.save(user);
	}

	public Optional<User> getUser(String email) {
		return userRepository.findById(email);
	}

	private boolean checkUser(String email) {
		return userRepository.existsById(email);
	}

	private void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String subject = "GIACOURIER";
		String content = "<div>\n"
				+ "      <style>\n"
				+ "        #container {\n"
				+ "            padding: 12px; font-family: Arial, Helvetica, sans-serif;\n"
				+ "        }\n"
				+ "        @media only screen and (min-width: 800px) {\n"
				+ "          #container {\n"
				+ "            padding: 10% 35%;\n"
				+ "          }\n"
				+ "        }\n"
				+ "      </style>\n"
				+ "      <div id=\"container\">\n"
				+ "        <div\n"
				+ "         \n"
				+ "          style=\"\n"
				+ "            box-shadow: 1px 1px 5px rgb(207, 207, 207);\n"
				+ "            font-size: 14px;\n"
				+ "            border-radius: 2px;\n"
				+ "          \"\n"
				+ "        >\n"
				+ "          <div style=\"background-color: rgb(207, 207, 207); padding: 16px\">\n"
				+ "            <img src=\"cid:image\" alt=\"\" style=\"width: 50%\" />\n"
				+ "          </div>\n"
				+ "          <div style=\"padding: 16px\">\n"
				+ "            <p\n"
				+ "              style=\"\n"
				+ "                font-size: 20px;\n"
				+ "                font-weight: 500;\n"
				+ "                margin: 12px 0px 32px;\n"
				+ "                text-align: center;\n"
				+ "                color: rgb(46, 46, 46);\n"
				+ "              \"\n"
				+ "            >\n"
				+ "              GIA-Courier freight shipping confirmation mail\n"
				+ "            </p>\n"
				+ "            <p\n"
				+ "              style=\"\n"
				+ "                margin: 16px 0px;\n"
				+ "                font-weight: bold;\n"
				+ "                color: rgb(0, 25, 150);\n"
				+ "              \"\n"
				+ "            >\n"
				+ "              Dear Mike,\n"
				+ "            </p>\n"
				+ "            <p style=\"margin: 12px 0px\">\n"
				+ "              Lorem, ipsum dolor sit amet consectetur adipisicing elit. Eum\n"
				+ "              maxime cumque nisi sed nulla quos voluptatem, perferendis minima\n"
				+ "              in, magnam doloribus laboriosam? Ad temporibus, incidunt beatae\n"
				+ "              minus praesentium numquam possimus?\n"
				+ "            </p>\n"
				+ "            <p\n"
				+ "              style=\"\n"
				+ "                margin: 12px 0px;\n"
				+ "                color: rgb(0, 25, 150);\n"
				+ "                font-weight: bold;\n"
				+ "              \"\n"
				+ "            >\n"
				+ "              GIA2726LX\n"
				+ "            </p>\n"
				+ "            <p style=\"margin: 12px 0px\">\n"
				+ "              Lorem ipsum, dolor sit amet consectetur adipisicing elit. Aperiam\n"
				+ "              quae aliquid earum, totam molestias ea hic similique doloremque,\n"
				+ "              quod atque accusantium provident corporis cupiditate\n"
				+ "              exercitationem tempore repudiandae incidunt harum ex?\n"
				+ "            </p>\n"
				+ "            <p style=\"margin: 12px 0px\">\n"
				+ "              Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat,\n"
				+ "              aliquam sunt molestiae omnis maxime itaque officia dolores quo\n"
				+ "              ipsam at fugit provident quod. Eligendi aperiam, molestias cum\n"
				+ "              mollitia nulla commodi?\n"
				+ "            </p>\n"
				+ "            <p\n"
				+ "              style=\"\n"
				+ "                margin: 24px 0px;\n"
				+ "                color: red;\n"
				+ "                font-weight: bold;\n"
				+ "                text-align: center;\n"
				+ "              \"\n"
				+ "            >\n"
				+ "              Lorem ipsum dolor sit amet consectetur adipisicing elit. Explicabo\n"
				+ "              assumenda vel modi ducimus voluptatibus officia autem perspiciatis\n"
				+ "              aspernatur consequuntur molestias, ipsa delectus impedit deleniti\n"
				+ "              ex. Tempora a maiores voluptatum harum.\n"
				+ "            </p>\n"
				+ "          </div>\n"
				+ "        </div>\n"
				+ "      </div>\n"
				+ "    </div>";
		

		mailSenderService.sendEmail(toAddress, subject, content);
	}

	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);
		if (user == null || user.isActive()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setActive(true);
			userRepository.save(user);
			return true;
		}
	}

	public User signIn(String email, String password) {
		Optional<User> user = userRepository.findByEmailAndPassword(email, password);
		return user.get();
	}

}
