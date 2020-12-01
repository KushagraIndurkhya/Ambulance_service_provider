package ambulance.service.controllers;
import ambulance.service.models.ambulance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ambulance.service.models.availability;

import java.util.List;

@Repository
public interface AmbulanceRepository extends MongoRepository<ambulance,String> {
    @Override
    List<ambulance> findAll();
    List<ambulance> findByNumberplate(String np);
    List<ambulance> findByStatus(availability available);
}
