package dat3.car.service;

import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Member;
import dat3.car.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberServiceH2Test {

  @Autowired
  public MemberRepository memberRepository;

  MemberService memberService;

  boolean dataIsReady = false;
  @BeforeEach
  void setUp() {
    if(!dataIsReady){  //Explain this
      memberRepository.save(new Member("m1", "test12", "m1@a.dk",  "bb", "Olsen", "xx vej 34", "Lyngby", "2800"));
      memberRepository.save(new Member("m2", "test12", "m2@a.dk", "aa", "hansen", "xx vej 34", "Lyngby", "2800"));
      dataIsReady = true;
      memberService = new MemberService(memberRepository); //Real DB is mocked away with H2
    }
  }

  @Test
  void getMembersAdmin() {
    List<MemberResponse> members = memberService.getMembers(true);
    assertEquals(2,members.size());
    assertNotNull(members.get(0).getCreated());
  }

  @Test
  void findMemberByUsername() throws Exception {
    MemberResponse response = memberService.findMemberByUsername("m1");
    assertEquals("m1@a.dk",response.getEmail());
  }

  @Test
  void findMemberByNotExistingUsername() throws Exception {
    ResponseStatusException ex = Assertions.assertThrows(ResponseStatusException.class,()-> memberService.findMemberByUsername("i-dont-exist"));
    assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
  }


  @Test
  void addMember() {
    Member m = new Member("m3", "test12", "m3@a.dk",  "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    MemberRequest mr = new MemberRequest(m);
    memberService.addMember(mr);
    assertEquals(3, memberRepository.findAll().size());
    assertEquals("bb", mr.getFirstName());

  }

  @Test
  void setRankingForUser () {
    memberService.setRankingForUser("m1",0);
    int expected = memberService.findMemberByUsername("m1").getRanking();
    assertEquals(0,expected);

    memberService.setRankingForUser("m1",4);
    int newValue = memberService.findMemberByUsername("m1").getRanking();
    assertNotEquals(expected,newValue);
  }

  @Test
  void deleteMember() {
    memberService.deleteMemberByUsername("m2");
    assertFalse(memberRepository.existsById("m2"));
  }

  @Test
  void editMember() {
    //Ændrer efternavn fra Olsen til Hansen
    Member member = new Member("m1", "test12", "m1@a.dk",  "bb", "Hansen", "xx vej 34", "Lyngby", "2800");
    MemberRequest mr = new MemberRequest(member);
    memberService.editMember(mr, member.getUsername());
    assertEquals("Hansen", memberRepository.findById("m1").get().getLastName());

  }


}