package ru.example.alfabanktest.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.example.alfabanktest.dto.CurrencyResponse;
import ru.example.alfabanktest.dto.GifResponse;
import ru.example.alfabanktest.feign.CurrencyClient;
import ru.example.alfabanktest.feign.GifClient;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class MainControllerIntegrationTest {
	
	@Value("${currency.today}")
	private String today;
	@Value("${currency.before}")
	private String before;
	@Value("${gif.rich}")
	private String gifRich;
	@Value("${gif.broke}")
	private String gifBroke;
	
	@Value("${gif.api.greater}")
	private String greaterString;
	@Value("${gif.api.lower}")
	private String lowerString;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GifClient gifClient;
	@MockBean
	private CurrencyClient currencyClient;
	
	@BeforeEach
	public void setup() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		CurrencyResponse todayCurrency = mapper.readValue(  today , CurrencyResponse.class );
		CurrencyResponse beforeCurrency = mapper.readValue(  before , CurrencyResponse.class );
		
		Mockito.when( currencyClient.getCurrentRate( ArgumentMatchers.any(), ArgumentMatchers.any() ) ).thenReturn( todayCurrency );
		Mockito.when( currencyClient.getHistoricalRate( ArgumentMatchers.any(), ArgumentMatchers.any() ) ).thenReturn( beforeCurrency );
		
		GifResponse richGif = mapper.readValue(gifRich, GifResponse.class);
		GifResponse brokeGif = mapper.readValue(gifBroke, GifResponse.class);
		
		Mockito.when( gifClient.getGif( ArgumentMatchers.any(), ArgumentMatchers.eq( greaterString ) ) ).thenReturn( richGif );
		Mockito.when( gifClient.getGif( ArgumentMatchers.any(), ArgumentMatchers.eq( lowerString ) ) ).thenReturn( brokeGif );
	}
	
	@Test
	public void whenRubThenUnchanged() throws Exception {
		
		mockMvc.perform( MockMvcRequestBuilders.get("/api/RUB") )
		.andExpect( MockMvcResultMatchers.status().isOk() )
		.andExpect( MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andExpect( MockMvcResultMatchers.jsonPath("$.searchString", CoreMatchers.is( "Rates unchanged!" ) ) );
	}
	
	@Test
	public void whenBtcThenBroke() throws Exception {
		
		mockMvc.perform( MockMvcRequestBuilders.get("/api/BTC") )
		.andExpect( MockMvcResultMatchers.status().isOk() )
		.andExpect( MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andExpect( MockMvcResultMatchers.jsonPath("$.searchString", CoreMatchers.is( "broke" ) ) );
	}
	
	@Test
	public void whenEurThenRich() throws Exception {
		
		mockMvc.perform( MockMvcRequestBuilders.get("/api/EUR") )
		.andExpect( MockMvcResultMatchers.status().isOk() )
		.andExpect( MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andExpect( MockMvcResultMatchers.jsonPath("$.searchString", CoreMatchers.is( "rich" ) ) );
	}
}
