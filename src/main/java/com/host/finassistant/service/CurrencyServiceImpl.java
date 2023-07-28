package com.host.finassistant.service;

import com.host.finassistant.domain.dto.CurrencyDTO;
import com.host.finassistant.domain.entity.Currency;
import com.host.finassistant.parser.xml.CurrencyParser;
import com.host.finassistant.repository.CurrencyRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDate;

/**
 * Сервис валют.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

	private final DateFormatter dateFormatter;
	private final CurrencyRepository currencyRepository;
	private final CurrencyParser currencyParser;

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	public CurrencyDTO getCurrencyByDate(LocalDate date) throws MalformedURLException, JAXBException {

		// Проверим что данные за эту дату не пустые
		var currencyOptional = currencyRepository.findById(date);
		if (currencyOptional.isPresent() && validateEntityHasNoNullFields(currencyOptional.get())) {
			// если данные корректны, тогда не нужно парсить данные на дату еще раз
			var dateForView = dateFormatter.formatForOutputView(currencyOptional.get().getDate());
			return new CurrencyDTO(dateForView, currencyOptional.get());
		}

		// парсим валюты на заданную дату
		var formattedDateForParsing = dateFormatter.formatForParsing(date);
		var valCurs = currencyParser.parseValutesByDateAndReturnValCurs(formattedDateForParsing);
		// создаем экземпляр сущности и мапим в него полученные данные
		var currency = currencyParser.mapCurrencyFromValCurse(valCurs);
		// залогировать если в сущности не хватает полей

		return new CurrencyDTO(valCurs.getDate(), currencyRepository.save(currency));
	}

	/**
	 * Валидируем данные в сущности (все поля должны быть не пустые).
	 *
	 * @param entity проверяемая сущность
	 * @return результат проверки полей на null
	 */
	private boolean validateEntityHasNoNullFields(Currency entity) {
		return entity.getUsd_nominal() != null
				&& entity.getUsd() != null
				&& entity.getEur_nominal() != null
				&& entity.getEur() != null
				&& entity.getHkd_nominal() != null
				&& entity.getHkd() != null;
	}

}
