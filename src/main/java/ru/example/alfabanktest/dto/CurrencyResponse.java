package ru.example.alfabanktest.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Объект, представляющий собой json-ответ от web-сервера курсов валют ( https://docs.openexchangerates.org/ ). Получаем ответ вида:
 * 
 * JSON - latest.json
 * {
 *    disclaimer: "https://openexchangerates.org/terms/",
 *    license: "https://openexchangerates.org/license/",
 *    timestamp: 1449877801,
 *    base: "USD",
 *    rates: {
 *        AED: 3.672538,
 *        AFN: 66.809999,
 *        ALL: 125.716501,
 *        AMD: 484.902502,
 *        ANG: 1.788575,
 *        AOA: 135.295998,
 *        ARS: 9.750101,
 *        AUD: 1.390866,
 *        / ... /
 *    }
 * }
 * 
 * В рамках нашего проекта нас интересует только 3 поля (timestamp, base и rates) из json-ответа.
 * Остальные поля мы не де-сериализуем.
 * @author karta
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class CurrencyResponse {
	private Long timestamp;
	private String base;
	private Map<String, Double> rates;
}
