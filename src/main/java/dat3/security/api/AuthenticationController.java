package dat3.security.api;

import dat3.security.dto.LoginRequest;
import dat3.security.dto.LoginResponse;
import dat3.security.entity.UserWithRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
public class AuthenticationController {

  @Value("${app.token-issuer}")
  private String tokenIssuer;

  @Value("${app.token-expiration}")
  private long tokenExpiration;
  private final AuthenticationManager authenticationManager;

  @Autowired
  JwtEncoder encoder;

  public AuthenticationController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    try {
      UsernamePasswordAuthenticationToken uat = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
      Authentication authentication = authenticationManager.authenticate(uat);

      UserWithRoles user = (UserWithRoles) authentication.getPrincipal();
      Instant now = Instant.now();
      long expiry = tokenExpiration;
      String scope = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(joining(" "));
      JwtClaimsSet claims = JwtClaimsSet.builder()
              .issuer(tokenIssuer)  //Only this for simplicity
              .issuedAt(now)
              .audience(Arrays.asList("not used"))
              .expiresAt(now.plusSeconds(tokenExpiration))
              .subject(user.getUsername())
              .claim("roles",scope)
              .build();

      JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").type("JWT").build();

      String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();


      List<String> roles = user.getRoles().stream().map(role->role.toString()).collect(Collectors.toList());
      return ResponseEntity.ok()
              .body(new LoginResponse(user.getUsername(),token,roles));
    } catch (BadCredentialsException ex) {
      throw ex;
      //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username or password wrong");
    }
  }
}
