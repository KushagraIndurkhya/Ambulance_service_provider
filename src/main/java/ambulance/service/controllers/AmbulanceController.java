package ambulance.service.controllers;
import ambulance.service.models.ambulance;
import ambulance.service.models.availability;
import ambulance.service.security.CookieUtills;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/ambulance") @Api(value = "Ambulance related use cases")

public class AmbulanceController
{
    @Autowired
    AmbulanceRepository ambulanceRepository;
    @PostMapping(value="/add")
    @ApiOperation(value = " [ADMIN] Add an ambulance to database")
    public ambulance addNewAmbulance(
            HttpServletResponse response,
            @CookieValue(value = "Token", defaultValue = "") String token,
            @RequestParam("NumberPlate") String np,
            @RequestParam("isAvailable") String isAvailable)
    {

        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isAdmin(token)) {
            try {
                ambulance Ambulance = new ambulance(np,
                        isAvailable.equalsIgnoreCase("true") ? availability.AVAILABLE : availability.UNAVAILABLE);
                ambulanceRepository.insert(Ambulance);
                return Ambulance;
            }
            catch (Exception e)
            {
                response.setStatus(403);
                System.out.print(e.getStackTrace());
                return null;
            }

        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @PostMapping("/all")
    @ApiOperation(value = "Get all ambulance details")
    public List<ambulance> getAllAmbulances(HttpServletResponse response,
                                            @CookieValue(value = "Token", defaultValue = "") String token)
    {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isLoggedin(token)) {
            try {
                response.setStatus(200);
                return ambulanceRepository.findAll();
            }
            catch (Exception e)
            {
                response.setStatus(404);
                System.out.print(e.getStackTrace());
                return null;
            }
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @PostMapping("/findavailable")
    @ApiOperation(value = "Find all available ambulances")
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

    @PostMapping(value = "/find/ {NumberPlate}")
    @ApiOperation(value = "List of available ambulance")
    public ambulance find(HttpServletResponse response,
                                @CookieValue(value = "Token", defaultValue = "")
                                        String token,@PathVariable("NumberPlate") String np)
    {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isLoggedin(token)) {
            try {
                response.setStatus(200);
                return ambulanceRepository.findByNumberplate(np).get(0);
            }
            catch (Exception e) {
                response.setStatus(404);
                System.out.print(e.getStackTrace());
                return null;
            }
        }
        else {
            response.setStatus(401);
            return null;
        }

    }
    @PutMapping("/available/ {NumberPlate}")
    @ApiOperation(value = " [ADMIN] Make an ambulance available")
    public ambulance makeAvailable( HttpServletResponse response,
                                    @CookieValue(value = "Token", defaultValue = "") String token,
                                    @PathVariable("NumberPlate") String np) {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isAdmin(token)) {
            try{
            ambulance Ambulance = ambulanceRepository.findByNumberplate(np).get(0);
            Ambulance.setStatus(availability.AVAILABLE);
            ambulanceRepository.save(Ambulance);
            response.setStatus(200);
            return Ambulance;
        }
        catch (Exception e)
        {
            response.setStatus(404);
            System.out.print(e.getStackTrace());
            return null;
        }
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
    @PutMapping("/unavailable/ {NumberPlate}")
    @ApiOperation(value = " [ADMIN] Make an ambulance Unavailable")
    public ambulance makeUnavailable( HttpServletResponse response,
                                      @CookieValue(value = "Token", defaultValue = "") String token,
                                      @PathVariable("NumberPlate") String np) {
        CookieUtills cookieUtills =new CookieUtills();
        if(cookieUtills.isAdmin(token)) {
            try {
                ambulance Ambulance = ambulanceRepository.findByNumberplate(np).get(0);
                Ambulance.setStatus(availability.UNAVAILABLE);
                ambulanceRepository.save(Ambulance);
                response.setStatus(200);
                return Ambulance;
            }
           catch (Exception e)
           {
               response.setStatus(404);
               System.out.print(e.getStackTrace());
               return null;
           }
        }
        else {
            response.setStatus(401);
            return null;
        }
    }
}