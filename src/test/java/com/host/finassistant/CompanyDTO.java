package com.host.simplehelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * ДТО компании - участника рынка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

	private String id;
	private String ticker;
	private String name;
	private String price;
	private String sector;
	private String weight;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CompanyDTO that)) {
			return false;
		}
		return Objects.equals(getTicker(), that.getTicker());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTicker());
	}
}
