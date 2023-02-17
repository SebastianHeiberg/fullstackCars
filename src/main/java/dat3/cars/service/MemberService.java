package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.dto.ReservationRequest;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final ReservationRepository reservationRepository;

  public MemberService(MemberRepository memberRepository,
                       ReservationRepository reservationRepository) {
    this.memberRepository = memberRepository;
    this.reservationRepository = reservationRepository;
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



}
