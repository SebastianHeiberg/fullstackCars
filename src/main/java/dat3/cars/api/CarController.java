package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/cars")
@RestController
@CrossOrigin


public class CarController {

  CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  //User
  @GetMapping
  List<CarResponse> getCars(){ return carService.getCars(false);}

  //admin
  @GetMapping("/admin")
  List<CarResponse> getCarsAdmin(){ return carService.getCars(true);}


  //Admin
  @GetMapping(path = "/{id}")
  CarResponse getCarById(@PathVariable int id) throws Exception { return carService.findCarById(id); }

  //Admin
//  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping
  CarResponse addCar(@RequestBody CarRequest body){ return carService.addCar(body); }

  //Admin
  @PutMapping("/{id}")
  ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int id){ return carService.editCar(body,id); }

  //Admin
  void setBestDiscount(@PathVariable int id, @PathVariable int value) { carService.setBestDiscount(id,value); }

  // Admin
  @DeleteMapping("/{id}")
  void deleteCarByid(@PathVariable int id) { carService.deleteCarById(id); }

  //Admin
  @GetMapping("/averagePrice")
  Double averagePricePrDay(){
    return carService.findAverageCostCars();
  }

  //Admin
  @GetMapping("/notReserved")
  List<CarResponse> notReserved() {
  return carService.findUnreservedCars();
  }



}
