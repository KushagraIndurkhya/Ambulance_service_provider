package ambulance.service.controllers;
import ambulance.service.models.user;
import ambulance.service.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
        if (User.getPassHash().equals(pass))
        {
        String cook = new Jwt().createToken(
                User.getUserId(),
                User.getFirstName()+" "+User.getLastName(),
                    User.getRole());
            Cookie cookie = new Cookie("Token", cook);
            response.addCookie(cookie);
            return "Welcome"+" "+User.getFirstName()+" "+User.getLastName();
        }
        else  {
            return "Unauthorized Access";
        }
    }
    @GetMapping("/logout")
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
    }

