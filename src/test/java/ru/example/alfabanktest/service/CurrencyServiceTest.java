package ru.example.alfabanktest.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.example.alfabanktest.dto.CurrencyResponse;
import ru.example.alfabanktest.feign.CurrencyClient;
import ru.example.alfabanktest.feign.GifClient;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CurrencyServiceTest {
	
	@Value("${currency.today}")
	private String today;
	@Value("${currency.before}")
	private String before;
	
	@Value("${currency.api.key}")
	private String currencyApiKey;
	@Value("${currency.api.base}")
	private String base;
	@Value("${currency.api.daysBefore}")
	private int daysBefore;

	@MockBean
	private GifClient gifClient;
	@MockBean
	private CurrencyClient currencyClient;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Test
	public void getRate_shouldWork() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		CurrencyResponse todayCurrency = mapper.readValue(  today , CurrencyResponse.class );
		CurrencyResponse beforeCurrency = mapper.readValue(  before , CurrencyResponse.class );
		
		LocalDate date = LocalDate.now().minusDays( daysBefore );
		
		Mockito.when( currencyClient.getCurrentRate( currencyApiKey, base ) ).thenReturn( todayCurrency );
		Mockito.when( currencyClient.getHistoricalRate( date.toString(), currencyApiKey ) ).thenReturn( beforeCurrency );
		
		Assertions.assertEquals(Double.valueOf( 1.0 ), currencyService.getRate( "RUB", 0 ));
		Assertions.assertEquals(Double.valueOf( 0.011 ), currencyService.getRate( "EUR", 0 ));
		Assertions.assertEquals(Double.valueOf( 0.005 ), currencyService.getRate( "BTC", 0 ));
		Assertions.assertEquals(Double.valueOf( 1 ), currencyService.getRate( "RUB", daysBefore ));
		Assertions.assertEquals(Double.valueOf( 0.010 ), currencyService.getRate( "EUR", daysBefore ));
		Assertions.assertEquals(Double.valueOf( 0.006 ), currencyService.getRate( "BTC", daysBefore ));
	}

}
