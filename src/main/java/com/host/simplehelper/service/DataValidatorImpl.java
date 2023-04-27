package com.host.simplehelper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Rustam Tastimullin (tastimullin.rustam@gmail.com) created on 06.06.2022
 */
@Service
@RequiredArgsConstructor
public class DataValidatorImpl implements DataValidator {

	/**
	 * {@inheritDoc}
	 */
	public String validateAndFormatData(LocalDate dataToValidate) throws IllegalArgumentException {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = dataToValidate.format(dateTimeFormatter);

		// regex для даты: dd/mm/yyyy
		String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";

		if (formattedDate.matches(regex)) {
			return formattedDate;
		} else {
			throw new IllegalArgumentException("Введенный формат даты не корректный.");
		}
	}

}
