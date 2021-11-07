package ru.example.alfabanktest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Объект, представляющий собой один объект (GIF Object) из массива в json-ответе от web-сервера gif-картинок ( https://developers.giphy.com/docs/api/schema#gif-object ).
 * В рамках нашего проекта нас интересует только одно поле (url) из json-ответа, содержащее в себе URL.
 * Остальные поля мы не де-сериализуем.
 * @author karta
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GifObject {
	private String url;
}
