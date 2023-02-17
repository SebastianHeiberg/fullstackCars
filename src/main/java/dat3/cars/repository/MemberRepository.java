package dat3.cars.repository;

import dat3.cars.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

  boolean existsByEmail(String email);

  @Query("SELECT DISTINCT r.member FROM Reservation r")
  List<Member> findMembersWithReservations();

  @Query("SELECT COUNT(r) FROM Reservation r WHERE r.member = ?1")
  Long countReservationsByMember(Member member);

}
