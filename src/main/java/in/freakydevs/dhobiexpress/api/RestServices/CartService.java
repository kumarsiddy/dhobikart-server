package in.freakydevs.dhobiexpress.api.RestServices;

import in.freakydevs.dhobiexpress.Entity.CartList;
import in.freakydevs.dhobiexpress.Entity.UserDetails;
import in.freakydevs.dhobiexpress.Pojo.MyCart;
import in.freakydevs.dhobiexpress.api.RestRepository.CartRepo;
import in.freakydevs.dhobiexpress.api.RestRepository.UserDetailsRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CartService {


	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	@Transactional
	public String addtoCart(CartList cartList, String mail) {

		System.out.println(mail);
		String cloth_name = cartList.getClothName();
		int cloth_price = cartList.getClothPrice();
		int no_of_items = cartList.getTotalNumber();

		System.out.println(cloth_name + "  " + cloth_price + "  " + no_of_items);

		try {
			int count = 0;
			String countString = cartRepo.getClothNumber(mail, cloth_name);
			if (countString != null) {
				count = Integer.parseInt(cartRepo.getClothNumber(mail, cloth_name));
			}

			System.out.println("" + count);
			boolean exists = count > 0;

			if (exists) {

				int updatedNo = no_of_items + count;
				CartList cartList2 = cartRepo.findByClothNameAndUserDetailsUserEmail(cloth_name, mail);
				cartList2.setTotalNumber(updatedNo);
				cartRepo.save(cartList2);

			} else {

				UserDetails userDetails = userDetailsRepo.findOne(mail);
				cartList.setUserDetails(userDetails);
				userDetails.getCartList().add(cartList);
				userDetailsRepo.save(userDetails);

			}

			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}


	public String getTotalNoOfClothes(String email) {
		String s = cartRepo.getTotalNoOfClothes(email);
		if (s != null)
			return s;
		else
			return "0";
	}

	public List<MyCart> getAllClothLists(String mail) {
		List<Object[]> result = cartRepo.getCartListsByUserEmail(mail);

		List<MyCart> carts = new ArrayList<>();

		if (result != null && !result.isEmpty()) {

			for (Object[] object : result) {
				MyCart myCart = new MyCart(String.valueOf(object[0]), Integer.parseInt(String.valueOf(object[1])), Integer.parseInt(String.valueOf(object[2])));
				carts.add(myCart);
			}
			return carts;

		}
		return null;
	}

	public String addOnetoCart(String mail, String clothname) {
		try {
			CartList cartList = cartRepo.findByClothNameAndUserDetailsUserEmail(clothname, mail);
			cartList.setTotalNumber(cartList.getTotalNumber() + 1);
			cartRepo.save(cartList);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

	}

	public String removeOneFromCart(String mail, String clothname) {

		try {
			CartList cartList = cartRepo.findByClothNameAndUserDetailsUserEmail(clothname, mail);
			cartList.setTotalNumber(cartList.getTotalNumber() - 1);
			cartRepo.save(cartList);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	public String deleteFromCart(String mail, String clothname) {

		try {
			CartList cartList = cartRepo.findByClothNameAndUserDetailsUserEmail(clothname, mail);
			UserDetails userDetails = userDetailsRepo.findOne(mail);
			userDetails.getCartList().remove(cartList);
			userDetailsRepo.save(userDetails);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

}
