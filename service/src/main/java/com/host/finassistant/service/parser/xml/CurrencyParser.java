package com.host.finassistant.service.parser.xml;

import com.host.finassistant.domain.ValCurs;
import com.host.finassistant.entity.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.JAXBException;

import java.net.MalformedURLException;

/**
 * Парсер валют.
 * Парсит данные на заданную дату из XML по адресу
 * <a href="http://www.cbr.ru/development/sxml/">source</a>
 */
public interface CurrencyParser {

	/**
	 * Парсер валют.<br/>
	 * Парсит валюты на указанную дату по схеме.
	 *
	 * @param date дата на которую запрашивается информация о валютах.
	 * @return ДТО с валютами.
	 */
	@NotNull
	ValCurs parseValutesByDateAndReturnValCurs(String date) throws MalformedURLException, JAXBException;

	/**
	 * Создает экземпляр сущности Currency из всего списка валют.
	 *
	 * @param source список валют
	 * @return экземпляр сущности без даты
	 */
	Currency mapCurrencyFromValCurse(ValCurs source);

}
