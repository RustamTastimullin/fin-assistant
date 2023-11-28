package com.host.finassistant;

import com.host.finassistant.dto.Result;
import com.host.finassistant.dto.beststocks.Item;
import com.host.finassistant.dto.beststocks.Response;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FinAssistantApplicationTests {

	@Inject
	private Unmarshaller jaxbUnmarshaller;

	/**
	 * Парсинг slickcharts.com
	 */
	@Test
	public void testSlickCharts() {
		Map<String, CompanyDTO> sp500Prices = getSP500FromSlickCharts();

		// Print the tickers and prices
//		for (String ticker : sp500Prices.keySet()) {
//			System.out.println("Ticker: " + ticker + ", Price: " + sp500Prices.get(ticker));
//		}

	}

	private Map<String, CompanyDTO> getSP500FromSlickCharts() {
		Map<String, CompanyDTO> sp500Map = new HashMap<>();

		try {
			// Fetch the S&P 500 website
			Document doc = Jsoup.connect("https://www.slickcharts.com/sp500").get();

			// Find the table containing the tickers and prices
			Element table = doc.selectFirst(".table-responsive table");

			// Find all rows in the table
			assertNotNull(table);
			Elements rows = table.select("tbody tr");

			// Iterate over each row
			for (Element row : rows) {
				// Extract the ticker and price from the row
				String companyId = row.select("td:nth-child(1)").text();
				String companyName = row.select("td:nth-child(2)").text();
				String companyTicker = row.select("td:nth-child(3)").text();
				String companyWeight = row.select("td:nth-child(4)").text();
				String companyPrice = row.select("td:nth-child(5)").text();

				// Add the ticker and price to the hashmap
				var companyDTO = new CompanyDTO();
				companyDTO.setName(companyName);
				companyDTO.setTicker(companyTicker);
				companyDTO.setPrice(companyPrice);
				companyDTO.setWeight(companyWeight);
				sp500Map.put(companyTicker, companyDTO);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sp500Map;
	}

	/**
	 * АПИ: beststocks.ru
	 */
	@Test
	public void testBeststocksAPI() throws JAXBException, ExecutionException, InterruptedException {
		Map<String, CompanyDTO> sp500FromApi = getSP500FromApi();
		System.out.println(sp500FromApi.get("AAPL"));
	}

	private Map<String, CompanyDTO> getSP500FromApi() throws JAXBException, ExecutionException, InterruptedException {

		// const
		var limitPerPage = 100;
		int totalElements = 9500;

		// vars
		var companies = new HashMap<String, CompanyDTO>();
		var futures = new ArrayList<Future<HashMap<String, CompanyDTO>>>(totalElements / 100);
		var jaxbContext = JAXBContext.newInstance(Response.class);
		this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		// последняя страница 9313
		for (int page = 0; page < totalElements; page += 100) {
			int finalPage = page;
			CompletableFuture<HashMap<String, CompanyDTO>> future = CompletableFuture.supplyAsync(() -> {
				var tmpCompanies = new HashMap<String, CompanyDTO>();
				try {
					System.out.println("Обрабатываем пачку: " + finalPage + " - " + (finalPage + 99));
					// source
					var url = new URL("https://beststocks.ru/api/stocks?mode=full&limit="
							+ limitPerPage + "&offset=" + finalPage);
					var urlConnection = url.openConnection();
					urlConnection.addRequestProperty("Accept", "application/xml");
					var soupDoc = Jsoup.parse(
							urlConnection.getInputStream(), "UTF-8", "", Parser.xmlParser());
					var w3cDoc = new W3CDom().fromJsoup(soupDoc);

					// десериализуем пачку
					var response = (Response) jaxbUnmarshaller.unmarshal(w3cDoc);

					// мапим респонс в ДТО
					for (Item company : response.getData().getItem()) {
						var companyDTO = new CompanyDTO();
						companyDTO.setTicker(company.getTicker());
						companyDTO.setName(company.getName());
						companyDTO.setPrice(company.getPrices().getCurrent().getValue());
						companyDTO.setSector(company.getSector());
						// weight must be taken from slickcharts

						// кладем в результирующую мапу
						tmpCompanies.put(companyDTO.getTicker(), companyDTO);
					}
					System.out.println("Обработка пачки: " + finalPage + " - " + (finalPage + 99) + " завершена");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return tmpCompanies;
			});
			futures.add(future);
		}

		waitAllSkipException(futures, 50000);

		return companies;
	}

	public <T> @NonNull List<Result<T, Throwable>> waitAllSkipException(@NonNull List<Future<T>> futures, long durationMillis) {
		if (futures == null) {
			throw new NullPointerException("futures is marked non-null but is null");
		} else {
			List<Result<T, Throwable>> result = new ArrayList(futures.size());
			long startTimeNanos = System.nanoTime();
			Iterator var7 = futures.iterator();

			while (var7.hasNext()) {
				Future<T> future = (Future) var7.next();
				result.add(this.waitResultSkipException(future, this.remainingTimeMillis(startTimeNanos, durationMillis)));
			}

			return result;
		}
	}

	public <T> Result<T, Throwable> waitResultSkipException(@NonNull Future<T> future, long durationMillis) {
		if (future == null) {
			throw new NullPointerException("future is marked non-null but is null");
		} else {
			try {
				return Result.withResult(future.get(durationMillis, TimeUnit.MILLISECONDS));
			} catch (ExecutionException var6) {
				log.error("Ошибка при выполнении асинхронной операции", var6);
				return var6.getCause() != null ? Result.withErrorResult(var6.getCause()) : Result.withErrorResult(var6);
			} catch (TimeoutException var7) {
				String message = String.format("Результат выполнения асинхронной операции не был получен за %d мс", durationMillis);
				log.error(message, var7);
				return Result.withErrorResult(new RuntimeException(message, var7));
			} catch (InterruptedException var8) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Исполнение потока было прервано", var8);
			}
		}
	}

	private long remainingTimeMillis(long startTimeNanos, long maxDurationMillis) {
		long timePassedNanos = System.nanoTime() - startTimeNanos;
		return maxDurationMillis - timePassedNanos / 1000000L;
	}

}
