package com.host.finassistant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Сущность хранит отношение курса единицы валюты к одному рублю на дату.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 26.04.2023.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency")
public class Currency {

	@Id
	@NotNull
	@Column(name = "date", nullable = false, unique = true)
	private LocalDate date;

	@Column(name = "usd_nominal")
	private Long usdNominal;

	@Column(name = "usd")
	private BigDecimal usdValue;

	@Column(name = "eur_nominal")
	private Long eurNominal;

	@Column(name = "eur")
	private BigDecimal eurValue;

	@Column(name = "hkd_nominal")
	private Long hkdNominal;

	@Column(name = "hkd")
	private BigDecimal hkdValue;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Currency currency)) {
			return false;
		}
		return getDate().equals(currency.getDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDate());
	}
}
