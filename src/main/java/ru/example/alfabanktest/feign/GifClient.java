package ru.example.alfabanktest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.example.alfabanktest.dto.GifResponse;

/**
 * Интерфейс, реализующий функционал библиотеки Feign для доступа к web  сервису gif-картинок.
 * URL web сервиса содержится в файле properties проекта.
 * @author karta
 *
 */
@FeignClient(name="gif-client", url="${gif.api.url}")
public interface GifClient {
	
	/**
	 * Метод для получения набора объектов-картинок (по умолчанию - 25 штук) с тегами, соответствующими определенной строке
	 * @param id - ключ пользователя web сервиса gif-картинок
	 * @param searchString - строка для поиска соответствия
	 * @return - объект типа {@link GifResponse}
	 */
	@GetMapping()
	GifResponse getGif(@RequestParam("api_key") String id, @RequestParam("q") String searchString);
}
