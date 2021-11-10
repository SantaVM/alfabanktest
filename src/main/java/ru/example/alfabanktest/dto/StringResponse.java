package ru.example.alfabanktest.dto;

import lombok.Data;

/**
 * Класс, представляющий собой json-ответ нашего сервиса.
 * Поле {@link #searchString} принимает одно из трех значений: "rich", "broke" или "Rates unchanged!".
 * Поле {@link #url} содержит URL случайной картинки, выбранной из того массива картинок, которые были получены от web-сервиса gif-картинок, либо строку
 * "Rates unchanged!", если курсы не изменились.
 * @author karta
 *
 */
@Data
public class StringResponse {
	private String searchString;
	private String url;
}
