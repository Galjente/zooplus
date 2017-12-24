package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.Authority;
import eu.galjente.zooplus.user.domain.entity.User;
import eu.galjente.zooplus.user.domain.repository.AuthorityRepository;
import eu.galjente.zooplus.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder,
					   UserRepository userRepository,
					   AuthorityRepository authorityRepository,
					   AuthenticationManager authenticationManager) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.authenticationManager = authenticationManager;
	}

	public User registerUser(RegistrationForm registrationForm) {
		final String encodedPassword = passwordEncoder.encode(registrationForm.getPassword());
		final Authority defaultAuthority = authorityRepository.findOneByName(Authority.ROLE_USER);

		User user = registrationForm.convertTo(new RegistrationFormUserConverter());
		user.setPassword(encodedPassword);
		user.getAuthorities().add(defaultAuthority);

		return userRepository.save(user);
	}

	public void signInRegisteredUser(User user, RegistrationForm registrationForm) {
		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user.getEmail(), registrationForm.getPassword(), user.getSimpleGrantedAuthorities());

		authenticationManager.authenticate(userToken);

		if (userToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(userToken);
		}
	}
}
