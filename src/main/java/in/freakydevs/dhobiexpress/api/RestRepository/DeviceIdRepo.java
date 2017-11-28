package in.freakydevs.dhobiexpress.api.RestRepository;

import in.freakydevs.dhobiexpress.Entity.DeviceId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceIdRepo extends CrudRepository<DeviceId,Integer> {
}
