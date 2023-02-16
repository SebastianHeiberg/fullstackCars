package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.dto.ReservationRequest;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public List<MemberResponse> getMembers(boolean includeAll) {
    List<Member> members = memberRepository.findAll();
    List<MemberResponse> memberResponses = members.stream().map(m -> new MemberResponse(m, includeAll)).toList();
    return memberResponses;
  }

  public MemberResponse addMember(MemberRequest memberRequest) {

    if (memberRepository.existsById(memberRequest.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member with this ID already exist");
    }
    if (memberRepository.existsByEmail(memberRequest.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member with this Email already exist");
    }
    Member newMember = MemberRequest.getMemberEntity(memberRequest);
    newMember = memberRepository.save(newMember);

    return new MemberResponse(newMember, false);
  }

  public MemberResponse findMemberByUsername(String username) {

    Member m = memberRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this username dont exist"));
    return new MemberResponse(m, true);
  }

  public ResponseEntity<Boolean> editMember(MemberRequest body, String username) {

    Member member = memberRepository.findById(username).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this username dont exist"));
    member.setPassword(body.getPassword());
    member.setEmail(body.getEmail());
    member.setFirstName(body.getFirstName());
    member.setLastName(body.getLastName());
    member.setStreet(body.getStreet());
    member.setCity(body.getCity());
    member.setZip(body.getZip());
    memberRepository.save(member);

    return new ResponseEntity<>(true, HttpStatus.OK);
  }


  public void setRankingForUser(String username, int value) {
    Member member = memberRepository.findById(username).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this username dont exist"));
    member.setRanking(value);
    memberRepository.save(member);
  }

  public void deleteMemberByUsername(String username) {
    if (!memberRepository.existsById(username)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this username dont exist");
    }
    memberRepository.deleteById(username);
  }

  public void makeReservation(ReservationRequest body, String username){

  }

  /*

Hint: annotate the LocalDate in your DTO's like this @JsonFormat(pattern = "yyyy-MM-dd") and pass data in this format from Postman while testing
Implement this service in two steps.

First, let the member reserve a car, even if it's already reserved
Now change this behaviour to only allow the car to be reserved for a given date, if not already reserved
Now add yet another check to prevent a reservation made for a day in the past
Implement service tests for what you did in step b

   */

}
