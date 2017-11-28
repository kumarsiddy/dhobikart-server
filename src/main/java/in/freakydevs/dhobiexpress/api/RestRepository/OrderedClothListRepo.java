package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.OrderedClothList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderedClothListRepo extends CrudRepository<OrderedClothList, Integer> {
	public Collection<OrderedClothList> findAllByOrderId(String orderId);
}
