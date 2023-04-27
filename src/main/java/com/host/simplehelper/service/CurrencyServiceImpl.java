package com.host.simplehelper.service;

import com.host.simplehelper.domain.Currency;
import com.host.simplehelper.domain.CurrencyDTO;
import com.host.simplehelper.mapper.CurrencyMapper;
import com.host.simplehelper.parser.xml.CurrencyParser;
import com.host.simplehelper.repository.CurrencyRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

	private final DataValidator dataValidator;
	private final CurrencyRepository currencyRepository;
	private final CurrencyParser currencyParser;
	private final CurrencyMapper currencyMapper;

	/**
	 * {@inheritDoc}
	 */
	public @NotNull CurrencyDTO getCurrencyByDate(LocalDate date) throws ParseException {

		var formattedData = dataValidator.validateAndFormatData(date);

		// Проверим что данные за эту дату не пустые
		var currencyOptional = currencyRepository.findById(date);
		if (currencyOptional.isPresent() && validateEntityHasNoNullFields(currencyOptional.get())) {
			// если данные корректны, тогда не нужно парсить данные на дату еще раз
			return currencyMapper.currencyToCurrencyDTO(currencyOptional.get(), formattedData);
		}

		// парсим валюты на заданную дату
		var currencyDTO = currencyParser.parseValutesByDateAndReturnDTO(formattedData);

		var currency = currencyMapper.currencyDTOToCurrency(currencyDTO, date);
		currencyRepository.save(currency);

		return currencyDTO;
	}

	/**
	 * Валидируем данные в сущности (все поля должны быть не пустые).
	 *
	 * @param referenceEntity проверяемая сущность
	 * @return результат проверки полей на null
	 */
	boolean validateEntityHasNoNullFields(Currency referenceEntity) {
		return referenceEntity.getUsd_nominal() != null
				&& referenceEntity.getUsd() != null
				&& referenceEntity.getEur_nominal() != null
				&& referenceEntity.getEur() != null
				&& referenceEntity.getHkd_nominal() != null
				&& referenceEntity.getHkd() != null;
	}

}
