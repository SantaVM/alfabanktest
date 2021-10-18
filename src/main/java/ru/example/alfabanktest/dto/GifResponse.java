package ru.example.alfabanktest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GifResponse {
	private GifObject[] data;
}
