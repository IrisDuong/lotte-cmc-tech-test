package com.cmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cmc.util.service.AuditingImpl;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class LotteCmcTechTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteCmcTechTestApplication.class, args);
	}
	
	@Bean
	public AuditorAware<String> auditorAware(){
		return new AuditingImpl();
	}
}
