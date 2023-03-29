package com.reggie;

import com.reggie.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReggieTakeOutApplicationTests {

	@Test
	void test1() {
		String fileName = "erererwew.jpg";
		System.out.println(fileName.substring(fileName.lastIndexOf(".")));
	}

}
