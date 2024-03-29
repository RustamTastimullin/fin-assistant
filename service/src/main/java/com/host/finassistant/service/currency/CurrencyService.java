package com.host.finassistant.service.currency;

import com.host.finassistant.dto.CurrencyDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.JAXBException;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;

/**
 * Сревис валют.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
public interface CurrencyService {

	/**
	 * Получение курса валют на дату (из БД, или официального ресурса).
	 *
	 * @param date дата, на которую запрашивается курс валют
	 * @return сущность с курсами валют на запрашиваемую дату
	 * @throws ParseException ошибки парсинга
	 */
	@NotNull
	CurrencyDTO getCurrencyByDate(LocalDate date) throws ParseException, MalformedURLException, JAXBException;

}