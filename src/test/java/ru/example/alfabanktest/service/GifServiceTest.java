package ru.example.alfabanktest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.example.alfabanktest.dto.GifResponse;
import ru.example.alfabanktest.feign.GifClient;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class GifServiceTest {
	
	@Value("${gif.rich}")
	private String gifRich;
	@Value("${gif.broke}")
	private String gifBroke;
	
	@Value("${gif.api.key}")
	private String gifApiKey;
	@Value("${gif.api.greater}")
	private String greaterString;
	@Value("${gif.api.lower}")
	private String lowerString;
	
	@Autowired
	private GifService gifService;
	
	@MockBean
	private GifClient gifClient;
	
	@Test
	public void getRandomGifTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		GifResponse richGif = mapper.readValue(gifRich, GifResponse.class);
		GifResponse brokeGif = mapper.readValue(gifBroke, GifResponse.class);
		
		Mockito.when( gifClient.getGif( gifApiKey, greaterString ) ).thenReturn( richGif );
		Mockito.when( gifClient.getGif( gifApiKey, lowerString ) ).thenReturn( brokeGif );
		
		Assertions.assertEquals( "https://giphy.com/gifs/rich", gifService.getRandomGif( greaterString ) );
		Assertions.assertEquals( "https://giphy.com/gifs/broke", gifService.getRandomGif( lowerString ) );
	}
}
