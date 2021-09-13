package com.isfp.app.ws.shared;

//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class UtilsTest {
//	
//	@Autowired
//	Utils utils;
//
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@Test
//	void testGenerateUserId() {
//		
//		String userId = utils.generateAddressId(30);
//		String userId2 = utils.generateAddressId(30);
//		
//		assertNotNull(userId);
//		assertNotNull(userId2);
//		
//		assertTrue(userId.length() == 30);
//		assertTrue(!userId.equalsIgnoreCase(userId2));
//		
//		
// 	}
//
//	@Disabled
//	@Test
//	void testGenerateAddressId() {
//		fail("Not yet implemented");
//	}
//
//}
