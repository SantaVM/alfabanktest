package ru.example.alfabanktest.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * ������, �������������� ����� json-����� �� web-������� ������ ����� ( https://docs.openexchangerates.org/ ). �������� ����� ����:
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
 * � ������ ������ ������� ��� ���������� ������ 3 ���� (timestamp, base � rates) �� json-������.
 * ��������� ���� �� �� ��-�����������.
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
