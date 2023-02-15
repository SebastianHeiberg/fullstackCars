package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.entity.Reservation;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import dat3.car.entity.Member;
import dat3.car.repository.ReservationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Controller
public class DeveloperData implements ApplicationRunner {

  MemberRepository memberRepository;
  CarRepository carRepository;

  public DeveloperData (MemberRepository memberRepository,
                        CarRepository carRepository,
                        ReservationRepository reservationRepository)
  {
    this.memberRepository = memberRepository;
    this.carRepository = carRepository;
    this.reservationRepository = reservationRepository;
  }

  private final String passwordUsedByAll = "test12";
  private final ReservationRepository reservationRepository;


  @Override
  public void run(ApplicationArguments args) throws Exception {

      Member m1 = new Member("member1", passwordUsedByAll, "memb1@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
      Member m2 = new Member("member2", passwordUsedByAll, "aaa@dd.dk", "Hanne", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");


      memberRepository.save(m1);
      memberRepository.save(m2);

    Car c1 = new Car("Tesla","M1",2500,20);
    Car c2 = new Car("Ford","Fieste",1500,10);
    carRepository.save(c1);
    carRepository.save(c2);

    Reservation reservation = new Reservation();
    reservation.setReservationDate(LocalDate.now());
    reservation.setRentalDate(LocalDate.now());
    reservation.setCar(c1);
    reservation.setMember(m1);
    reservationRepository.save(reservation);


    }





  }

