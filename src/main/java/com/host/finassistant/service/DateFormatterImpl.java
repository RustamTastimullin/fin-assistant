package com.host.finassistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Сервис форматирования дат.
 *
 * @author Rustam Tastimullin (tastimullin.rustam@gmail.com) created on 06.06.2022
 */
@Service
@RequiredArgsConstructor
public class DateFormatterImpl implements DateFormatter {

	/**
	 * {@inheritDoc}
	 */
	public String formatForParsing(LocalDate dataToValidate) throws IllegalArgumentException {

		var dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		var formattedDate = dataToValidate.format(dtf);

		// regex для даты: dd/mm/yyyy
		var regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";

		if (formattedDate.matches(regex)) {
			return formattedDate;
		} else {
			throw new IllegalArgumentException("Введенный формат даты не корректный.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public LocalDate formatForSave(String date) {
		var dtf = DateTimeFormatter.ofPattern("dd.MM.yyy", Locale.ROOT);
		return LocalDate.parse(date, dtf);
	}

	/**
	 * {@inheritDoc}
	 */
	public String formatForOutputView(LocalDate date) {
		var dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return date.format(dtf);
	}
}
