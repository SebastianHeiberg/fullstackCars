package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {

  CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  //Admin
  @GetMapping
  List<CarResponse> getCars(){ return carService.getCars(true);}

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
  @PatchMapping("/bestDiscount/{id}/{value}")
  void setBestDiscount(@PathVariable int id, @PathVariable int value) { carService.setBestDiscount(id,value); }

  // Admin
  @DeleteMapping("/{id}")
  void deleteCarByid(@PathVariable int id) { carService.deleteCarById(id); }


}
