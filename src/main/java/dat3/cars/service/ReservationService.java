package dat3.cars.service;

import dat3.cars.dto.CarResponse;
import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

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

  List<ReservationResponse> findReservationsByMember_Username(String username) {
    List<Reservation> reservations = reservationRepository.findReservationsByMember_Username(username);
    List<ReservationResponse> reservationResponses = reservations.stream().map(r -> new ReservationResponse(r)).toList();
    return reservationResponses;
  }

  public List<ReservationResponse> getReservations() {
    List<Reservation> reservations = reservationRepository.findAll();
    List<ReservationResponse> reservationResponses = reservations.stream().map(r -> new ReservationResponse(r)).toList();
    return reservationResponses;
  }

  public List<ReservationResponse> getReservationsForUser(String username) {
    List<Reservation> reservations = reservationRepository.findReservationsByMember_Username(username);
    List<ReservationResponse> reservationResponses = reservations.stream().map(r -> new ReservationResponse(r)).toList();
    return reservationResponses;
  }
}
