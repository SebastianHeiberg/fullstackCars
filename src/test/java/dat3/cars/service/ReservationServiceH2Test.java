package dat3.cars.service;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ReservationServiceH2Test {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  ReservationRepository reservationRepository;
  @Autowired
  CarRepository carRepository;

  //bruges til at sætte auto-increment start til 1 igen, i @AfterEach ellers virker testene ikke træk. Credits til chatGPT
  @Autowired
  private EntityManager entityManager;

  ReservationService reservationService;
  private boolean dataIsReady;

  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      entityManager.createNativeQuery("ALTER TABLE CAR ALTER COLUMN id RESTART WITH 1").executeUpdate();
      Car car1 = Car.builder().brand("Tesla").model("M1").pricePrDay(1000).bestDiscount(10).build();
      carRepository.saveAndFlush(car1);
      Member m1 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
      memberRepository.save(m1);
      LocalDate today = LocalDate.now();
      Reservation reservation = new Reservation(today,car1,m1);
      reservationRepository.saveAndFlush(reservation);
      dataIsReady = true;
      reservationService = new ReservationService(carRepository,memberRepository,reservationRepository);
    }
  }



  @Test
  void makeReservation() {

    Car car = carRepository.findById(1).get();
    Member member = memberRepository.findById("m2").get();
    LocalDate today = LocalDate.now();

    ReservationRequest reservationRequest = new ReservationRequest(today,car.getId(), member.getUsername());

    ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,()-> reservationService.makeReservation(reservationRequest));
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
  }

  @Test
  void findReservationsByMember_Username () {
    List<ReservationResponse> reservationResponses = reservationService.findReservationsByMember_Username("m2");
    assertEquals(reservationResponses.size(),1);
  }



}