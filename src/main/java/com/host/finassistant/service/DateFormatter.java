package com.host.finassistant.service;

import java.time.LocalDate;

/**
 * Сервис форматирования дат.
 *
 * @author Rustam Tastimullin (tastimullin.rustam@gmail.com) created on 06.06.2022
 */
public interface DateFormatter {

	/**
	 * Принимает дату и изменяет её под парсер валют в формат ("dd/mm/yyyy").
	 *
	 * @param dataToValidate входящая строка с датой
	 * @return корректная строка с датой для парсера
	 */
	String formatForParsing(LocalDate dataToValidate) throws IllegalArgumentException;

	/**
	 * Принимает строку в виде ("01.01.2023") и форматирует её в формат LocalDate.
	 *
	 * @param date строка с датой
	 * @return LocalDate дата
	 */
	LocalDate formatForSave(String date);

	/**
	 * Принимает дату и форматирует её в строку формата ("01.01.2023).
	 *
	 * @param date дата для форматирования
	 * @return форматированная строка с датой
	 */
	String formatForOutputView(LocalDate date);

}
