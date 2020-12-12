package ambulance.service.models;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;

@Document(collection = "user")
@ApiModel
public class user {
    @MongoId
    @ApiModelProperty(notes = "Auto Generated Mongo Id")
    private ObjectId id;
    @NotNull
    @ApiModelProperty(notes = "First name of the user")
    private final String firstName;
    @NotNull
    @ApiModelProperty(notes = "Last name of the user")
    private final String lastName;
    private String passHash;
    @NotNull
    @ApiModelProperty(notes = "Hospital Generated userId")
    private final String userId;
    @NotNull
    @ApiModelProperty(notes = "Role of the user")
    private userTypes role;

    public user(String firstName, String lastName, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "user{" +
                "id='" + id + '\'' +
                ", user_id='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", password"+passHash+
                '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getUserId() {
        return userId;
    }

    public userTypes getRole() {
        return role;
    }

    public void setRole(userTypes role) {
        this.role = role;
    }
}
