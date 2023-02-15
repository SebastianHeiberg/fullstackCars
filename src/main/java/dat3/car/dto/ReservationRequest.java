package dat3.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationRequest {

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate rentalDate;
  int carId;
  String username;


}
