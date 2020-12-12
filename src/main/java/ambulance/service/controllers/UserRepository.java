package ambulance.service.controllers;

import ambulance.service.models.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<user,String> {
    @Override
    List<user> findAll();
    List<user> findByUserId(String id);
    user findByFirstName(String fname);
}
