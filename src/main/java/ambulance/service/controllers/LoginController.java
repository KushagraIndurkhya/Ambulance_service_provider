package ambulance.service.controllers;
import ambulance.service.models.user;
import ambulance.service.security.CookieUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static ambulance.service.security.PasswordUtills.checkPass;
import static ambulance.service.security.PasswordUtills.genPass;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserRepository userRepository;
    public boolean isLoggedIn(String token)
    {
        return !token.isEmpty();
    }
    @GetMapping("/login")
    public String login(HttpServletResponse response, @RequestParam("id") String id, @RequestParam("pass") String pass)
    {
        user User=userRepository.findByUserId(id).get(0);
        if (User != null) {
            if (checkPass(User.getPassHash(), pass)) {
                String cook = new CookieUtills().createToken(
                        User.getUserId(),
                        User.getFirstName() + " " + User.getLastName(),
                        User.getRole());
                Cookie cookie = new Cookie("Token", cook);
                response.addCookie(cookie);
                return "Welcome" + " " + User.getFirstName() + " " + User.getLastName();
            } else {
                return "Unauthorized Access";
            }
        }
        else {
            return "No User exists for given Id: "+id;
        }
    }
    @DeleteMapping("/logout")
    public String logout(@CookieValue(value = "Token", defaultValue = "") String token,
                         HttpServletResponse response)
    {
        if (isLoggedIn(token)) {
            Cookie cookie = new Cookie("Token", "");
            response.addCookie(cookie);
        }
        else {
            return "Not Logged in";
        }
        return "logged Out";
    }
    @PutMapping("/resetPassword")
    public String resetPassword(@CookieValue(value = "Token", defaultValue = "") String token,
                         HttpServletResponse response,@RequestParam("id") String id) {
        if (new CookieUtills().isAdmin(token)) {
            user User = userRepository.findByUserId(id).get(0);
            if (User != null) {
                String password = genPass(6);
                User.setPassHash(password);
                userRepository.save(User);
                response.setStatus(201);
                return "Password for user:"+User.toString()+"changed to:"+password;
            } else {
                response.setStatus(401);
                return "Unauthorized Access";
            }
        } else {
            response.setStatus(404);
            return "No User exists for given Id: " + id;
        }
    }
    @PutMapping("/changePassword")
    public String changePassword(@CookieValue(value = "Token", defaultValue = "") String token,
                                 HttpServletResponse response,@RequestParam("id") String id, @RequestParam("Old_pass") String Old_pass,
                                 @RequestParam("new_pass") String new_pass)
    {
        user User=userRepository.findByUserId(id).get(0);
        if (User != null) {
            if (checkPass(User.getPassHash(), Old_pass)) {
                User.setPassHash(new_pass);
                userRepository.save(User);
                response.setStatus(201);
                return "Password Successfully Changed for" + " " + User.getFirstName() + " " + User.getLastName();
            } else {
                response.setStatus(401);
                return "Unauthorized Access";
            }
        }
        else {
            response.setStatus(404);
            return "No User exists for given Id: "+id;
        }

    }
    }

