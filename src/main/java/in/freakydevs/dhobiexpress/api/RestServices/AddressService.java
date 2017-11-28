package in.freakydevs.dhobiexpress.api.RestServices;

import in.freakydevs.dhobiexpress.Beans.TokenHandler;
import in.freakydevs.dhobiexpress.Entity.AddressList;
import in.freakydevs.dhobiexpress.Entity.UserDetails;
import in.freakydevs.dhobiexpress.Pojo.MyAddress;
import in.freakydevs.dhobiexpress.api.RestRepository.AddressRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.UserDetailsRepo;
import org.apache.catalina.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AddressService {

	@Autowired
	private TokenHandler tokenHandler;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private UserDetailsRepo userDetailsRepo;


	public Collection<AddressList> getAllAddresses(String mail) {

		try {
			UserDetails userDetails = userDetailsRepo.findOne(mail);
			Collection<AddressList> addresses = userDetails.getAddressList();
			return addresses;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String addAddress(String mail, AddressList address) {

		try {
			UserDetails userDetails = userDetailsRepo.findOne(mail);
			userDetails.getAddressList().add(address);
			userDetailsRepo.save(userDetails);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	public String updateAddress(String mail, AddressList address) {
		try {
			addressRepo.save(address);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	public String deleteAddress(String mail, Integer addressId) {
		try {
			UserDetails userDetails = userDetailsRepo.findOne(mail);
			AddressList addressList = addressRepo.findOne(addressId);
			userDetails.getAddressList().remove(addressList);
			userDetailsRepo.save(userDetails);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
}
