package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.entity.Member;
import dat3.cars.repository.ReservationRepository;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DeveloperData implements ApplicationRunner {

  MemberRepository memberRepository;
  CarRepository carRepository;


  public DeveloperData(MemberRepository memberRepository, CarRepository carRepository, ReservationRepository reservationRepository) {
    this.memberRepository = memberRepository;
    this.carRepository = carRepository;
    this.reservationRepository = reservationRepository;
  }

  private final String passwordUsedByAll = "test12";
  private final ReservationRepository reservationRepository;


  @Override
  public void run(ApplicationArguments args) throws Exception {

      Member m1 = new Member("member1", passwordUsedByAll, "memb1@a.dk", "Kurt", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");
      Member m2 = new Member("member2", passwordUsedByAll, "aaa@dd.dk", "Hanne", "Wonnegut", "Lyngbyvej 2", "Lyngby", "2800");


      memberRepository.save(m1);
      memberRepository.save(m2);

    Car c1 = new Car("Tesla","M1",2500,20);
    Car c2 = new Car("Ford","Fieste",1500,10);
    carRepository.save(c1);
    carRepository.save(c2);

    Reservation reservation = new Reservation();
    reservation.setReservationDate(LocalDate.now());
    reservation.setRentalDate(LocalDate.now().plusDays(5));
    reservation.setCar(c1);
    reservation.setMember(m1);
    reservationRepository.save(reservation);

    setupUserWithRoleUsers();

    }



@Autowired
  UserWithRolesRepository userWithRolesRepository;

  /*****************************************************************************************
   NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
   iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO
   *****************************************************************************************/
  private void setupUserWithRoleUsers() {

    System.out.println("******************************************************************************");
    System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
    System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
    System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
    System.out.println("******************************************************************************");
    UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
    UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
    UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
    UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
    user1.addRole(Role.USER);
    user1.addRole(Role.ADMIN);
    user2.addRole(Role.USER);
    user3.addRole(Role.ADMIN);
    //No Role assigned to user4
    userWithRolesRepository.save(user1);
    userWithRolesRepository.save(user2);
    userWithRolesRepository.save(user3);
    userWithRolesRepository.save(user4);
  }




  }

