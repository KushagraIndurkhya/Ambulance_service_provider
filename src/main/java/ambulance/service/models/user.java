package ambulance.service.models;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;
public class user {
    @MongoId
    private ObjectId id;
    private final String firstName;
    private final String lastName;
    private String passHash;
    private final String userId;
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
