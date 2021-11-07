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

/**
 * �������� ���������� MVC ����������
 * @author karta
 *
 */
@Controller
public class MainController {
	
	private MainService mainService;
	
	@Value("${gif.api.greater}")
	private String greaterString;
	
	@Value("${gif.api.lower}")
	private String lowerString;
	
	@Autowired
	public MainController( MainService mainService ) {
		this.mainService = mainService;
	}
	
	/**
	 * � ����� �� GET ������ �� ������: http://localhost:8080/api/{code}, ��� {code} ��� ������������� ��� ������ (��������, EUR), 
	 * ���� ������� �� ��������� � ����������� ������ (RUB) �� ���������, ����� ���������
	 * JSON ����� �� ������ ������� {@link StringResponse} ����:
	 * 
	 * 	JSON - StringResponse.json
	 * 		{
	 * 			"searchString": "rich",
	 * 			"url": "https://giphy.com/gifs/Originals-rich-pay-me-count-money-l0HlIvLpzz624GAUM"
	 * 		}
	 * 
	 * ��� ���� "searchString" �������� ���� �� ���� ��������: 
	 * 		- "rich", ���� ����������� ������ ���������� �� ��������� � ���������,
	 * 		- "broke", ���� ����������� ������ ���������� �� ��������� � ��������� ��� 
	 * 		- "Rates unchanged!", ���� ���� �� ���������.
	 * � ���� "url" �������� ������ �� ��������� �������� ��������, ���� ������ "Rates unchanged!", ���� ���� �� �������� ������ �� ���������.
	 * @param currency - ��� ������, �� ������������ � �������� ��������
	 * @return - URL �������� ���� ������ "Rates unchanged!"
	 */
	@ResponseBody
	@GetMapping(value="api/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StringResponse getResponse(@PathVariable("currency") String currency) {
		
		StringResponse response = new StringResponse();
		double compare = mainService.compareRates( currency.toUpperCase() );
		
		if ( compare < 1.0 ) {
			response.setSearchString( lowerString );
			response.setUrl( mainService.getGifUrl( lowerString ) );
		} else if ( compare > 1.0 ) {
			response.setSearchString( greaterString );
			response.setUrl( mainService.getGifUrl( greaterString ) );
		} else {
			response.setSearchString( "Rates unchanged!" );
			response.setUrl( "Rates unchanged!" );
		}
		
		return response;
	}
	
	/**
	 * ��� �������� ����������� �������� ��� GET ������� �� ����� http://localhost:8080 ������������ �� �������� ����� ����������:
	 * http://localhost:8080/api/USD
	 * @return
	 */
	@GetMapping
	public String toMainPage() {
		return "redirect:/api/USD";
	}
	
	/**
	 * ������������ ����������, ����������� � ������ {@link CurrencyService#getRate}, ���� ���������� � ������� ��� ������ ��������
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<String> handleException(IllegalArgumentException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
