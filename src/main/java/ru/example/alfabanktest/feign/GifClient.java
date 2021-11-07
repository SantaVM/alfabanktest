package ru.example.alfabanktest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.example.alfabanktest.dto.GifResponse;

/**
 * ���������, ����������� ���������� ���������� Feign ��� ������� � web  ������� gif-��������.
 * URL web ������� ���������� � ����� properties �������.
 * @author karta
 *
 */
@FeignClient(name="gif-client", url="${gif.api.url}")
public interface GifClient {
	
	/**
	 * ����� ��� ��������� ������ ��������-�������� (�� ��������� - 25 ����) � ������, ���������������� ������������ ������
	 * @param id - ���� ������������ web ������� gif-��������
	 * @param searchString - ������ ��� ������ ������������
	 * @return - ������ ���� {@link GifResponse}
	 */
	@GetMapping()
	GifResponse getGif(@RequestParam("api_key") String id, @RequestParam("q") String searchString);
}
