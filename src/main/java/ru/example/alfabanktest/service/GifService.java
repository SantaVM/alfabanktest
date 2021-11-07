package ru.example.alfabanktest.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.example.alfabanktest.dto.GifObject;
import ru.example.alfabanktest.feign.GifClient;

/**
 * �����, ���������� �� ����� ����������� �������-�������� �� ������ (�������) ��������, ����������� �� web-������� gif-��������
 * @author karta
 *
 */
@Service
public class GifService {
	
	private GifClient gifClient;

	@Autowired
	public GifService( GifClient gifClient ) {
		this.gifClient = gifClient;
	}
	
	@Value("${gif.api.key}")
	private String gifApiKey;
	
	/**
	 * ����� ��������� URL �������-��������, ��������� ������� ���������� �� ������� ��������. ��� �������� � ������� �������� �� ����, ����������������
	 * ������ ������ (https://developers.giphy.com/docs/api/endpoint#search)
	 * @param searchString - ������ ��� ������ ������������
	 * @return - ������, ���������� URL ���������� ��������
	 */
	public String getRandomGif(String searchString) {
		GifObject[] arrayGifObjects = gifClient.getGif( gifApiKey, searchString ).getData();
		Random rand = new Random();
		int randomInt = rand.nextInt( arrayGifObjects.length );
		return arrayGifObjects[ randomInt ].getUrl();
	}
}
