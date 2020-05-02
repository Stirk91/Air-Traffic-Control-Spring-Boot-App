package com.example.atc;


import com.example.atc.service.AtcControlService;
import com.example.atc.service.AtcInitializationService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
@EnableEncryptableProperties
@SpringBootApplication
public class AtcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AtcApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;


	@Override
	public void run(String... args) throws Exception {

		int timeMultiplier = 1; // 1 = 60 secs; 2 = 30 secs

		AtcInitializationService atcInitializationService = new AtcInitializationService(jdbcTemplate);
		AtcControlService atcControl = new AtcControlService(jdbcTemplate, timeMultiplier);

		atcInitializationService.run();


		while (true) {
			atcControl.run();
			Thread.sleep(1000);
		}


	}
}
