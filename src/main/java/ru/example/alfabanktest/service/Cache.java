package ru.example.alfabanktest.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Cache {
	protected static Map<LocalDate, Map<String, Double>> ratesMap = new HashMap<>();
}
