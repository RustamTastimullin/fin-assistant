package com.host.finassistant.service.parser.xml;

import com.host.finassistant.domain.ValCurs;
import com.host.finassistant.entity.Currency;
import com.host.finassistant.service.conditions.CharAndCodeCurrency;
import com.host.finassistant.service.date.DateFormatter;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.NonNull;
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
		// если запускать с дефолтом, то будет зависеть от системы, поэтому FRANCE (замена запятой)
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

		for (ValCurs.Valute valute : source.getValute()) {
			Double value;
			try {
				if (valute.getID().equals(CharAndCodeCurrency.USD.getCurrencyId())) {
					currency.setUsdNominal(valute.getNominal());
					value = format.parse(valute.getValue()).doubleValue();
					currency.setUsdValue(createBigDecimal(value));
				}
				if (valute.getID().equals(CharAndCodeCurrency.EUR.getCurrencyId())) {
					currency.setEurNominal(valute.getNominal());
					value = format.parse(valute.getValue()).doubleValue();
					currency.setEurValue(createBigDecimal(value));
				}
				if (valute.getID().equals(CharAndCodeCurrency.HKD.getCurrencyId())) {
					currency.setHkdNominal(valute.getNominal());
					value = format.parse(valute.getValue()).doubleValue();
					currency.setHkdValue(createBigDecimal(value));
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return currency;
	}

	/**
	 * Создает BigDecimal из передаваемого значения с параметрами округления.
	 *
	 * @param value значение
	 * @return BigDecimal
	 */
	@NonNull
	private BigDecimal createBigDecimal(double value) {
		return BigDecimal.valueOf(value).setScale(2, HALF_UP);
	}
}