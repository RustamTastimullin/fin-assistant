package com.host.simplehelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class SimpleHelperApplicationTests {

	@Test
	public void simpleTest() {

		HashMap<String, Long> map = new HashMap<>();

		map.put("lol", null);
		map.put("kek", null);
		map.put("nub", 5L);

		System.out.println("lol: " + map.get("lol"));
		System.out.println("kek is: " + map.get("kek"));
		System.out.println("nub is: " + map.get("nub"));
	}

}
