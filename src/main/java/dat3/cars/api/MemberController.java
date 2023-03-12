package dat3.cars.api;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
@CrossOrigin

class MemberController {

  MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  //Admin
  @GetMapping
  List<MemberResponse> getMembers(){ return memberService.getMembers(false);}

  //Admin
  @GetMapping(path = "/{username}")
  MemberResponse getMemberById(@PathVariable String username) throws Exception { return memberService.findMemberByUsername(username); }

  //Anonymous
//  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostMapping
  MemberResponse addMember(@RequestBody MemberRequest body){ return memberService.addMember(body); }

  //Member
  @PutMapping("/{username}")
  ResponseEntity<Boolean> editMember(@RequestBody MemberRequest body, @PathVariable String username){
    return memberService.editMember(body,username);
  }

  //Admin
  @PatchMapping("/ranking/{username}/{value}")
  void setRankingForUser(@PathVariable String username, @PathVariable int value) {
    memberService.setRankingForUser(username,value);
  }

  // Admin
  @DeleteMapping("/{username}")
  void deleteMemberByUsername(@PathVariable String username) { memberService.deleteMemberByUsername(username);}



}

