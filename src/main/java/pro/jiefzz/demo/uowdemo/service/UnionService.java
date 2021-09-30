package pro.jiefzz.demo.uowdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kimffy24.uow.annotation.AutoCommit;

import pro.jiefzz.demo.uowdemo.aggr.user.RegistrationUser;

@Service
public class UnionService {

	@Autowired
	private UserEntranceService userEntranceService;
	
	@AutoCommit
	@Transactional
	public boolean setBothEmail() {
		
		RegistrationUser user2 = userEntranceService.getUserRegistrationInfo(2);
		RegistrationUser user5 = userEntranceService.getUserRegistrationInfo(5);
		
		user2.initialEmail("abc@jiefzz.com");
		user5.initialEmail("xyz@jiefzz.com");
		
		return true;
	}
	
}
