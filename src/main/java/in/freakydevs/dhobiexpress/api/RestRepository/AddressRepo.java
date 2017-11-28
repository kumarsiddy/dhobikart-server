package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.AddressList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends CrudRepository<AddressList, Integer> {

}
