package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryH2Test {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  ReservationRepository reservationRepository;
  @Autowired
  CarRepository carRepository;

  private boolean dataIsReady;

  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      Car car1 = Car.builder().brand("Tesla").model("M1").pricePrDay(1000).bestDiscount(10).build();
      carRepository.save(car1);
      Member m1 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
      Member m2 = new Member("m3", "test12", "m3@a.dk", "bb", "hansen", "xx vej 34", "Lyngby", "2800");
      memberRepository.save(m1);
      memberRepository.save(m2);
      LocalDate today = LocalDate.now();
      Reservation reservation = new Reservation(today,car1,m1);
      reservationRepository.save(reservation);
      dataIsReady = true;
    }
  }

  @Test
  void searchReservationByMemberFirstName(){
    List<Reservation> reservations = reservationRepository.findReservationsByMember_Username("m2");
    assertEquals(1,reservations.size());
  }



}