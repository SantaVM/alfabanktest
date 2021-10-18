package ru.example.alfabanktest.dto;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.TestPropertySource;

@JsonTest
@TestPropertySource("classpath:application-test.properties")
public class ModelTest {
	
	@Value("${gif.rich}")
	private String gifRich;
	@Value("${currency.today}")
	private String today;
	
	@Autowired
	JacksonTester<CurrencyResponse> currencyResponseTester;
	@Autowired
	JacksonTester<GifResponse> gifResponseTester;
	
	@Test
	public void testCurrencyResponse() throws IOException {
		CurrencyResponse currencyResponse = currencyResponseTester.parse( today ).getObject();
		Assertions.assertEquals( Double.valueOf( 70.0 ), currencyResponse.getRates().get( "RUB" ) );
	}
	
	@Test
	public void testGifResponse() throws IOException {
		GifResponse gifResponse = gifResponseTester.parseObject( gifRich );
		GifObject gifObject = gifResponse.getData()[0];
		Assertions.assertEquals( 2, gifResponse.getData().length );
		Assertions.assertEquals( "https://giphy.com/gifs/rich", gifObject.getUrl() );
	}
}
