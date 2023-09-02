package com.host.finassistant.parser.xml;

import com.host.finassistant.domain.entity.Currency;
import com.host.finassistant.domain.ValCurs;
import com.host.finassistant.service.DateFormatter;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.host.finassistant.conditions.CharAndCodeCurrency.EUR;
import static com.host.finassistant.conditions.CharAndCodeCurrency.HKD;
import static com.host.finassistant.conditions.CharAndCodeCurrency.USD;
import static java.math.RoundingMode.HALF_UP;

/**
 * {@inheritDoc}
 */
@Service
@RequiredArgsConstructor
public class CurrencyParserImpl implements CurrencyParser {

	private final DateFormatter dateFormatter;
	private Unmarshaller jaxbUnmarshaller;
	private NumberFormat format;

	@PostConstruct
	private void init() throws SAXException, JAXBException {
		var jaxbContext = JAXBContext.newInstance(ValCurs.class);
		var schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		var schema = schemaFactory.newSchema(
				this.getClass().getResource("/schema/xsd/ValCurs.xsd")
		);
		this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		this.jaxbUnmarshaller.setSchema(schema);
		// если запускать с дефолтом, то будет зависеть от системы, поэтому  FRANCE (замена запятой)
		this.format = NumberFormat.getInstance(Locale.FRANCE);
	}

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	public ValCurs parseValutesByDateAndReturnValCurs(String formattedDate)
			throws MalformedURLException, JAXBException {

		var url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + formattedDate);

		return (ValCurs) jaxbUnmarshaller.unmarshal(url);
	}

	/**
	 * {@inheritDoc}
	 */
	public Currency mapCurrencyFromValCurse(ValCurs source) {

		var currency = new Currency();

		var formatForSave = dateFormatter.formatForSave(source.getDate());
		currency.setDate(formatForSave);

		Double value;
		for (ValCurs.Valute valute : source.getValutes()) {
			try {
				if (valute.getID().equals(USD.getCurrencyId())) {
					currency.setUsd_nominal(valute.getNominal());
					value = format.parse(valute.getValue()).doubleValue();
					currency.setUsd(BigDecimal.valueOf(value).setScale(2, HALF_UP));
				}
				if (valute.getID().equals(EUR.getCurrencyId())) {
					currency.setEur_nominal(valute.getNominal());
					value = format.parse(valute.getValue()).doubleValue();
					currency.setEur(BigDecimal.valueOf(value).setScale(2, HALF_UP));
				}
				if (valute.getID().equals(HKD.getCurrencyId())) {
					currency.setHkd_nominal(valute.getNominal());
					value = format.parse(valute.getValue()).doubleValue();
					currency.setHkd(BigDecimal.valueOf(value).setScale(2, HALF_UP));
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return currency;
	}
}