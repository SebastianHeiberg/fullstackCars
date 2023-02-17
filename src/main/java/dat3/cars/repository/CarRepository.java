package dat3.cars.repository;

import dat3.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer> {

  List<Car> findCarsByBrandAndAndModel(String brand, String model);


  @Query("SELECT AVG(c.pricePrDay) FROM Car c")
  double findAverageCostCars();


  @Query("SELECT c FROM Car c WHERE c.bestDiscount = (SELECT MAX(c2.bestDiscount) FROM Car c2)")
  List<Car> findCarsWithHighestDiscount();


  @Query("SELECT c FROM Car c WHERE NOT EXISTS (SELECT r FROM Reservation r WHERE r.car = c)")
  List<Car> findUnreservedCars();


}
