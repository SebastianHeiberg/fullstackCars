package dat3.car.api;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.service.CarService;
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
  CarResponse getMemberById(@PathVariable String id) throws Exception { return carService.findCarById(id); }

  //Anonymous
//  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping
  CarResponse addMember(@RequestBody CarRequest body){
    return carService.addCar(body);
  }

  //Member
  @PutMapping("/{id}")
  ResponseEntity<Boolean> editMember(@RequestBody CarRequest body, @PathVariable String id){
    return carService.editMember(body,username);
  }

  //Admin
  @PatchMapping("/ranking/{username}/{value}")
  void setRankingForUser(@PathVariable String username, @PathVariable int value) {
    carService.setRankingForUser(username,value);
  }

  // Admin
  @DeleteMapping("/{username}")
  void deleteMemberByUsername(@PathVariable String username) { carService.deleteMemberByUsername(username);}


}
