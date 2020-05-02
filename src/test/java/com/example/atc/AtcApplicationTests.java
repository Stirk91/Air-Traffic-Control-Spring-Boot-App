package com.example.atc;

import com.example.atc.FlightGen.PlaneGen;
import com.example.atc.api.PlaneController;
import com.example.atc.dao.DataAccessService;
import com.example.atc.model.Plane;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@WebMvcTest(PlaneController.class)
@ContextConfiguration(classes = {DataAccessService.class}) // only instantiates this class
@SpringBootTest
class AtcApplicationTests {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@MockBean
	private DataAccessService dataAccessService;

	//@Autowired
	//private MockMvc mockMvc;

	private List<Plane> planesDB = new ArrayList<Plane>();
	private PlaneGen planeGen = new PlaneGen();

	@BeforeEach
	public void init() {
		dataAccessService = new DataAccessService(jdbcTemplate);
	}

	@Test
	void testInsertThenSelectAllPlanes() {

		Mockito.when(dataAccessService.insertPlane(planeGen)).thenReturn(1);
		assertThat(dataAccessService.insertPlane(planeGen)).isEqualTo(1);
		planesDB.add(planeGen);
		Mockito.when(dataAccessService.selectAllPlanes()).thenReturn(planesDB);
		assertThat(dataAccessService.selectAllPlanes().get(0)).isEqualTo(planeGen);
	}

	@Test
	void testDeleteThenSelectAllPlanes() {
		planesDB.add(planeGen);

		Mockito.when(dataAccessService.deletePlaneById(planeGen.getId())).thenReturn(1);
		assertThat(dataAccessService.deletePlaneById(planeGen.getId())).isEqualTo(1);
		planesDB.remove(0);
		Mockito.when(dataAccessService.selectAllPlanes()).thenReturn(planesDB);
		assertThat(dataAccessService.selectAllPlanes().size()).isEqualTo(0);
	}

	@Test
	void testUpdateThenSelectAllPlanes() {
		planeGen.setSpeed(0);
		planeGen.setAltitude(0);
		planeGen.setDistance(0);
		planesDB.add(planeGen);

		Mockito.when(dataAccessService.updatePlaneById(planeGen.getId(), planeGen)).thenReturn(1);
		Mockito.when(dataAccessService.selectAllPlanes()).thenReturn(planesDB);
		assertThat(dataAccessService.selectAllPlanes().get(0).getSpeed()).isEqualTo(0);
		assertThat(dataAccessService.selectAllPlanes().get(0).getAltitude()).isEqualTo(0);
		assertThat(dataAccessService.selectAllPlanes().get(0).getDistance()).isEqualTo(0);
	}



/*
	@Test
	public void testPostRequest() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		Object object = new Object() {
			public final String id = "12345";
		};

		String json = objectMapper.writeValueAsString(object);


		mockMvc.perform(
				(RequestBuilder) post("/v1/plane")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

 */
}
