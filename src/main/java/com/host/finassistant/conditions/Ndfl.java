package com.host.finassistant.conditions;

import java.math.BigDecimal;

public enum Ndfl {

	ndfl13(BigDecimal.valueOf(0.13));

	/**
	 * Текущая ставка
	 */
	private final BigDecimal ndflRate;

	Ndfl(BigDecimal ndflRate) {
		this.ndflRate = ndflRate;
	}

	public BigDecimal getNdflRate() {
		return ndflRate;
	}

}
