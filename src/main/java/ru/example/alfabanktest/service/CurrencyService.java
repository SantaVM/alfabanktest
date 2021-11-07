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
 * �����, ���������� �� ���������� ����� ��������� ������������� ������ � ����������� ������ (�� �������� ������� ����������� ������ - RUB, 
 * ����� �������� � properties).
 * ����� �� ��������� web ������ ����� ������ �������� ������ ����������� (����������) � �������� ������ ����������� ����������:
 * - ������������ ������ �� �������� � ������� ����� � ����������� �� ����� ������� �����;
 * - ������� � �������� ������� ����� �������� ������ ��� �, ��������������, ���������� � ������� �������� ����, ����� ���� ����� ������ ������ �����
 *   ����� ��������� �� web ������.
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
	 * ����� ��� ���������� ����� ������
	 * @param currency - ������-������ ��� ��������� ������������� ������ (��������: "EUR", "BTC", "USD"), ������ � ������� ��������.
	 * @param daysBefore - ����� ���� ����� ��� ��������� ������������ ������ (��� ��������� �������� ����� ����� �������� 0)
	 * @return - ���� ��������� ������ �� ��������� � �����������
	 * @throws - IllegalArgumentException ���� ������-������ ��������� ������ ��� ����������� ������ �� ������������� �� ����� ������ �� �������, 
	 * ���������� �� web-�������
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
