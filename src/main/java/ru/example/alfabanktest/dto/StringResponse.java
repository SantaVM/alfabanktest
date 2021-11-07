package ru.example.alfabanktest.dto;

import lombok.Data;

/**
 * �����, �������������� ����� json-����� ������ �������.
 * ���� {@link #searchString} ��������� ���� �� ���� ��������� � ������� ��������: "rich" ��� "broke" � ������������ ����� �� ������, �� ������� ��
 * ������ �������� �� web-������� gif-��������.
 * ���� {@link #url} �������� URL ��������� ��������, ��������� �� ���� ������� ��������, ������� ���� �������� �� web-������� gif-��������.
 * @author karta
 *
 */
@Data
public class StringResponse {
	private String searchString;
	private String url;
}
