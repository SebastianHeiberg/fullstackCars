package dat3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
//public class CarsApplication {
//
//  public static void main(String[] args) {
//    SpringApplication.run(CarsApplication.class, args);
//  }
//
//}

@SpringBootApplication( exclude = {SecurityAutoConfiguration.class} )
public class CarsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CarsApplication.class, args);
  }
}
