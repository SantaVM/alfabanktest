package ru.example.alfabanktest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.example.alfabanktest.dto.CurrencyResponse;

/**
 * »нтерфейс, реализующий функционал библиотеки Feign дл€ доступа к web сервису курсов валют.
 * URL web сервиса содержитс€ в файле properties проекта.
 * @author karta
 *
 */

@FeignClient(name="currency-client", url="${currency.api.url}")
public interface CurrencyClient {
	
	/**
	 * ћетод дл€ получени€ таблицы текущих курсов валют по отношению к базовой валюте. “аблица обновл€етс€ в начале каждого часа по итогам торгов.
	 * «апрос типа GET на адрес вида: "currency_api_url/latest.json?app_id=my_key&base=base_currency"
	 * @param id - ключ пользовател€ web сервиса курсов валют
	 * @param base - строка кода базовой валюты.
	 * @return - объект типа {@link CurrencyResponse}, содержащий таблицу с курсами.
	 */
	@GetMapping("/latest.json")
	CurrencyResponse getCurrentRate(@RequestParam("app_id") String id, @RequestParam("base") String base);
	
	/**
	 * ћетод дл€ получени€ таблицы исторических курсов валют по отношению к базовой валюте на указанную дату. “аблица фиксируетс€ в конце торгового дн€
	 * по итогам торгов. ≈сли торги в указанный день не производились, то возвращаютс€ курсы за ближайший предыдущий торговый день.
	 * @param date - ƒата в формате: "2021-12-31"
	 * @param id - ключ пользовател€ web сервиса курсов валют
	 * @return - объект типа {@link CurrencyResponse}, содержащий таблицу с курсами.
	 */
	@GetMapping("/historical/{date}.json")
	CurrencyResponse getHistoricalRate(@PathVariable("date") String date, @RequestParam("app_id") String id);

}
