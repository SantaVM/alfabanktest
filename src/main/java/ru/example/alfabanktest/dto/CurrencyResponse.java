package ru.example.alfabanktest.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class CurrencyResponse {
	private Long timestamp;
	private String base;
	private Map<String, Double> rates;
}
