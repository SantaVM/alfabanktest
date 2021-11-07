package ru.example.alfabanktest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Объект, представляющий собой json-ответ от web-сервера gif-картинок ( https://developers.giphy.com/docs/api/endpoint#search ).
 * В рамках нашего проекта нас интересует только одно поле (data) из json-ответа, содержащее в себе массив объектов-картинок (GIF Object).
 * Остальные поля мы не де-сериализуем.
 * @author karta
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GifResponse {
	private GifObject[] data;
}
