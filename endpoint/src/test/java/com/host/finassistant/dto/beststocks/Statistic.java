package com.host.finassistant.dto.beststocks;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "statistic")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {

	private Double capitalization;
	private Double earningsPerShare;
	private Double sma50;
	private Double sma200;
	private Double beta;
	private Double week52high;
	private Double week52low;
	private Double ebitda;
	private Double peRatio;
	private Double totalCash;
	private Double currentDebt;
	private Double revenue;
	private Double forwardPERatio;
	private String week52highDate;
	private String week52lowDate;
	private String nextEarningsDate;
	private Double revenuePerShare;
	private Double revenuePerEmployee;
	private Double debtToEquity;
	private Double profitMargin;
	private Double sharesOutstanding;
	@XmlElement(name = "float")
	private Double floatValue;
	private Double previousVolume;
	private Double avg10Volume;
	private Double avg30Volume;
	private Double dividendYield;
	private String nextDividendDate;
	private String nextBuyBeforeDate;
	private String exDividendDate;
	private Double maxChangePercent;
	private Double year5ChangePercent;
	private Double year2ChangePercent;
	private Double year1ChangePercent;
	private Double ytdChangePercent;
	private Double month6ChangePercent;
	private Double month3ChangePercent;
	private Double month1ChangePercent;
	private Double day30ChangePercent;
	private Double day5ChangePercent;
	private Double enterpriseValue;
	private Double enterpriseValueToRevenue;
	private Double grossProfit;
	private Double totalRevenue;
	private Double priceToSales;
	private String priceToBook;
	private Double pegRatio;
	private Double peHigh;
	private Double peLow;
	private Double putCallRatio;
	private String ipoDate;
	private Double dcf;

}
