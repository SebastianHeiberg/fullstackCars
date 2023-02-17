package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ReservationResponse {

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate rentalDate;
  private Long id;
  private String username;
  private int carId;
  private LocalDate reservationdate;


  public ReservationResponse(Reservation r) {
    this.id = r.getId();
    this.username = r.getMember().getUsername();
    this.carId = r.getCar().getId();
    this.rentalDate = r.getRentalDate();
    this.reservationdate = r.getReservationDate();
  }
}

