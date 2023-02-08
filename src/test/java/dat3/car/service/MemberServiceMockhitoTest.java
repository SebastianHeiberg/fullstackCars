package dat3.car.service;

import dat3.car.dto.MemberRequest;
import dat3.car.entity.Member;
import dat3.car.dto.MemberResponse;
import dat3.car.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockhitoTest {


  @Mock
  MemberRepository memberRepository;

  MemberService memberService;

  @BeforeEach
  void setUp() {
    memberService = new MemberService(memberRepository);
  }

  @Test
  void getMembersAdmin() {
    Member m1 = new Member("m1", "m1@a.dk", "test12", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    Member m2 = new Member("m2", "m2@a.dk", "test12", "aa", "hansen", "xx vej 34", "Lyngby", "2800");
    m1.setCreated(LocalDateTime.now());
    m2.setCreated(LocalDateTime.now());

    Mockito.when(memberRepository.findAll()).thenReturn(List.of(m1,m2));
    List<MemberResponse> members = memberService.getMembers(true);
    assertEquals(2,members.size());
    assertNotNull(members.get(0).getCreated());
  }

  @Test
  void addMember() {
    Member m1 = new Member("m1", "test12", "m1@a.dk", "bb", "Olsen", "xx vej 34", "Lyngby", "2800");
    //If Id was generated by the server, for example as for car you would need to set the id here
    Mockito.when(memberRepository.save(any(Member.class))).thenReturn(m1);

    //Quick way to get a MemberRequest (remember eventually values come via a incoming JSON object)
    MemberRequest request = new MemberRequest(m1);
    MemberResponse response = memberService.addMember(request);
    assertEquals("m1@a.dk",response.getEmail());
  }



  @Test
  void getMembers() {
  }



}