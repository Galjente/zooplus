package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.User;
import eu.galjente.zooplus.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Primary
@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
								  .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not exists", username)));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getSimpleGrantedAuthorities());
	}
}
