package com.host.simplehelper.dto.beststocks;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "analysis")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Analysis {

	private Integer smartScore;
	private String topScoreSince;
	private String analystConsensus;
	private String investorSentiment;
	private String bloggerConsensus;
	private String newsSentiment;
	private String insiderTrend;
	private String hedgeFundTrend;
	private Double priceTarget;
	private Double pricePotential;
	private Recommendations recommendations;
	private Integer lowPriceTarget;
	private Integer highPriceTarget;
	private String recommendationConsensus;
	private Integer recommendationCount;
	private String sma;
	private Integer hedgeFundTrendValue;
	private Double bloggerSectorAvg;
	private Double bloggerBullishSentiment;
	private Integer insidersLast3MonthsSum;
	private Double newsSentimentsBearishPercent;
	private Double newsSentimentsBullishPercent;
	private Double investorHoldingChangeLast7Days;
	private Double investorHoldingChangeLast30Days;
	private Double fundamentalsReturnOnEquity;
	private Double fundamentalsAssetGrowth;
	private Double technicalsTwelveMonthsMomentum;
	private Double topAnalystsPriceTarget;
	private Double topAnalystsPricePotential;
	private TopAnalystsRecommendations topAnalystsRecommendations;
	private Integer topAnalystsLowPriceTarget;
	private Integer topAnalystsHighPriceTarget;
	private String topAnalystsRecommendationConsensus;
	private Integer topAnalystsRecommendationCount;
	private String topAnalystsLastRecommendationDate;

}
