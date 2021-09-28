package pro.jiefzz.demo.uowdemo.service;

import org.springframework.stereotype.Service;

import com.github.kimffy24.uow.annotation.AutoCommit;
import com.github.kimffy24.uow.export.UoWContextLocator;

import pro.jiefzz.demo.uowdemo.aggr.user.RegistrationUser;

@Service
public class UserEntranceService {

	public RegistrationUser getUserRegistrationInfo(int userId) {
		return UoWContextLocator.curr().fetch(RegistrationUser.class, userId);
	}

	@AutoCommit
	public RegistrationUser registerNormalUserStep1(String phone, String email) {
		return registerBasicUserStep1(1, phone, email);
	}

	@AutoCommit
	public RegistrationUser registerEnterpriseUserStep1(String phone, String email) {
		return registerBasicUserStep1(2, phone, email);
	}
	
	private RegistrationUser registerBasicUserStep1(int type, String phone, String email) {
		RegistrationUser newRegistrationUser;
		UoWContextLocator.addToContext(
				newRegistrationUser = new RegistrationUser(type, phone, email)
				);
		return newRegistrationUser;
	}

	@AutoCommit
	public void updatePasswordFroce(int userId, String passwdNew) {
		RegistrationUser rUser = UoWContextLocator.curr().fetch(RegistrationUser.class, userId);
		rUser.updatePasswordForce(passwdNew);
	}

	@AutoCommit
	public void updatePassword(int userId, String passwdNew, String passwdOld) {
		RegistrationUser rUser = UoWContextLocator.curr().fetch(RegistrationUser.class, userId);
		rUser.updatePassword(passwdOld, passwdNew);
	}
	
	@AutoCommit
	public void frozenPermanently(int userId) {
		RegistrationUser rUser = UoWContextLocator.curr().fetch(RegistrationUser.class, userId);
		rUser.frozen("君要臣死，你非死不可；");
	}
	
	@AutoCommit
	public void frozenTemporarily(int userId, long until) {
		RegistrationUser rUser = UoWContextLocator.curr().fetch(RegistrationUser.class, userId);
		rUser.frozen("给你小小的教训；", until);
	}
	
	@AutoCommit
	public void unfrozen(int userId) {
		RegistrationUser rUser = UoWContextLocator.curr().fetch(RegistrationUser.class, userId);
		rUser.unfrozen();
	}
}
