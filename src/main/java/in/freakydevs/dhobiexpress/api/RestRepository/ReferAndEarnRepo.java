package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.ReferAndEarn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferAndEarnRepo extends CrudRepository<ReferAndEarn, String> {

	ReferAndEarn findByReferCode(String referCode);

}
