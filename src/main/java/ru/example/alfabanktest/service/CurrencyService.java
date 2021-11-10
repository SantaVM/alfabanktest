package ru.example.alfabanktest.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.example.alfabanktest.feign.CurrencyClient;

/**
 * Класс, отвечающий за вычисление курса выбранной пользователем валюты к контрольной валюте (по условиям задания контрольная валюта - RUB, 
 * можно изменить в properties).
 * Чтобы не нагружать web сервис валют данные валютных таблиц сохраняются (кэшируются) в пределах сессии запущенного приложения:
 * - исторические данные не меняются в течение суток и сохраняются до конца текущих суток;
 * - таблицы с текущими курсами валют меняются каждый час и, соответственно, кэшируются в течение текущего часа, после чего новый запрос курсов валют
 *   будет отправлен на web сервис.
 * @author karta
 *
 */

@Service
public class CurrencyService {
	
	private CurrencyClient currencyClient;
	
	@Autowired
	public CurrencyService( CurrencyClient currencyClient ) {
		this.currencyClient = currencyClient;
	}
	
	@Value("${currency.api.key}")
	private String currencyApiKey;
	
	@Value("${currency.api.base}")
	private String base;
	
	@Value("${currency.api.bench}")
	private String benchmarkCurrency;
	
	/**
	 * Метод для вычисления курса валюты
	 * @param currency - Строка-символ для выбранной пользователем валюты (например: "EUR", "BTC", "USD"), только в верхнем регистре.
	 * @param daysBefore - Число дней назад для получения исторических курсов (для получения текущего курса нужно передать 0)
	 * @return - курс выбранной валюты по отношению к контрольной
	 * @throws - IllegalArgumentException если строка-символ выбранной валюты или контрольной валюты не соответствует ни одной валюте из таблицы, 
	 * полученной от web-сервиса
	 */
	public Double getRate(String currency, int daysBefore) {
		
		LocalDateTime date = LocalDateTime.now().minusDays( daysBefore );
		
		Map <String, Double> rates = new HashMap<>();
		
		if( daysBefore == 0) {
			LocalDateTime thisHour = date.withMinute( 0 ).withSecond( 0 ).withNano( 0 );
			Cache.ratesMap.putIfAbsent( thisHour, currencyClient.getCurrentRate( currencyApiKey , base ).getRates() );
			
			rates = Cache.ratesMap.get( thisHour );
		} else {
			LocalDateTime beforeDay = date.withHour( 23 ).withMinute( 59 ).withSecond( 0 ).withNano( 0 );
			Cache.ratesMap.putIfAbsent( beforeDay, currencyClient.getHistoricalRate( date.toLocalDate().toString(), currencyApiKey ).getRates() );
			
			rates = Cache.ratesMap.get( beforeDay );
		}
		
		if ( !rates.containsKey( currency ) || !rates.containsKey( benchmarkCurrency.toUpperCase() ) ) {
			throw new IllegalArgumentException( "There is no such currency: " + currency + " or " + benchmarkCurrency.toUpperCase() );
		}
		
		Double currencyRate = rates.get( currency );
		Double benchmarkRate = rates.get( benchmarkCurrency.toUpperCase() );
		
		System.out.println( currency +" "+ currencyRate.toString() +" | "+ benchmarkCurrency +" "+ benchmarkRate.toString() );
		
		BigDecimal first = BigDecimal.valueOf( currencyRate );
		BigDecimal second = BigDecimal.valueOf( benchmarkRate );
		BigDecimal result = first.divide(second, 12, RoundingMode.DOWN);
		return result.doubleValue();
	}
}
