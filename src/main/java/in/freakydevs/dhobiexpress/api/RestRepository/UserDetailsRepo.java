package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDetailsRepo extends CrudRepository<UserDetails, String> {

	public UserDetails findByUserPhone(String userPhone);

}
