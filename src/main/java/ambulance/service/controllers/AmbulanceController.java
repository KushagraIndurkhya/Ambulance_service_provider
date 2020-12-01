package ambulance.service.controllers;
import ambulance.service.models.ambulance;
import ambulance.service.models.availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambulance")
public class AmbulanceController
{
    @Autowired
    AmbulanceRepository ambulanceRepository;
    @RequestMapping(value="/add")
    public String addNewAmbulance(@RequestParam("np") String np,@RequestParam("available") String isAvailable)
    {
        ambulance Ambulance=new ambulance(np,
                isAvailable.equalsIgnoreCase("true")?availability.AVAILABLE:availability.UNAVAILABLE);
        ambulanceRepository.insert(Ambulance);
        return Ambulance.toString();
    }
    @RequestMapping("/all")
    public List<ambulance> getAllAmbulances()
    {
       return  ambulanceRepository.findAll();
    }
    @RequestMapping("/findavailable")
    public List<ambulance> findAvailable()
    {
        return ambulanceRepository.findByStatus(availability.AVAILABLE);
    }
    @RequestMapping("/find/{np}")
    public List<ambulance> find(@PathVariable("np") String np)
    {
        return ambulanceRepository.findByNumberplate(np);
    }
    @RequestMapping("/available/{np}")
    public ambulance makeAvailable(@PathVariable("np") String np) {
        ambulance Ambulance = ambulanceRepository.findByNumberplate(np).get(0);
        Ambulance.setStatus(availability.AVAILABLE);
        return Ambulance;
    }
    @RequestMapping("/unavailable/{np}")
    public ambulance makeUnavailable(@PathVariable("np") String np) {
        ambulance Ambulance = ambulanceRepository.findByNumberplate(np).get(0);
        Ambulance.setStatus(availability.UNAVAILABLE);
        return Ambulance;
    }

    }



