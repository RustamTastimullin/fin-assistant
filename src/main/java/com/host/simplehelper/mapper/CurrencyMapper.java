package com.host.simplehelper.mapper;

import com.host.simplehelper.domain.Currency;
import com.host.simplehelper.domain.CurrencyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

	@Mappings({
			@Mapping(target = "date", source = "date"),
			@Mapping(target = "usd_nominal", source = "source.usd_nominal"),
			@Mapping(target = "usd", source = "source.usd"),
			@Mapping(target = "eur_nominal", source = "source.eur_nominal"),
			@Mapping(target = "eur", source = "source.eur"),
			@Mapping(target = "hkd_nominal", source = "source.hkd_nominal"),
			@Mapping(target = "hkd", source = "source.hkd")
	})
	Currency currencyDTOToCurrency(CurrencyDTO source, LocalDate date);

	@Mappings({
			@Mapping(target = "formattedDate", source = "date"),
			@Mapping(target = "usd_nominal", source = "source.usd_nominal"),
			@Mapping(target = "usd", source = "source.usd"),
			@Mapping(target = "eur_nominal", source = "source.eur_nominal"),
			@Mapping(target = "eur", source = "source.eur"),
			@Mapping(target = "hkd_nominal", source = "source.hkd_nominal"),
			@Mapping(target = "hkd", source = "source.hkd")
	})
	CurrencyDTO currencyToCurrencyDTO(Currency source, String date);


}
