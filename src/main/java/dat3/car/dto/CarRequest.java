package dat3.car.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.car.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarRequest {

  private int id;
  private String brand;
  private String model;
  private double pricePrDay;
  private int bestDiscount;

  // CarRequest to Car conversion
  public static Car getCarEntity(CarRequest cr){
    return new Car(cr.brand, cr.model,cr.pricePrDay,cr.bestDiscount);
  }

  // Car to CarRequest coversion
  public CarRequest (Car c){
    this.brand = c.getBrand();
    this.model = c.getModel();
    this.pricePrDay = c.getPricePrDay();
    this.bestDiscount = c.getBestDiscount();
  }

}
