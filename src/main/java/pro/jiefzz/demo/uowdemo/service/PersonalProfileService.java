package pro.jiefzz.demo.uowdemo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kimffy24.uow.annotation.AutoCommit;
import com.github.kimffy24.uow.export.UoWContextLocator;

import pro.jiefzz.demo.uowdemo.aggr.profile.PersonalProfile;
import pro.jiefzz.demo.uowdemo.aggr.user.RegistrationUser;

@Service
public class PersonalProfileService {
	
	@Autowired
	private UserEntranceService userEntranceService;

	public PersonalProfile getPersonalProfile(Integer userId) {
		return UoWContextLocator.curr().fetch(PersonalProfile.class, userId);
	}

	@AutoCommit
	public PersonalProfile prepareEmptyPersonalProfile(Integer userId) {
		PersonalProfile newPersonalProfile;
		UoWContextLocator.addToContext(
				newPersonalProfile = new PersonalProfile(userId)
				);
		return newPersonalProfile;
	}
	
	@AutoCommit
	public void settingProfileByUser(Integer userId, String countryCode, String country, String idType, String idCode, String idAddress) {
		PersonalProfile personalProfile = getPersonalProfile(userId);
		RegistrationUser registrationUser = userEntranceService.getUserRegistrationInfo(userId);
		
		personalProfile.setContent(countryCode, country, idType, idCode, idAddress);
		registrationUser.markProfileExisted();
	}

	@AutoCommit
	public void reviewProfile(Integer userId, String reviewUser, Date profilePostDate) {
		PersonalProfile personalProfile = getPersonalProfile(userId);
		RegistrationUser registrationUser = userEntranceService.getUserRegistrationInfo(userId);
		
		personalProfile.markValidate(profilePostDate, reviewUser, false);
		registrationUser.markProfileCompleted();
	}
}

