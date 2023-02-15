package dat3.car.service;

import dat3.car.dto.ReservationRequest;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import dat3.car.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class ReservationServiceH2Test {

  private boolean dataIsReady;

  MemberRepository memberRepository;

  ReservationRepository reservationRepository;

  CarRepository carRepository;

  ReservationService reservationService;


  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      Car car1 = Car.builder().brand("Tesla").model("M1").pricePrDay(1000).bestDiscount(10).build();
      carRepository.save(car1);
      Member m1 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
      memberRepository.save(m1);
      LocalDate today = LocalDate.now();
      Reservation reservation = new Reservation(today,car1,m1);

      dataIsReady = true;
//      reservationService = new ReservationService(reservationRepository); //Real DB is mocked away with H2
    }
  }

  @Test
  void makeReservation() {
//
//    ReservationRequest rs = new ReservationRequest(today,car1.getId(), m1.getUsername());
//
//    Reservation reservation = new Reservation()
//    reservationService.makeReservation()
  }
}