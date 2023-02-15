package dat3.car.service;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import dat3.car.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class ReservationService {

  CarRepository carRepository;
  MemberRepository memberRepository;
  ReservationRepository reservationRepository;

  public ReservationService(CarRepository carRepository, MemberRepository memberRepository, ReservationRepository reservationRepository) {
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
    this.reservationRepository = reservationRepository;
  }

  public ReservationResponse makeReservation(ReservationRequest body) {

//    if (reservationRepository.checkReservation(body.getRentalDate(), body.getCarId())){
//      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already a reservation made on this date");
//    }
    if (reservationRepository.existsByCar_IdAndRentalDate(body.getCarId(),body.getRentalDate())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already a reservation made on this date");
    }

    Car car = carRepository.findById(body.getCarId()).
        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this ID dont exist"));

    Member member = memberRepository.findById(body.getUsername()).
        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this username dont exist"));

    LocalDate rentalDate;
    if (body.getRentalDate().isBefore(LocalDate.now())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is in the past");
    } else {
      rentalDate = body.getRentalDate();
    }

    Reservation reservation = new Reservation(rentalDate,car,member);
    reservationRepository.save(reservation);
    return new ReservationResponse(reservation);
  }
}
