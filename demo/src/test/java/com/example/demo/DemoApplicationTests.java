package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "areaLocation = Europe/London")
class DemoApplicationTests {

	@Value("${areaLocation}")
	String areaLocation;

	@Test
	void test() {
	}

}
