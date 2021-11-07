package ru.example.alfabanktest.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.example.alfabanktest.dto.GifObject;
import ru.example.alfabanktest.feign.GifClient;

/**
 * Класс, отвечающий за выбор конкретного объекта-картинки из набора (массива) картинок, полученного от web-сервиса gif-картинок
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
	 * Метод получения URL объекта-картинки, случайным образом выбранного из массива картинок. Все картинки в массиве отобраны по тегу, соответствующему
	 * строке поиска (https://developers.giphy.com/docs/api/endpoint#search)
	 * @param searchString - строка для поиска соответствия
	 * @return - строка, содержащая URL конкретной картинки
	 */
	public String getRandomGif(String searchString) {
		GifObject[] arrayGifObjects = gifClient.getGif( gifApiKey, searchString ).getData();
		Random rand = new Random();
		int randomInt = rand.nextInt( arrayGifObjects.length );
		return arrayGifObjects[ randomInt ].getUrl();
	}
}
