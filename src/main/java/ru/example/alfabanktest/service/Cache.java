package ru.example.alfabanktest.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * �������� �� �������� (�����������) ������ �� ������ ������ ����� � �������� ������ ������ � �����������.
 * @author karta
 *
 */

public class Cache {
	protected static Map<LocalDateTime, Map<String, Double>> ratesMap = new HashMap<>();
}
