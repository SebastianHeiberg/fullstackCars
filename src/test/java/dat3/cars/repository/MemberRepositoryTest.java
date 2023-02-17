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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  ReservationRepository reservationRepository;
  @Autowired
  CarRepository carRepository;

  ReservationService reservationService;
  private boolean dataIsReady;



  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      Car car1 = Car.builder().brand("Tesla").model("M1").pricePrDay(1000).bestDiscount(10).build();
       Car car2 = Car.builder().brand("Audi").model("M2").pricePrDay(2000).bestDiscount(20).build();
      carRepository.save(car1);
      carRepository.save(car2);
      Member m1 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
      Member m2 = new Member("m3", "test12", "m3@a.dk", "bb", "Bansen", "xx vej 34", "Lyngby", "2800");
      memberRepository.save(m1);
      memberRepository.save(m2);
      LocalDate today = LocalDate.now();
      Reservation reservation = new Reservation(today,car1,m1);
      reservationRepository.save(reservation);



      dataIsReady = true;
      reservationService = new ReservationService(carRepository,memberRepository,reservationRepository);
    }
  }

    @Test
    void findAllMembersWhoHasAReservation(){
    int actual = memberRepository.findMembersWithReservations().size();
      int expected = 1;
      assertEquals(expected,actual);
    }


    @Test
    void countReservationsByMember() {
    Member member = memberRepository.findById("m2").get();
    Long actual = memberRepository.countReservationsByMember(member);
    assertEquals(actual,1);
    }

  }