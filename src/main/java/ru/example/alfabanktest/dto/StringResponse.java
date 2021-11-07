package ru.example.alfabanktest.dto;

import lombok.Data;

/**
 * Класс, представляющий собой json-ответ нашего сервиса.
 * Поле {@link #searchString} принимает одно из двух указанных в задании значений: "rich" или "broke" и представляет собой ту строку, по которой мы
 * искали картинки на web-сервисе gif-картинок.
 * Поле {@link #url} содержит URL случайной картинки, выбранной из того массива картинок, которые были получены от web-сервиса gif-картинок.
 * @author karta
 *
 */
@Data
public class StringResponse {
	private String searchString;
	private String url;
}
