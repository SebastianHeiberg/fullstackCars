package dat3.car.repository;

import dat3.car.entity.Reservation;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository <Reservation,Long> {

  @Query("""
      select (count(r) > 0) from Reservation r
      where r.rentalDate = ?1 and r.car.id = ?2""")
  boolean checkReservation(LocalDate rentalDate, int id);


boolean existsByCar_IdAndRentalDate(int carId, LocalDate rentalDate);

  @Query("""
      select (id(r) > 0) from Reservation r
      where r.rentalDate = ?1 and r.car.id = ?2""")
  int findReservationId(LocalDate rentalDate, int id);


}
