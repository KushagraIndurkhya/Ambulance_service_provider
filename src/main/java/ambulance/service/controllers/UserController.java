package ambulance.service.controllers;
import ambulance.service.models.user;
import ambulance.service.models.userTypes;
import ambulance.service.security.CookieUtills;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import static ambulance.service.security.PasswordUtills.genPass;

@RestController
@RequestMapping("/")
@Api(value = "User Addition related use cases")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/add/{role}")
    @ApiOperation(value =  "[ADMIN] Add an user to database with given role")
    public user addUser(HttpServletResponse response,
                          @CookieValue(value = "Token", defaultValue = "") String token,
                          @RequestParam( "FirstName") String fname,
                          @RequestParam( "LastName") String lname,
                          @RequestParam("id") String id,
                        @PathVariable("role") String role) {
        CookieUtills cookieUtills = new CookieUtills();
        if (cookieUtills.isAdmin(token)) {
            try {
                userTypes role_assign=userTypes.GUEST;
                int flag=0;
                userTypes[] available_types = userTypes.values();
                for (userTypes role_x : available_types) {
                    if (role.equalsIgnoreCase(role_x.name())) {
                        role_assign = role_x;
                        flag = 1;
                    }
                }
                if (flag==0)
                {
                    response.setStatus(403);
                    return null;
                }
                String password = genPass(6);
                List<user> ll = userRepository.findByUserId(id);
                if (ll.size() == 0) {
                    user User = new user(fname, lname, id);
                    User.setRole(role_assign);
                    User.setPassHash(password);
                    userRepository.insert(User);
                    response.setStatus(201);
                    return User;
                }
                else {
                    response.setStatus(403);
                    return null;
                }
            } catch (Exception e) {
                response.setStatus(403);
                System.out.print(e.getStackTrace());
                return null;
            }
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @PostMapping("/add-doctor")
    @ApiOperation(value =  "[ADMIN] Add a doctor to database")
    public user addDoctor(HttpServletResponse response,
                          @CookieValue(value = "Token", defaultValue = "") String token,
                          @RequestParam( "FirstName") String fname,
                          @RequestParam( "LastName") String lname,
                          @RequestParam("id") String id) {
        CookieUtills cookieUtills = new CookieUtills();
        if (cookieUtills.isAdmin(token)) {
            try {
                String password = genPass(6);
                List<user> ll = userRepository.findByUserId(id);
                if (ll.size() == 0) {
                    user User = new user(fname, lname, id);
                    User.setRole(userTypes.DOCTOR);
                    User.setPassHash(password);
                    userRepository.insert(User);
                    response.setStatus(201);
                    return User;
                }
                else {
                    response.setStatus(403);
                    return null;
                }
            } catch (Exception e) {
                response.setStatus(403);
                System.out.print(e.getStackTrace());
                return null;
            }
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @PostMapping("/add-employee")
    @ApiOperation(value =  "[ADMIN] Add an employee to database")
    public user addEmployee(
            HttpServletResponse response,
            @CookieValue(value = "Token", defaultValue = "") String token,
            @RequestParam( "FirstName") String fname,
            @RequestParam( "LastName") String lname,
            @RequestParam("id") String id) {
        CookieUtills cookieUtills = new CookieUtills();
        if (cookieUtills.isAdmin(token)) {
            try {
                String password = genPass(6);
                List<user> ll = userRepository.findByUserId(id);
                if (ll.size() == 0) {
                    user User = new user(fname, lname, id);
                    User.setRole(userTypes.EMPLOYEE);
                    User.setPassHash(password);
                    userRepository.insert(User);
                    response.setStatus(201);
                    return User;
                }
                else {
                    response.setStatus(403);
                    return null;
                }
            } catch (Exception e) {
                response.setStatus(403);
                System.out.print(e.getStackTrace());
                return null;
            }
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @PostMapping("/add-attendee")
    @ApiOperation(value =  "[ADMIN] Add an employee to database")
    public user addAttendee(HttpServletResponse response,
                            @CookieValue(value = "Token", defaultValue = "") String token,
                            @RequestParam( "FirstName") String fname,
                            @RequestParam( "LastName") String lname,
                            @RequestParam("id") String id) {
        CookieUtills cookieUtills = new CookieUtills();
        if (cookieUtills.isAdmin(token)) {
            try {
                String password = genPass(6);
                List<user> ll = userRepository.findByUserId(id);
                if (ll.size() == 0) {
                    user User = new user(fname, lname, id);
                    User.setRole(userTypes.ATTENDEE);
                    User.setPassHash(password);
                    userRepository.insert(User);
                    response.setStatus(201);
                    return User;
                }
                else {
                    response.setStatus(403);
                    return null;
                }

            }
             catch (Exception e) {
                response.setStatus(403);
                System.out.print(e.getStackTrace());
                return null;
            }
        } else {
            response.setStatus(401);
            return null;
        }
    }
}
