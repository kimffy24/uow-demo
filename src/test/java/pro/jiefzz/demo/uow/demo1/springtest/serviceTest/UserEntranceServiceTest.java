package pro.jiefzz.demo.uow.demo1.springtest.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pro.jiefzz.demo.uow.demo1.springtest.RootTest;
import pro.jiefzz.demo.uowdemo.aggr.user.RegistrationUser;
import pro.jiefzz.demo.uowdemo.service.UserEntranceService;

public class UserEntranceServiceTest extends RootTest {
	
	private final static Logger logger = LoggerFactory.getLogger(UserEntranceServiceTest.class);
	
	@Autowired
	UserEntranceService userEntranceService;
	
	@AfterEach
	public void pause() {
		logger.info("======");
	}
	
	private static volatile Integer user1_id = 5;
//	private static volatile Integer user2_id = null;

	@Test
	public void test1_RegisterStep1() {
		user1_id = userEntranceService.registerNormalUserStep1("15626280000", null).getId();
//		user2_id = userEntranceService.registerNormalUserStep1("", "jiefzz24@gmail.com").getId();
	}

//	@Test
	public void test2_FronzenPermanently() {
		userEntranceService.frozenPermanently(user1_id);
		
		RegistrationUser userRegistrationInfo = userEntranceService.getUserRegistrationInfo(user1_id);
		assertEquals(userRegistrationInfo.isAvaliable(), false, "isAvaliable() == false");
		
		userEntranceService.unfrozen(user1_id);
		
		userRegistrationInfo = userEntranceService.getUserRegistrationInfo(user1_id);
		assertEquals(userRegistrationInfo.isAvaliable(), true, "isAvaliable() == true");
	}

//	@Test
	public void test3_password() {
		userEntranceService.updatePasswordFroce(user1_id, "qqqwww");
	}
	
}