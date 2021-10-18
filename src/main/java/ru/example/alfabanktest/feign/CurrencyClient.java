package ru.example.alfabanktest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.example.alfabanktest.dto.CurrencyResponse;

@FeignClient(name="currency-client", url="${currency.api.url}")
public interface CurrencyClient {
	
	@GetMapping("/latest.json")
	CurrencyResponse getCurrentRate(@RequestParam("app_id") String id, @RequestParam("base") String base);
	
	@GetMapping("/historical/{date}.json")
	CurrencyResponse getHistoricalRate(@PathVariable("date") String date, @RequestParam("app_id") String id);

}
