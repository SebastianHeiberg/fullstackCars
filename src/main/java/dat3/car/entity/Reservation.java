package dat3.car.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  private LocalDate reservationDate;

  @Column(nullable = false)
  private LocalDate rentalDate;

  @ManyToOne
  private Car car;

  @ManyToOne
  private Member member;

  public Reservation(LocalDate rentalDate, Car car, Member member) {
    this.rentalDate = rentalDate;
    this.car = car;
    this.member = member;
  }
}