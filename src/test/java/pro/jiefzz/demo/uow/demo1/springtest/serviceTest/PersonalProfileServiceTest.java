package pro.jiefzz.demo.uow.demo1.springtest.serviceTest;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pro.jiefzz.demo.uow.demo1.springtest.RootTest;
import pro.jiefzz.demo.uowdemo.aggr.profile.PersonalProfile;
import pro.jiefzz.demo.uowdemo.service.PersonalProfileService;

@FixMethodOrder(MethodSorters.JVM)
public class PersonalProfileServiceTest extends RootTest {
	
	private final static Logger logger = LoggerFactory.getLogger(UserEntranceServiceTest.class);
	
	@Autowired
	PersonalProfileService personalProfileService;
	
	private final static int user_id = 2;
	
	@AfterEach
	public void pause() {
		logger.info("======");
	}
	
//	@Test
//	public void prepare() {
//		personalProfileService.prepareEmptyPersonalProfile(user_id);
//	}

//	@Test
//	public void uploadProfile() {
//		personalProfileService.settingProfileByUser(user_id, "86", "中国啦", "PersonalIdCard", "08243352", "Welcome to the Guangdong");
//	}
	
//	@Test
//	public void reviewProfile1() {
//		personalProfileService.reviewProfile(user_id, "Super Administrator", new Date());
//	}

	@Test
	public void reviewProfile2() {
		PersonalProfile personalProfile = personalProfileService.getPersonalProfile(user_id);
		personalProfileService.reviewProfile(user_id, "Super Administrator", personalProfile.getLatestPostProfileAt());
	}

}
