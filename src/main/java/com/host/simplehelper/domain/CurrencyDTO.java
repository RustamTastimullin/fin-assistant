package com.host.simplehelper.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {

	@NotNull
	private String formattedDate;

	private Integer usd_nominal;

	private BigDecimal usd;

	private Integer eur_nominal;

	private BigDecimal eur;

	private Integer hkd_nominal;

	private BigDecimal hkd;

}
