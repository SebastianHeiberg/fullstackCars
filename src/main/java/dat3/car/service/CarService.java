package dat3.car.service;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.entity.Car;
import dat3.car.repository.CarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

private CarRepository carRepository;

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<CarResponse> getCars(boolean includeAll) {

    List<Car> cars = carRepository.findAll();
    List<CarResponse> listOfCarResponses = cars.stream().map( c -> new CarResponse(c,includeAll)).toList();
    return listOfCarResponses;
  }

  public CarResponse findCarById(int id) {
    return null;
  }


  public CarResponse addCar(CarRequest body) {
    return null;
  }

  public ResponseEntity<Boolean> editMember(CarRequest body, int id) {
    return null;
  }


  public void setRankingForUser(int username, int value) {

  }

  public void deleteMemberByUsername(int id) {

  }
}
