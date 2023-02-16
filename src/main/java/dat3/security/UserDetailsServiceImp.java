package dat3.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  UserWithRolesRepository userWithRolesRepository;

  public UserDetailsServiceImp(UserWithRolesRepository userWithRolesRepository) {
    this.userWithRolesRepository = userWithRolesRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("loadUsersByUsername");
    final Optional<UserWithRoles> optionalUser = userWithRolesRepository.findById(username);
    return optionalUser.orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Incorrect username or password"));
  }
}
