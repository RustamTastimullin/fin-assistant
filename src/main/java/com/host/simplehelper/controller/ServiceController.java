package com.host.simplehelper.controller;

import com.host.simplehelper.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Controller
@RequestMapping("/service")
@EnableMethodSecurity
@RequiredArgsConstructor
public class ServiceController {

	private final CurrencyService currencyService;

	@GetMapping
	public String service() {
		return "service/service";
	}

	@PostMapping
	@Transactional
	public String servicePost(Model model, @RequestParam LocalDate data) throws ParseException {

		var currencyByDateDTO = currencyService.getCurrencyByDate(data);

		model.addAttribute("currencyData", currencyByDateDTO.getFormattedDate());
		model.addAttribute("currencyUSDNominal", currencyByDateDTO.getUsd_nominal());
		model.addAttribute("currencyUSD", currencyByDateDTO.getUsd());
		model.addAttribute("currencyEURNominal", currencyByDateDTO.getEur_nominal());
		model.addAttribute("currencyEUR", currencyByDateDTO.getEur());
		model.addAttribute("currencyHKDNominal", currencyByDateDTO.getHkd_nominal());
		model.addAttribute("currencyHKD", currencyByDateDTO.getHkd());

		return "service/service";
	}
}
