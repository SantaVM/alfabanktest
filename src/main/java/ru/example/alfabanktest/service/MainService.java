package ru.example.alfabanktest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * �������� Service-����� ����������
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
	 * ���������� �����, ������ ��������� �������� ����� �������� � ������������� ����� ���� ������ ������������ ����������� ������ 
	 * (�� �������� ������� ����������� ������ - RUB, ����� �������� � properties).
	 * @param currency - ��� �������� ������
	 * @return  1, ���� ���� �� ���������; ����� � �������� 0 < x < 1 ���� ������ ���������� ������������ �����������; 
	 * ����� � �������� 1 < x ���� ������ ���������� ������������ �����������.
	 */
	public Double compareRates(String currency) {
		Double todayRate = currencyService.getRate( currency, 0 );
		Double beforeRate = currencyService.getRate( currency, daysBefore );
		System.out.println( "Today: " + todayRate.toString() + " Before: " + beforeRate.toString() );
		return todayRate / beforeRate;
	}
	
	/**
	 * ���������� ������ URL gif-��������
	 * @param searchString
	 * @return
	 */
	public String getGifUrl(String searchString) {
		return gifService.getRandomGif( searchString );
	}

}
