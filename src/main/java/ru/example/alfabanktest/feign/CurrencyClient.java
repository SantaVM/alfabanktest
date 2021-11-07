package ru.example.alfabanktest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.example.alfabanktest.dto.CurrencyResponse;

/**
 * ���������, ����������� ���������� ���������� Feign ��� ������� � web ������� ������ �����.
 * URL web ������� ���������� � ����� properties �������.
 * @author karta
 *
 */

@FeignClient(name="currency-client", url="${currency.api.url}")
public interface CurrencyClient {
	
	/**
	 * ����� ��� ��������� ������� ������� ������ ����� �� ��������� � ������� ������. ������� ����������� � ������ ������� ���� �� ������ ������.
	 * ������ ���� GET �� ����� ����: "currency_api_url/latest.json?app_id=my_key&base=base_currency"
	 * @param id - ���� ������������ web ������� ������ �����
	 * @param base - ������ ���� ������� ������.
	 * @return - ������ ���� {@link CurrencyResponse}, ���������� ������� � �������.
	 */
	@GetMapping("/latest.json")
	CurrencyResponse getCurrentRate(@RequestParam("app_id") String id, @RequestParam("base") String base);
	
	/**
	 * ����� ��� ��������� ������� ������������ ������ ����� �� ��������� � ������� ������ �� ��������� ����. ������� ����������� � ����� ��������� ���
	 * �� ������ ������. ���� ����� � ��������� ���� �� �������������, �� ������������ ����� �� ��������� ���������� �������� ����.
	 * @param date - ���� � �������: "2021-12-31"
	 * @param id - ���� ������������ web ������� ������ �����
	 * @return - ������ ���� {@link CurrencyResponse}, ���������� ������� � �������.
	 */
	@GetMapping("/historical/{date}.json")
	CurrencyResponse getHistoricalRate(@PathVariable("date") String date, @RequestParam("app_id") String id);

}
