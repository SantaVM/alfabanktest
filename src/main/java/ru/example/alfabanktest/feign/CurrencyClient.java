package ru.example.alfabanktest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.example.alfabanktest.dto.CurrencyResponse;

/**
 * Интерфейс, реализующий функционал библиотеки Feign для доступа к web сервису курсов валют.
 * URL web сервиса содержится в файле properties проекта.
 * @author karta
 *
 */

@FeignClient(name="currency-client", url="${currency.api.url}")
public interface CurrencyClient {
	
	/**
	 * Метод для получения таблицы текущих курсов валют по отношению к базовой валюте. Таблица обновляется в начале каждого часа по итогам торгов.
	 * Запрос типа GET на адрес вида: "currency_api_url/latest.json?app_id=my_key&base=base_currency"
	 * @param id - ключ пользователя web сервиса курсов валют
	 * @param base - строка кода базовой валюты.
	 * @return - объект типа {@link CurrencyResponse}, содержащий таблицу с курсами.
	 */
	@GetMapping("/latest.json")
	CurrencyResponse getCurrentRate(@RequestParam("app_id") String id, @RequestParam("base") String base);
	
	/**
	 * Метод для получения таблицы исторических курсов валют по отношению к базовой валюте на указанную дату. Таблица фиксируется в конце торгового дня
	 * по итогам торгов. Если торги в указанный день не производились, то возвращаются курсы за ближайший предыдущий торговый день.
	 * @param date - Дата в формате: "2021-12-31"
	 * @param id - ключ пользователя web сервиса курсов валют
	 * @return - объект типа {@link CurrencyResponse}, содержащий таблицу с курсами.
	 */
	@GetMapping("/historical/{date}.json")
	CurrencyResponse getHistoricalRate(@PathVariable("date") String date, @RequestParam("app_id") String id);

}
