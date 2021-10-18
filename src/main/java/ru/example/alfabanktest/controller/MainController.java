package ru.example.alfabanktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.example.alfabanktest.dto.StringResponse;
import ru.example.alfabanktest.service.MainService;

@Controller
public class MainController {
	
	private MainService mainService;
	
	@Value("${currency.api.key}")
	private String currencyApiKey;
	
	@Value("${currency.api.bench}")
	private String benchmarkCurrency;
	
	@Value("${gif.api.key}")
	private String gifApiKey;
	
	@Value("${gif.api.greater}")
	private String greaterString;
	
	@Value("${gif.api.lower}")
	private String lowerString;
	
	@Autowired
	public MainController( MainService mainService ) {
		this.mainService = mainService;
	}
	
	@ResponseBody
	@GetMapping(value="api/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StringResponse getResponse(@PathVariable("currency") String currency) {
		
		StringResponse response = new StringResponse();
		double compare = mainService.compareRates( currency.toUpperCase() );
		
		if ( compare < 1.0 ) {
			response.setSearchString( greaterString );
			response.setUrl( mainService.getGifUrl( greaterString ) );
		} else if ( compare > 1.0 ) {
			response.setSearchString( lowerString );
			response.setUrl( mainService.getGifUrl( lowerString ) );
		} else {
			response.setSearchString( "Rates unchanged!" );
			response.setUrl( "Rates unchanged!" );
		}
		
		return response;
	}
	
	@GetMapping
	public String toMainPage() {
		return "redirect:/api/USD";
	}
	
	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<String> handleException(IllegalArgumentException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
