package ru.example.alfabanktest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MainServiceTest {
	@Autowired
	private MainService mainService;
	
	@MockBean
	private CurrencyService currencyService;
	@MockBean
	private GifService gifService;
	
	@Test
	public void compareRatesTest() {
		Mockito.when( currencyService.getRate( "RUB", 0 ) ).thenReturn( 70.00 );
		Mockito.when( currencyService.getRate( ArgumentMatchers.any(), ArgumentMatchers.anyInt() ) ).thenReturn( 70.00 );
		
		Assertions.assertEquals(Double.valueOf( 1.0 ), mainService.compareRates( "RUB" ));
	}
}
