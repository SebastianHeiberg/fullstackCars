package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryH2Test {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  ReservationRepository reservationRepository;
  @Autowired
  public CarRepository carRepository;

  boolean dataIsReady = false;

  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      Car car1 = Car.builder().brand("Tesla").model("M1").pricePrDay(1000).bestDiscount(10).build();
      Car car2 = Car.builder().brand("Audi").model("M2").pricePrDay(2000).bestDiscount(20).build();
      car1 = carRepository.saveAndFlush(car1);
      car2 = carRepository.saveAndFlush(car2);
      Member m1 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
      memberRepository.save(m1);
      LocalDate today = LocalDate.now();
      Reservation reservation = new Reservation(today,car1,m1);
      reservationRepository.save(reservation);
      dataIsReady = true;

    }
  }

  @Test
  void findCarsByIdAndModel(){
    int actual = carRepository.findCarsByBrandAndAndModel("Tesla","M1").size();
    int expected = 1;
    assertEquals(expected,actual);
  }

  @Test
  void findAveragePricePrDayForAllCars(){
    double actual = carRepository.findAverageCostCars();
    double expeted = 1500;
    assertEquals(actual,expeted);
  }

  @Test
  void findBestDiscount(){
    List<Car> discountCars = carRepository.findCarsWithHighestDiscount();
    assertEquals(1,discountCars.size());
    assertEquals(discountCars.get(0).getBestDiscount(),20);

  }

  @Test
  void findUnreservedCars(){

    assertEquals(carRepository.findUnreservedCars().size(),1);
      }

}

