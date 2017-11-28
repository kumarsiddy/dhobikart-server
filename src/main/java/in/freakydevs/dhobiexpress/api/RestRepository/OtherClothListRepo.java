package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.OtherCLothList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherClothListRepo extends CrudRepository<OtherCLothList, Integer> {
}
