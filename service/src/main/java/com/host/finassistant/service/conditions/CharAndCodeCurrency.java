package com.host.finassistant.service.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharAndCodeCurrency {

	USD("USD", "R01235"),
	EUR("EUR", "R01239"),
	GBP("GBP", "R01035"),
	HKD("HKD", "R01200"),
	RUB("RUS", null);

	/**
	 * Наименование валюты
	 */
	private final String currencyName;

	/**
	 * Ид валюты
	 */
	private final String currencyId;

}