package com.host.finassistant.dto.beststocks;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

	private Splits splits;
	private Boolean inSpbe;
	private Boolean indexableDescription;
	private Status status;
	private Prices prices;
	private String name;
	private String slug;
	private String ticker;
	private String type;
	private String company;
	private String logo;
	private String ogImage;
	private String description;
	private String mobileDescription;
	private Industry industry;
	private String exchange;
	private Long employees;
	private String ceo;
	private String site;
	private String currency;
	private Tags tags;
	private String sector;
	private Country country;
	private Address address;
	private String isin;
	private Analysis analysis;
	private Statistic statistic;

}
