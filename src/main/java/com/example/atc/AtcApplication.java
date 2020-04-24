package com.example.atc;


import com.example.atc.FlightGen.PlaneGen;
import com.example.atc.dao.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class AtcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AtcApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;



	@Override
	public void run(String... args) throws Exception {



		// clears all rows from plane
		final String sql = "DELETE FROM plane;" ;
		jdbcTemplate.update(sql);

		// insert 100 planes into the database
		DataAccessService dataAccessService = new DataAccessService(jdbcTemplate);

		for (int i = 0; i < 100; i++)
		{
			PlaneGen planeGen = new PlaneGen();
			dataAccessService.insertPlane(planeGen);
		}





	}
}
