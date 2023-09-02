package com.host.finassistant.conditions;

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

	CharAndCodeCurrency(String currencyName, String currencyId) {
		this.currencyName = currencyName;
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public String getCurrencyId() {
		return currencyId;
	}
}