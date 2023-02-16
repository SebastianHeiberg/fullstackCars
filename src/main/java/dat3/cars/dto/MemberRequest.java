package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberRequest {
  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String zip;

  // MemberRequest to member conversion
  public static Member getMemberEntity(MemberRequest m){
    return new Member(m.username,m.getPassword(),m.getEmail(), m.getFirstName(), m.getLastName(),m.getStreet(), m.getCity(), m.getZip());
  }

  // Member to MemberRequest conversion
  public MemberRequest(Member m){
    this.firstName = m.getFirstName();
    this.lastName = m.getLastName();
    this.username = m.getUsername();
    this.password = m.getPassword();
    this.email = m.getEmail();
    this.street = m.getStreet();
    this.city = m.getCity();
    this.zip = m.getZip();
  }
}



