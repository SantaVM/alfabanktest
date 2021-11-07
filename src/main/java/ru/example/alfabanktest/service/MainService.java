package ru.example.alfabanktest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Основной Service-класс приложения
 * @author karta
 *
 */
@Service
public class MainService {
	
	private CurrencyService currencyService;
	private GifService gifService;
	
	@Autowired
	public MainService( CurrencyService currencyService, GifService gifService ) {
		this.currencyService = currencyService;
		this.gifService = gifService;
	}
	
	@Value("${currency.api.daysBefore}")
	private int daysBefore;
	
	@Value("${gif.api.greater}")
	private String greaterString;
	
	@Value("${gif.api.lower}")
	private String lowerString;
	
	/**
	 * Возвращает число, равное отношению текущего курса заданной к историческому курсу этой валюты относительно контрольной валюты 
	 * (по условиям задания контрольная валюта - RUB, можно изменить в properties).
	 * @param currency - код заданной валюты
	 * @return  1, если курс не изменился; число в пределах 0 < x < 1 если валюта подорожала относительно контрольной; 
	 * число в пределах 1 < x если валюта подешевела относительно контрольной.
	 */
	public Double compareRates(String currency) {
		Double todayRate = currencyService.getRate( currency, 0 );
		Double beforeRate = currencyService.getRate( currency, daysBefore );
		System.out.println( "Today: " + todayRate.toString() + " Before: " + beforeRate.toString() );
		return todayRate / beforeRate;
	}
	
	/**
	 * Возвращает строку URL gif-картинки
	 * @param searchString
	 * @return
	 */
	public String getGifUrl(String searchString) {
		return gifService.getRandomGif( searchString );
	}

}
