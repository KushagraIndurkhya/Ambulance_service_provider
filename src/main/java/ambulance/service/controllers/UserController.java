package ambulance.service.controllers;
import ambulance.service.models.user;
import ambulance.service.models.userTypes;
import ambulance.service.security.CookieUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import static ambulance.service.security.PasswordUtills.genPass;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/add-doctor")
    public String addDoctor(HttpServletResponse response,
                            @CookieValue(value = "Token", defaultValue = "") String token,
                            @RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("id") String id){
        if (new CookieUtills().isAdmin(token)) {

            String password = genPass(6);
            List<user> ll = userRepository.findByUserId(id);
            for (user n:ll)
            {
                System.out.print(n.toString()+"\n");
            }
            if (ll.size() == 0) {
                user User = new user(fname, lname, id);
                User.setRole(userTypes.DOCTOR);
                User.setPassHash(password);
                userRepository.insert(User);
                response.setStatus(201);
                return User.toString();

            }
            else {
                response.setStatus(403);
                return "User with id: " + id + " already exists.";
            }
        }
        else {
            return "Unauthorized Access";
        }
    }
    @PostMapping("/add-employee")
    public String addEmployee(
            HttpServletResponse response,
            @CookieValue(value = "Token", defaultValue = "") String token,
            @RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("id") String id){
        if (new CookieUtills().isAdmin(token)) {

            String password = genPass(6);
            List<user> ll = userRepository.findByUserId(id);
            for (user n:ll)
            {
                System.out.print(n.toString()+"\n");
            }
            if (ll.size() == 0) {
                user User = new user(fname, lname, id);
                User.setRole(userTypes.EMPLOYEE);
                User.setPassHash(password);
                userRepository.insert(User);
                response.setStatus(201);
                return User.toString();

            }
            else {
                response.setStatus(403);
                return "User with id: " + id + " already exists.";
            }
        }
        else {
            return "Unauthorized Access";
        }
    }
    @PostMapping("/add-attendee")
    public String addAttendee(HttpServletResponse response,
                              @CookieValue(value = "Token", defaultValue = "") String token,
                              @RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("id") String id){
        if (new CookieUtills().isAdmin(token)) {

            String password = genPass(6);
            List<user> ll = userRepository.findByUserId(id);
            for (user n:ll)
            {
                System.out.print(n.toString()+"\n");
            }
            if (ll.size() == 0) {
                user User = new user(fname, lname, id);
                User.setRole(userTypes.ATTENDEE);
                User.setPassHash(password);
                userRepository.insert(User);
                response.setStatus(201);
                return User.toString();

            }
            else {
                response.setStatus(403);
                return "User with id: " + id + " already exists.";
            }
        }
        else {
            return "Unauthorized Access";
        }
    }

}
