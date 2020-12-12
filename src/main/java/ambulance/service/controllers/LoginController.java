package ambulance.service.controllers;
import ambulance.service.models.user;
import ambulance.service.security.CookieUtills;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import static ambulance.service.security.PasswordUtills.checkPass;
import static ambulance.service.security.PasswordUtills.genPass;

@RestController
@RequestMapping("/")
@Api(value = "Login related use cases")
public class LoginController {
    @Autowired
    UserRepository userRepository;
    public boolean isLoggedIn(String token)
    {
        return !token.isEmpty();
    }
    @GetMapping("/login")
    @ApiOperation(value = "Login")
    public String login(HttpServletResponse response, @RequestParam("id") String id, @RequestParam("pass") String pass)
    {
        try {
            user User = userRepository.findByUserId(id).get(0);
                if (checkPass(User.getPassHash(), pass)) {
                    String cook = new CookieUtills().createToken(
                            User.getUserId(),
                            User.getFirstName() + " " + User.getLastName(),
                            User.getRole());
                    Cookie cookie = new Cookie("Token", cook);
                    response.addCookie(cookie);
                    response.setStatus(200);
                    return "Welcome" + " " + User.getFirstName() + " " + User.getLastName();
                }
                else {
                    response.setStatus(403);
                    return "Incorrect Password";
                }
        }
        catch (Exception e)
            {
                response.setStatus(404);
            return "No User exists for given Id: "+id;
        }
    }
    @DeleteMapping("/logout")
    @ApiOperation(value = "Logout")
    public String logout(@CookieValue(value = "Token", defaultValue = "") String token,
                         HttpServletResponse response)
    {
        if (isLoggedIn(token)) {
            Cookie cookie = new Cookie("Token", "");
            response.addCookie(cookie);
        }
        else {
            response.setStatus(404);
            return "Not Logged in";
        }
        response.setStatus(200);
        return "logged Out";
    }
    @PutMapping("/resetPassword")
    @ApiOperation(value = "[Admin]Resetting password of an user")
    public String resetPassword(@CookieValue(value = "Token", defaultValue = "") String token,
                         HttpServletResponse response,@RequestParam("id") String id) {
        if (new CookieUtills().isAdmin(token))
        {
            try {
                user User = userRepository.findByUserId(id).get(0);
                String password = genPass(6);
                User.setPassHash(password);
                userRepository.save(User);
                response.setStatus(201);
                return "Password for user:"+User.toString()+"changed to:"+password;
            }
            catch (Exception e)
            {
                response.setStatus(404);
                return "No User exists for given Id: " + id;
            }
        }
        else {
            response.setStatus(401);
            return "Must be Logged in as an admin";
        }
    }
    @PutMapping("/changePassword")
    @ApiOperation(value = "changing password by user")
    public String changePassword(@CookieValue(value = "Token", defaultValue = "") String token,
                                 HttpServletResponse response,
                                 @RequestParam("id") String id,
                                 @RequestParam("Old_pass") String Old_pass,
                                 @RequestParam("new_pass") String new_pass)
    {
        try{
            user User=userRepository.findByUserId(id).get(0);
            if (checkPass(User.getPassHash(), Old_pass)) {
                User.setPassHash(new_pass);
                userRepository.save(User);
                response.setStatus(201);
                return "Password Successfully Changed for" + " " + User.getFirstName() + " " + User.getLastName();
            }
            else
                {
                    response.setStatus(403);
                return "Old Password Incorrect";
            }
        }
        catch (Exception e)
        {
            response.setStatus(404);
            return "No User exists for given Id: "+id;
        }
    }
    }

