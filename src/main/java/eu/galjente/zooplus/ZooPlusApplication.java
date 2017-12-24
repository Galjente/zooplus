package eu.galjente.zooplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EnableCaching
@EntityScan(basePackageClasses = {ZooPlusApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class ZooPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooPlusApplication.class, args);
	}

}
