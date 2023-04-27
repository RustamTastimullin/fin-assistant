package com.host.simplehelper.service;

import com.host.simplehelper.domain.CurrencyDTO;
import jakarta.validation.constraints.NotNull;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
public interface CurrencyService {

	/**
	 * Получение курса валют на дату (из БД, или удаленного сервера).
	 *
	 * @param date дата, на которую запрашивается курс валют
	 * @return сущность с курсами валют на запрашиваемую дату
	 * @throws ParseException ошибки парсинга
	 */
	@NotNull
	CurrencyDTO getCurrencyByDate(LocalDate date) throws ParseException;

}
