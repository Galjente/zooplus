package eu.galjente.zooplus.user;

import eu.galjente.zooplus.user.domain.entity.Authority;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorityUnitTest {

	private final static Integer AUTHORITY_ID = 12;
	private final static String AUTHORITY_NAME = "Random name";

	@Test
	public void shouldBeEquals() {
		Authority authority1 = createDefaultAuthority();
		Authority authority2 = createDefaultAuthority();

		assertThat(authority1.getId()).isEqualTo(authority2.getId());
		assertThat(authority1.getName()).isEqualTo(authority2.getName());
		assertThat(authority1.hashCode()).isEqualTo(authority2.hashCode());
		assertThat(authority1).isEqualTo(authority2);
	}

	@Test
	public void shouldNotBeEquals() {
		Authority authority1 = createDefaultAuthority();
		Authority authority2 = new Authority();

		authority2.setId(1);
		authority2.setName("321");


		assertThat(authority1.getId()).isNotEqualTo(authority2.getId());
		assertThat(authority1.getName()).isNotEqualTo(authority2.getName());
		assertThat(authority1.hashCode()).isNotEqualTo(authority2.hashCode());
		assertThat(authority1).isNotEqualTo(authority2);
	}

	private Authority createDefaultAuthority() {
		Authority authority = new Authority();

		authority.setId(AUTHORITY_ID);
		authority.setName(AUTHORITY_NAME);

		return authority;
	}
}
