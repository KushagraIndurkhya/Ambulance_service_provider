package ambulance.service.controllers;
import ambulance.service.models.ambulance;
import ambulance.service.models.availability;
import ambulance.service.security.CookieUtills;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.util.List;
import ambulance.service.security.CookieUtills.*;

@RestController
@RequestMapping("/ambulance")
public class AmbulanceController
{
    @Autowired
    AmbulanceRepository ambulanceRepository;
    @PostMapping(value="/add")
    public String addNewAmbulance(
            HttpServletResponse response,
            @CookieValue(value = "Token", defaultValue = "") String token,
            @RequestParam("np") String np,
            @RequestParam("available") String isAvailable)
    {

       CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isAdmin(token)) {
            ambulance Ambulance = new ambulance(np,
                    isAvailable.equalsIgnoreCase("true") ? availability.AVAILABLE : availability.UNAVAILABLE);
            ambulanceRepository.insert(Ambulance);
            response.setStatus(200);
            return Ambulance.toString();
        }
        else
        {
            response.setStatus(401);
            return "Admin Acess Only";

        }
    }
    @GetMapping("/all")
    public List<ambulance> getAllAmbulances(HttpServletResponse response,
                                            @CookieValue(value = "Token", defaultValue = "") String token)
    {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isLoggedin(token)) {
            response.setStatus(200);
            return  ambulanceRepository.findAll();
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @GetMapping("/findavailable")
    public List<ambulance> findAvailable(HttpServletResponse response,
                                         @CookieValue(value = "Token", defaultValue = "") String token)
    {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isLoggedin(token)) {
            response.setStatus(200);
            return ambulanceRepository.findByStatus(availability.AVAILABLE);
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @GetMapping("/find/{np}")
    public List<ambulance> find(HttpServletResponse response,
                                @CookieValue(value = "Token", defaultValue = "") String token,@PathVariable("np") String np)
    {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isLoggedin(token)) {
            response.setStatus(200);
            return ambulanceRepository.findByNumberplate(np);
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @PutMapping("/available/{np}")
    public ambulance makeAvailable( HttpServletResponse response,
                                    @CookieValue(value = "Token", defaultValue = "") String token,
                                    @PathVariable("np") String np) {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isAdmin(token)) {
            ambulance Ambulance = ambulanceRepository.findByNumberplate(np).get(0);
            Ambulance.setStatus(availability.AVAILABLE);
            ambulanceRepository.save(Ambulance);
            response.setStatus(200);
            return Ambulance;
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @PutMapping("/unavailable/{np}")
    public ambulance makeUnavailable( HttpServletResponse response,
                                      @CookieValue(value = "Token", defaultValue = "") String token,
                                      @PathVariable("np") String np) {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isAdmin(token)) {
            ambulance Ambulance = ambulanceRepository.findByNumberplate(np).get(0);
            Ambulance.setStatus(availability.UNAVAILABLE);
            ambulanceRepository.save(Ambulance);
            return Ambulance;
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    }



