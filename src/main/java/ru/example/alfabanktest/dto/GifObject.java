package ru.example.alfabanktest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * ������, �������������� ����� ���� ������ (GIF Object) �� ������� � json-������ �� web-������� gif-�������� ( https://developers.giphy.com/docs/api/schema#gif-object ).
 * � ������ ������ ������� ��� ���������� ������ ���� ���� (url) �� json-������, ���������� � ���� URL.
 * ��������� ���� �� �� ��-�����������.
 * @author karta
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GifObject {
	private String url;
}
