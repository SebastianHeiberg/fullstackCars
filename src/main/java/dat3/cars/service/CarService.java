package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

  private CarRepository carRepository;

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<CarResponse> getCars(boolean includeAll) {

    List<Car> cars = carRepository.findAll();
    List<CarResponse> listOfCarResponses = cars.stream().map(c -> new CarResponse(c, includeAll)).toList();
    return listOfCarResponses;
  }

  public CarResponse findCarById(int id) {
    Car car = carRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this id dont exist"));
    CarResponse cr = new CarResponse(car, true);
    return cr;
  }

  public CarResponse addCar(CarRequest body) {
    Car car = CarRequest.getCarEntity(body);

    if (carRepository.existsById(car.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with this ID already exist");
    }

    carRepository.save(car);
    return new CarResponse(car, false);
  }

  public ResponseEntity<Boolean> editCar(CarRequest body, int id) {

    Car car = carRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID dont exist"));
    car.setModel(body.getModel());
    car.setBrand(body.getBrand());
    car.setBestDiscount(body.getBestDiscount());
    car.setPricePrDay(body.getPricePrDay());
    carRepository.save(car);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  public void setBestDiscount(int id, int value) {
    Car car = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID dont exist"));
    car.setBestDiscount(value);
    carRepository.save(car);
  }

  public void deleteCarById(int id) {
    if (!carRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this Id dont exist");
    }
    carRepository.deleteById(id);
  }


  public List<CarResponse> findAllCarsByBrandAndModel(String brand, String model){
    List <Car> carList = carRepository.findCarsByBrandAndAndModel(brand, model);
    List<CarResponse> carResponseList = carList.stream().map(c -> new CarResponse(c,true) ).toList();
    return carResponseList;
  }

  public double findAverageCostCars(){
    return carRepository.findAverageCostCars();
  }

  public List<CarResponse> findUnreservedCars(){
    List<Car> cars = carRepository.findUnreservedCars();
    List<CarResponse> carResponseList = cars.stream().map(c -> new CarResponse(c,true)).toList();
    return carResponseList;
  }


}