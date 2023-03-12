package dat3.cars.api;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
@CrossOrigin

public class ReservationController {

  ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  // admin
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ReservationResponse createReservation(@RequestBody ReservationRequest body){ return reservationService.makeReservation(body); }

  @GetMapping
  List<ReservationResponse> getReservation(){ return reservationService.getReservations(); }
}
