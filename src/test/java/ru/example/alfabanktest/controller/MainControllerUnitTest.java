package ru.example.alfabanktest.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ru.example.alfabanktest.service.MainService;

@WebMvcTest( MainController.class )
public class MainControllerUnitTest {
	@MockBean
	private MainService mainService;
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getResponseTest() throws Exception {
		Mockito.when( mainService.compareRates( ArgumentMatchers.any() ) ).thenReturn( 1.0 );
		
		mockMvc.perform( MockMvcRequestBuilders.get("/api/RUB") )
			.andExpect( MockMvcResultMatchers.status().isOk())
			.andExpect( MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect( MockMvcResultMatchers.jsonPath("$.searchString", CoreMatchers.is( "Rates unchanged!" ) ) );
	}
	
	@Test
	public void toMainPageTest() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders.get("/") )
			.andExpect( MockMvcResultMatchers.redirectedUrl( "/api/USD" ) );
	}

}
