package ru.example.alfabanktest.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.example.alfabanktest.feign.CurrencyClient;

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
	
	public Double getRate(String currency, int daysBefore) {
		
		LocalDate date = LocalDate.now().minusDays( daysBefore );
		
		if( daysBefore == 0) {
			Cache.ratesMap.putIfAbsent( date, currencyClient.getCurrentRate( currencyApiKey , base ).getRates() );
		} else {
			Cache.ratesMap.putIfAbsent( date, currencyClient.getHistoricalRate( date.toString(), currencyApiKey ).getRates() );
		}
				
		Map <String, Double> rates = Cache.ratesMap.get( date );
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
