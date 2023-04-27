package com.host.simplehelper.parser.xml;

import com.host.simplehelper.domain.CurrencyDTO;
import jakarta.validation.constraints.NotNull;

import java.text.ParseException;

/**
 * Парсер валют.
 * Парсит данные на заданную дату из XML по адресу:
 * <a href="http://www.cbr.ru/scripts/XML_daily.asp?date_req=">http://www.cbr.ru/scripts/XML_daily.asp?date_req=01/01/2022</a>
 */
public interface CurrencyParser {

	/**
	 * Парсер валют.
	 *
	 * @param date дата на которую запрашивается информация о валютах.
	 * @return заполненная ДТО.
	 */
	@NotNull
	CurrencyDTO parseValutesByDateAndReturnDTO(String date) throws IllegalArgumentException, ParseException;

}
