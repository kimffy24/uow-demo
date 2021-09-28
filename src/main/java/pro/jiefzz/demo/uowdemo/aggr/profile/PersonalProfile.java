package pro.jiefzz.demo.uowdemo.aggr.profile;

import java.util.Date;

import com.github.kimffy24.uow.annotation.MappingComment;
import com.github.kimffy24.uow.annotation.MappingType;
import com.github.kimffy24.uow.annotation.RBind;
import com.github.kimffy24.uow.export.skeleton.SimpleAggregateRoot;

import pro.jiefzz.demo.uowdemo.aggr.profile.contraint.ProfileReviewStatus;

/**
 * 个人资料
 * @author kimffy
 *
 */
//@RBind(PersonalProfileUoWMapper.class)
public class PersonalProfile extends SimpleAggregateRoot<Integer> {

	@MappingComment("登记资料的审核状态，即资料的有效状态")
	@MappingType(tableType = "TINYINT", tableAttr = "DEFAULT -1")
	private int latestReviewStatus = ProfileReviewStatus.COMMITING;

	@MappingComment("登记资料的审核状态，即资料的有效状态")
	@MappingType(tableType = "VARCHAR(32)")
	private String latestReviewBy;
	
	@MappingComment("最近一次审核时间")
	@MappingType(tableAttr = "")
	private Date latestReviewAt;
	
	@MappingComment("最近一次更新资料时间")
	@MappingType(tableAttr = "")
	private Date latestPostProfileAt;

	@MappingComment("所在国家或地区的编号")
	@MappingType(tableType = "VARCHAR(16)")
	private String countryCode;

	@MappingComment("所在国家或地区的简称")
	@MappingType(tableType = "VARCHAR(32)")
	private String country;

	@MappingComment("身份证件类型")
	@MappingType(tableType = "VARCHAR(32)")
	private String idType;

	@MappingComment("身份证件号码")
	@MappingType(tableType = "VARCHAR(64)")
	private String idCode;

	@MappingComment("身份证件登记地址")
	@MappingType(tableType = "VARCHAR(64)")
	private String idAddress;
	
	// 证件有效期，为简化不作出处理
	// private String idValidatedUntil;

	public PersonalProfile() { }
	
	public PersonalProfile(Integer userId) {
		super(userId);
		this.setContent("", "", "", "", "");
		this.latestPostProfileAt = null;
	}
	
	public PersonalProfile(Integer userId, String countryCode, String country, String idType, String idCode, String idAddress) {
		super(userId);
		this.setContent(countryCode, country, idType, idCode, idAddress);
	}
	
	public ProfileValidateInfo getValidateInfo() {
		switch(latestReviewStatus) {
		case ProfileReviewStatus.OK: {
			if(latestPostProfileAt.compareTo(latestReviewAt) < 0) {
				return ProfileValidateInfo.of(true, "审核通过");
			} else {
				return ProfileValidateInfo.of(false, "资料已更新，待重新审核");
			}
		}
		case ProfileReviewStatus.COMMITING:
			return ProfileValidateInfo.of(false, "资料已提交，但未被审核");
		case ProfileReviewStatus.NOT_PASSES:
			return ProfileValidateInfo.of(false, "资料已提交，但审核未通过");
		case ProfileReviewStatus.OUT_OF_DATE:
		case ProfileReviewStatus.FROZEN:
			return ProfileValidateInfo.of(false, "资料已过期或被冻结");
		default:
			return ProfileValidateInfo.of(false, "资料状态非法");
			
		}
	}
	
	public void markValidate(Date profileDate, String exector, boolean makeUnPassed) {
		if(latestPostProfileAt.compareTo(profileDate) != 0) {
			throw new RuntimeException("用户已提前更新了资料!!!");
		}
		
		this.latestReviewAt = new Date();
		this.latestReviewStatus = makeUnPassed ? ProfileReviewStatus.NOT_PASSES : ProfileReviewStatus.OK;
		this.latestReviewBy = exector;
	}

	public void setContent(String countryCode, String country, String idType, String idCode, String idAddress) {
		this.countryCode = countryCode;
		this.country = country;
		this.idType = idType;
		this.idCode = idCode;
		this.idAddress = idAddress;
		this.latestPostProfileAt = new Date();
	}
	
	// ================= getter
	
	public int getLatestReviewStatus() {
		return latestReviewStatus;
	}

	public String getLatestReviewBy() {
		return latestReviewBy;
	}

	public Date getLatestReviewAt() {
		return latestReviewAt;
	}

	public Date getLatestPostProfileAt() {
		return latestPostProfileAt;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountry() {
		return country;
	}

	public String getIdType() {
		return idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public String getIdAddress() {
		return idAddress;
	}
	
	// ================= tuple prototype
	
	public final static class ProfileValidateInfo {
		public final boolean isValidate;
		
		public final String validateInfo;

		public ProfileValidateInfo(boolean isValidate, String validateInfo) {
			this.isValidate = isValidate;
			this.validateInfo = validateInfo;
		}
		
		public static ProfileValidateInfo of(boolean isValidate, String validateInfo) {
			return new ProfileValidateInfo(isValidate, validateInfo);
		}
	}
}
