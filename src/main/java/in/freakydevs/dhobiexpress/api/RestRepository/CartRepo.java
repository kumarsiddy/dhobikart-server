package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.CartList;
import in.freakydevs.dhobiexpress.Pojo.MyCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends CrudRepository<CartList, Integer> {

	@Query(value = "select c.totalNumber from UserDetails u join u.cartList c where u.userEmail=:email and c.clothName=:clothName")
	public String getClothNumber(@Param("email") String email, @Param("clothName") String clothName);

	@Query(value = "select sum(c.totalNumber) from UserDetails u join u.cartList c where u.userEmail=:email")
	public String getTotalNoOfClothes(@Param("email") String email);


	public CartList findByClothNameAndUserDetailsUserEmail(String cloth_name, String userEmail);

	@Query(value = "select c.clothName,c.clothPrice,c.totalNumber,c.id from UserDetails u join u.cartList c where u.userEmail=:email")
	public List<Object[]> getCartListsByUserEmail(@Param("email") String email);

}
