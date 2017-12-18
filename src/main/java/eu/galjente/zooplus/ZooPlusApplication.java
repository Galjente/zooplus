package eu.galjente.zooplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = {ZooPlusApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class ZooPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooPlusApplication.class, args);
	}

}
