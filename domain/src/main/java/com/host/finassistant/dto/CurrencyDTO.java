package com.host.finassistant.dto;

import com.host.finassistant.entity.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ДТО для передачи в контроллер.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {

	@NotNull
	private String formattedDate;

	@NotNull
	private Currency currency;

}
