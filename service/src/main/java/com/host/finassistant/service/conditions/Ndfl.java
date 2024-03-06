package com.host.finassistant.service.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum Ndfl {

	NDFL_13(BigDecimal.valueOf(0.13));

	/**
	 * Текущая ставка
	 */
	private final BigDecimal ndflRate;

}
