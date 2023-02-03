package fr.pokerplanning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Slf4j
public class PokerplanningApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		log.debug("Lancement pokerplanning main");
		SpringApplication.run(PokerplanningApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		log.debug("Lancement pokerplanning servlet");
		return application.sources(PokerplanningApplication.class);
	}

}
