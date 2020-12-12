package ambulance.service.models;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;

@Document(collection = "ambulance")
@ApiModel
public class ambulance {

    @MongoId
    @ApiModelProperty(notes = "Auto Generated Mongo Id")
    private ObjectId id;
    @ApiModelProperty(notes = "Number Plate of the ambulance")
    @NotNull
    private final String numberplate;
    @ApiModelProperty(notes = "Availability of the ambulance")
    private availability status;
// constructor
    public ambulance(String numberplate, availability status) {
        this.numberplate = numberplate;
        this.status = status;
    }
//   getters
    public ObjectId getId() {
        return id;
    }
    public String getNumberplate() {
        return numberplate;
    }
    public availability getStatus() {
        return status;
    }
// setters
    public void setStatus(availability status) {
        this.status = status;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
// tostring
    @Override
    public String toString() {
        return "ambulance{" +
                "id=" + id +
                ", numberplate='" + numberplate + '\'' +
                ", status=" + status +
                '}';
    }


}
