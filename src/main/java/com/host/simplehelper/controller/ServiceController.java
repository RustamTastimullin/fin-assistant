package com.host.simplehelper.controller;

import com.host.simplehelper.service.CurrencyService;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;

/**
 * Контроллер сервиса валют.
 *
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
	public String servicePost(Model model, @RequestParam LocalDate data)
			throws ParseException, MalformedURLException, JAXBException {

		var currencyDTO = currencyService.getCurrencyByDate(data);

		model.addAttribute("currencyData", currencyDTO.getFormattedDate());
		model.addAttribute("currencyUSDNominal", currencyDTO.getCurrency().getUsd_nominal());
		model.addAttribute("currencyUSD", currencyDTO.getCurrency().getUsd());
		model.addAttribute("currencyEURNominal", currencyDTO.getCurrency().getEur_nominal());
		model.addAttribute("currencyEUR", currencyDTO.getCurrency().getEur());
		model.addAttribute("currencyHKDNominal", currencyDTO.getCurrency().getHkd_nominal());
		model.addAttribute("currencyHKD", currencyDTO.getCurrency().getHkd());

		return "service/service";
	}
}
