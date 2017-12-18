package eu.galjente.zooplus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testFavicon() {
		ResponseEntity<String> entity = restTemplate.getForEntity("/favicon.ico", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isNotEmpty();
	}

	@Test
	public void testSignInCss() {
		ResponseEntity<String> entity = restTemplate.getForEntity("/css/signin.css", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("body");
	}

	@Test
	public void testBootstrapCss() {
		ResponseEntity<String> entity = restTemplate.getForEntity("/webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("bootstrap");
	}

	@Test
	public void testBootstrapJs() {
		ResponseEntity<String> entity = restTemplate.getForEntity("/webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("bootstrap");
	}

	@Test
	public void testJQueryJs() {
		ResponseEntity<String> entity = restTemplate.getForEntity("/webjars/jquery/3.2.1/jquery.min.js", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("jquery");
	}

	@Test
	public void testPopperJs() {
		ResponseEntity<String> entity = restTemplate.getForEntity("/webjars/popper.js/1.11.1/dist/popper.min.js", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("jquery");
	}
}
