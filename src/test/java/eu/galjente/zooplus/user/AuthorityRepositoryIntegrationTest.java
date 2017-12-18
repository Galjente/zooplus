package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.Authority;
import eu.galjente.zooplus.user.domain.repository.AuthorityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorityRepositoryIntegrationTest {

	private final static String ROLE_USER = "ROLE_USER";
	private final static String ROLE_TEST = "ROLE_TEST";

	@Autowired private AuthorityRepository authorityRepository;

	@Test
	public void findDefaultRoleForUser() {
		//when
		Authority found = authorityRepository.findOneByName(Authority.ROLE_USER);

		//then
		assertThat(found.getName()).isEqualTo(ROLE_USER);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void trySaveDuplicateAuthority() {
		//given
		Authority authority = new Authority();
		authority.setName(ROLE_USER);

		//when
		authorityRepository.save(authority);
	}

	@Test
	public void saveNewAuthority() {
		//given
		Authority authority = new Authority();
		authority.setName(ROLE_TEST);

		//when
		authorityRepository.save(authority);

		//then
		Authority found = authorityRepository.findOneByName(ROLE_TEST);
		assertThat(authority)
				.isEqualTo(found);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void saveAuthorityWithEmptyName() {
		//given
		Authority authority = new Authority();

		//when
		authorityRepository.save(authority);
	}
}
