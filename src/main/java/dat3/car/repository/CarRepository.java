package dat3.car.repository;

import dat3.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CarRepository extends JpaRepository<Car,Integer> {

  @Transactional
  @Modifying
  @Query("update Car c set c.bestDiscount = ?1 where c.id = ?2")
  void setCarBestDiscount(Integer bestDiscount, int id);

}
