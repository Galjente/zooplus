package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.Authority;
import eu.galjente.zooplus.user.domain.entity.User;
import eu.galjente.zooplus.user.domain.repository.AuthorityRepository;
import eu.galjente.zooplus.user.domain.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryIntegrationTest {

	private final static String USER_EMAIL = "aaa@aa.lv";
	private final static String USER_PASSWORD = "test";
	private final static LocalDate USER_BIRTHDAY = LocalDate.of(2017, 10, 10);
	private final static String USER_COUNTRY = "New country";
	private final static String USER_CITY = "New city";
	private final static String USER_ADDRESS = "New āūģš 222/a - 222";
	private final static String USER_ZIP = "New zip";

	@Autowired private UserRepository userRepository;
	@Autowired private AuthorityRepository authorityRepository;

	@Test
	public void saveNewUser() {
		//given
		Authority defaultAuthority = authorityRepository.findOneByName(Authority.ROLE_USER);
		User user = createDefaultUser();

		//when
		userRepository.save(user);

		//then
		User found = userRepository.findOne(user.getId());

		assertThat(found.getEmail()).isEqualTo(USER_EMAIL);
		assertThat(found.getPassword()).isEqualTo(USER_PASSWORD);
		assertThat(found.getBirthday()).isEqualTo(USER_BIRTHDAY);
		assertThat(found.getCountry()).isEqualTo(USER_COUNTRY);
		assertThat(found.getCity()).isEqualTo(USER_CITY);
		assertThat(found.getAddress()).isEqualTo(USER_ADDRESS);
		assertThat(found.getZip()).isEqualTo(USER_ZIP);
		assertThat(found.getAuthorities()).containsExactly(defaultAuthority);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void saveDuplicateUser() {
		//given
		User user1 = createDefaultUser();
		User user2 = createDefaultUser();

		//when
		userRepository.save(user1);
		userRepository.save(user2);
	}

	private User createDefaultUser() {
		Authority defaultAuthority = authorityRepository.findOneByName(Authority.ROLE_USER);
		User user = new User();
		user.setEmail(USER_EMAIL);
		user.setPassword(USER_PASSWORD);
		user.setBirthday(USER_BIRTHDAY);
		user.setCountry(USER_COUNTRY);
		user.setCity(USER_CITY);
		user.setAddress(USER_ADDRESS);
		user.setZip(USER_ZIP);
		user.getAuthorities().add(defaultAuthority);

		return user;
	}
}
