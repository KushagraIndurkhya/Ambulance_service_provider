package ambulance.service.models;
//import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class ambulance {
    @MongoId
    private ObjectId id;
    private final String numberplate;
    private availability status;

    public ambulance(String numberplate, availability status) {
        this.numberplate = numberplate;
        this.status = status;
    }
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNumberplate() {
        return numberplate;
    }
    public availability getStatus() {
        return status;
    }

    public void setStatus(availability status) {
        this.status = status;
    }
}
