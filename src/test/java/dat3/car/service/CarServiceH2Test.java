package dat3.car.service;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.entity.Car;
import dat3.car.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarServiceH2Test {

  @Autowired
  public CarRepository carRepository;

  public CarService carService;


  boolean dataIsReady = false;
  @BeforeEach
  void setUp() {
    if(!dataIsReady){  //Explain this
      carRepository.save(new Car("Tesla","M1",1000,10));
      carRepository.save(new Car("Tesla","M2",2000,20));
      carRepository.save(new Car("Tesla","M3",3000,30));
      carRepository.save(new Car("Tesla","M4",4000,40));
      dataIsReady = true;
      carService = new CarService(carRepository); //Real DB is mocked away with H2
    }
  }

  @Test
  void getCars() {
    int actual = carRepository.findAll().size();
    int expected = 4;
    assertEquals(expected,actual);
  }

  @Test
  void findCarById() {
    CarResponse carResponse = carService.findCarById(4);
    String actual = carResponse.getBrand();
    String expected = "Tesla";
    assertEquals(expected, actual);
  }

  @Test
  void addCar() {
    Car car = new Car("Tesla","M15",3000,30);
    CarRequest carRequest = new CarRequest(car);
    CarResponse carResponse = carService.addCar(carRequest);
    assertEquals(car.getModel(),carResponse.getModel());
  }

  @Test
  void editCar() {
    //ændre prisen til 3000
    Car car = new Car("Tesla","M2",3000,20);
    CarRequest carRequest = new CarRequest(car);

    ResponseEntity<Boolean> responseEntity = carService.editCar(carRequest,1);

    double actual = carRepository.findById(1).get().getPricePrDay();
    double expected = car.getPricePrDay();
    assertEquals(actual, expected);
  }

  @Test
  void setBestDiscount() {

    int expected = 25;
    carService.setBestDiscount(2,expected);
    int actual = carRepository.findById(2).get().getBestDiscount();
    assertEquals(expected, actual);
  }

  @Test
  void deleteCarById() {
    carService.deleteCarById(3);
    assertFalse(carRepository.existsById(3));
  }
}