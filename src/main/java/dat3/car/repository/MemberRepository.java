package dat3.car.repository;

import dat3.car.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {

  @Transactional
  @Modifying
  @Query("update Member m set m.ranking = ?1 where m.username = ?2")
  void updateRankingForUser(int ranking, String username);

  boolean existsByEmail(String email);

}
