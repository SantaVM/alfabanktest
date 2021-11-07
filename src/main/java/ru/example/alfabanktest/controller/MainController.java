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
 * Основной контроллер MVC приложения
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
	 * В ответ на GET запрос по адресу: http://localhost:8080/api/{code}, где {code} это трехбуквенный код валюты (например, EUR), 
	 * курс которой по отношению к контрольной валюте (RUB) мы проверяем, метод формирует
	 * JSON ответ на основе объекта {@link StringResponse} вида:
	 * 
	 * 	JSON - StringResponse.json
	 * 		{
	 * 			"searchString": "rich",
	 * 			"url": "https://giphy.com/gifs/Originals-rich-pay-me-count-money-l0HlIvLpzz624GAUM"
	 * 		}
	 * 
	 * где поле "searchString" содержит одно из трех значений: 
	 * 		- "rich", если контрольная валюта подорожала по отношению к выбранной,
	 * 		- "broke", если контрольная валюта подешевела по отношению к выбранной или 
	 * 		- "Rates unchanged!", если курс не изменился.
	 * а поле "url" содержит ссылку на найденную сервисом картинку, либо строку "Rates unchanged!", если курс за заданный период не изменился.
	 * @param currency - код валюты, не чувствителен к регистру символов
	 * @return - URL картинки либо строку "Rates unchanged!"
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
	 * Для удобства пользования сервисом при GET запросе на адрес http://localhost:8080 переадресует на основной адрес приложения:
	 * http://localhost:8080/api/USD
	 * @return
	 */
	@GetMapping
	public String toMainPage() {
		return "redirect:/api/USD";
	}
	
	/**
	 * Обрабатывает исключение, возникающее в методе {@link CurrencyService#getRate}, если переданный в запросе код валюты неверный
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<String> handleException(IllegalArgumentException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
