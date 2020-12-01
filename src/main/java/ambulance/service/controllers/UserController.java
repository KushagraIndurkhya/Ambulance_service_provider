package ambulance.service.controllers;
import ambulance.service.models.user;
import ambulance.service.models.userTypes;
import ambulance.service.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static ambulance.service.security.PasswordUtills.genPass;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/add-doctor")
    public String addDoctor(
            @CookieValue(value = "Token", defaultValue = "") String token,
            @RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("id") String id){
        if (new Jwt().isAdmin(token)) {
            String password = genPass(6);
            List<user> ll = userRepository.findByUserId(id);
            if (ll.size() == 1) {
                return "User with id: " + id + " already exists.";
            }
            user User = new user(fname, lname, id);
            User.setRole(userTypes.DOCTOR);
            User.setPassHash(password);
            userRepository.insert(User);
            return User.toString();
        }
        else {
            return "Unauthorized Access";
        }
    }
    @GetMapping("/add-employee")
    public String addEmployee(
            @CookieValue(value = "Token", defaultValue = "") String token,
            @RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("id") String id){
        if (new Jwt().isAdmin(token)) {
            String password = genPass(6);
            List<user> ll = userRepository.findByUserId(id);
            if (ll.size() == 1) {
                return "User with id: " + id + " already exists.";
            }
            user User = new user(fname, lname, id);
            User.setRole(userTypes.EMPLOYEE);
            User.setPassHash(password);
            userRepository.insert(User);
            return User.toString();
        }
        else {
            return "Unauthorized Access";
        }
    }

}
