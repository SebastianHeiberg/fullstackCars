package dat3.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.car.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ReservationResponse {


  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate rentalDate;
  private Long id;
  private String username;
  private int carId;


  public ReservationResponse(Reservation r) {
    this.id = r.getId();
    this.username = r.getMember().getUsername();
    this.carId = r.getCar().getId();
    this.rentalDate = r.getRentalDate();
  }
}

