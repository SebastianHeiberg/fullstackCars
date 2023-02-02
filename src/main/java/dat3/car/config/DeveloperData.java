package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import dat3.car.entity.Member;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DeveloperData implements ApplicationRunner {

  MemberRepository memberRepository;
  CarRepository carRepository;

  public DeveloperData (MemberRepository memberRepository,
                        CarRepository carRepository)
  {
    this.memberRepository = memberRepository;
    this.carRepository = carRepository;
  }

  private final String passwordUsedByAll = "test12";


  @Override
  public void run(ApplicationArguments args) throws Exception {

      Member m1 = new Member("member1", passwordUsedByAll, "memb1@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
      Member m2 = new Member("member2", passwordUsedByAll, "aaa@dd.dk", "Hanne", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
      m1.setFavoriteCarColors(new ArrayList<String>(Arrays.asList("Yellow")));
      m2.setFavoriteCarColors(new ArrayList<String>(Arrays.asList("Red")));
      m1.setPhones(new HashMap<String, String>(){
        {
          put("Nokia", "11234");
        }});

      memberRepository.save(m1);
      memberRepository.save(m2);

    Car c1 = new Car("Tesla","M1",2500,20);
    Car c2 = new Car("Ford","Fieste",1500,10);
    carRepository.save(c1);
    carRepository.save(c2);

    }





  }

