package ambulance.service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.String;
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	

	}
	// @RequestMapping("/")
	// public String welcome()
	// {
	// 	return "Welcome to Ambulance service Provider";
	// }
}
