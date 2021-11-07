package ru.example.alfabanktest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * ������, �������������� ����� json-����� �� web-������� gif-�������� ( https://developers.giphy.com/docs/api/endpoint#search ).
 * � ������ ������ ������� ��� ���������� ������ ���� ���� (data) �� json-������, ���������� � ���� ������ ��������-�������� (GIF Object).
 * ��������� ���� �� �� ��-�����������.
 * @author karta
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class GifResponse {
	private GifObject[] data;
}
