package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarServiceH2Test {

  @Autowired
  public CarRepository carRepository;

  public CarService carService;

  boolean dataIsReady = false;

  //bruges til at sætte auto-increment start til 1 igen, i @AfterEach ellers virker testene ikke træk. Credits til chatGPT
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ReservationRepository reservationRepository;


  @BeforeEach
  void setUp() {
    if (!dataIsReady) {  //Explain this
      entityManager.createNativeQuery("ALTER TABLE CAR ALTER COLUMN id RESTART WITH 1").executeUpdate();
      Car car1 = Car.builder().brand("Tesla").model("M1").pricePrDay(1000).bestDiscount(10).build();
      Car car2 = Car.builder().brand("Audi").model("M2").pricePrDay(2000).bestDiscount(20).build();
      car1 = carRepository.save(car1);
      car2 = carRepository.save(car2);
      dataIsReady = true;
      Member m1 = new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
      memberRepository.save(m1);
      LocalDate today = LocalDate.now();
      Reservation reservation = new Reservation(today,car1,m1);
      reservationRepository.save(reservation);
      carService = new CarService(carRepository); //Real DB is mocked away with H2
    }
  }




  @Test
  void getCars() {
    int actual = carRepository.findAll().size();
    int expected = 2;
    assertEquals(expected, actual);
  }

  @Test
  void addCar() {
    Car car = new Car("Tesla", "M15", 3000, 30);
    CarRequest carRequest = new CarRequest(car);
    CarResponse carResponse = carService.addCar(carRequest);
    assertEquals(car.getModel(), carResponse.getModel());
  }

  @Test
  void editCar() {
    //ændre prisen til 3000
    Car car = new Car("Tesla", "M2", 3000, 20);
    CarRequest carRequest = new CarRequest(car);

    ResponseEntity<Boolean> responseEntity = carService.editCar(carRequest, 1);

    double actual = carRepository.findById(1).get().getPricePrDay();
    double expected = car.getPricePrDay();
    assertEquals(actual, expected);

  }

  @Test
  void setBestDiscount() {

    int expected = 25;
    carService.setBestDiscount(2, expected);
    int actual = carRepository.findById(2).get().getBestDiscount();
    assertEquals(expected, actual);
  }

  @Test
  void findCarById() {
    CarResponse carResponse = carService.findCarById(2);
    String actual = carResponse.getBrand();
    String expected = "Audi";
    assertEquals(expected, actual);
  }

  @Test
  void deleteCarById() {
    carService.deleteCarById(2);
    assertFalse(carRepository.existsById(2));
    }



  @Test
  void findAllCarsByBrandAndModel() {
    List<CarResponse> carResponseList = carService.findAllCarsByBrandAndModel("Audi","M2");
    assertEquals(carResponseList.size(),1);
  }

  @Test
  void findAverageCostCars(){
    double actualPricAverage = carService.findAverageCostCars();
    double expected = 1500;
    assertEquals(expected, actualPricAverage);
  }

  @Test
  void findUnreservedCars(){
    List <CarResponse> carResponseList = carService.findUnreservedCars();
    assertEquals(carResponseList.size(),1);
  }

}
