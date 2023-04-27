package com.host.simplehelper.service;

import java.time.LocalDate;

/**
 * @author Rustam Tastimullin (tastimullin.rustam@gmail.com) created on 06.06.2022
 */
public interface DataValidator {

	/**
	 * Принимает дату и изменяет её под парсер валют
	 *
	 * @param dataToValidate входящая строка с датой
	 * @return корректная строка с датой для парсера
	 */
	String validateAndFormatData(LocalDate dataToValidate) throws IllegalArgumentException;

}
