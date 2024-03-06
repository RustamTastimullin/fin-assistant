package com.host.finassistant.endpoint;

import com.host.finassistant.service.currency.CurrencyService;
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
		model.addAttribute("currencyUSDNominal", currencyDTO.getCurrency().getUsdNominal());
		model.addAttribute("currencyUSD", currencyDTO.getCurrency().getUsdValue());
		model.addAttribute("currencyEURNominal", currencyDTO.getCurrency().getEurNominal());
		model.addAttribute("currencyEUR", currencyDTO.getCurrency().getEurValue());
		model.addAttribute("currencyHKDNominal", currencyDTO.getCurrency().getHkdNominal());
		model.addAttribute("currencyHKD", currencyDTO.getCurrency().getHkdValue());

		return "service/service";
	}
}
