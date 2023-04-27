package com.host.simplehelper.parser.xml;

import com.host.simplehelper.domain.CurrencyDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.host.simplehelper.conditions.CharAndCodeCurrency.EUR;
import static com.host.simplehelper.conditions.CharAndCodeCurrency.HKD;
import static com.host.simplehelper.conditions.CharAndCodeCurrency.USD;
import static java.math.RoundingMode.HALF_UP;

/**
 * Парсер валют.
 * Парсит данные на заданную дату из XML по адресу:
 * <a href="http://www.cbr.ru/scripts/XML_daily.asp?date_req=">http://www.cbr.ru/scripts/XML_daily.asp?date_req=01/01/2022</a>
 */
@Service
@RequiredArgsConstructor
public class CurrencyParserImpl implements CurrencyParser {

	/**
	 * {@inheritDoc}
	 */
	public @NotNull CurrencyDTO parseValutesByDateAndReturnDTO(String date) throws IllegalArgumentException, ParseException {

		Double value;
		int nominal;
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		var parsedValues = new CurrencyDTO();
		parsedValues.setFormattedDate(date);

		// пример: http://www.cbr.ru/scripts/XML_daily.asp?date_req=01/01/2023
		var source = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date;

		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// optional, but recommended
			// process XML securely, avoid attacks like XML External Entities (XXE)
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(source);

			// optional, but recommended
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// get nodes
			NodeList list = doc.getElementsByTagName("Valute");

			for (int i = 0; i < list.getLength(); i++) {

				Node node = list.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					// get node's attribute
					// example: Valute ID="R01235" for USD
					String id = element.getAttribute("ID");

					if (id.equals(USD.getCurrencyId())) {
						value = format
								.parse(element.getElementsByTagName("Value").item(0).getTextContent())
								.doubleValue();
						parsedValues.setUsd(BigDecimal.valueOf(value).setScale(2, HALF_UP));
						nominal = format.parse(element.getElementsByTagName("Nominal").item(0).getTextContent())
								.intValue();
						parsedValues.setUsd_nominal(nominal);
					}

					if (id.equals(EUR.getCurrencyId())) {
						value = format
								.parse(element.getElementsByTagName("Value").item(0).getTextContent())
								.doubleValue();
						parsedValues.setEur(BigDecimal.valueOf(value).setScale(2, HALF_UP));
						nominal = format
								.parse(element.getElementsByTagName("Nominal").item(0).getTextContent())
								.intValue();
						parsedValues.setEur_nominal(nominal);
					}

					if (id.equals(HKD.getCurrencyId())) {
						value = format
								.parse(element.getElementsByTagName("Value").item(0).getTextContent())
								.doubleValue();
						parsedValues.setHkd(BigDecimal.valueOf(value).setScale(2, HALF_UP));
						nominal = format
								.parse(element.getElementsByTagName("Nominal").item(0).getTextContent())
								.intValue();
						parsedValues.setHkd_nominal(nominal);
					}
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return parsedValues;
	}
}