package com.host.simplehelper.dto.beststocks;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "response")
@Getter
@Setter
public class Response {

	private Integer count;
	private Integer available;
	private Integer limit;
	private Integer offset;
	private Data data;
	private Sort sort;

}
