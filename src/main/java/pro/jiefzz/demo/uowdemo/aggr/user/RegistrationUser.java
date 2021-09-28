package pro.jiefzz.demo.uowdemo.aggr.user;

import static pro.jk.ejoker.common.system.enhance.StringUtilx.fmt;

import java.util.Date;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.github.kimffy24.uow.annotation.MappingComment;
import com.github.kimffy24.uow.annotation.MappingType;
import com.github.kimffy24.uow.annotation.RBind;
import com.github.kimffy24.uow.export.skeleton.SimpleAggregateRoot;

import pro.jiefzz.demo.uowdemo.aggr.user.constraint.RegistrationUserAvaliableStatus;
import pro.jiefzz.demo.uowdemo.aggr.user.constraint.RegistrationUserStatus;
//import pro.jiefzz.demo.uow.demo1.mapper.RegistrationUserUoWMapper;
import pro.jk.ejoker.common.system.enhance.StringUtilx;

/**
 * 登记用户
 * @author kimffy
 *
 */
//@RBind(RegistrationUserUoWMapper.class)
public class RegistrationUser extends SimpleAggregateRoot<Integer> {
	
	@MappingComment("登记手机号")
	@MappingType(tableType = "VARCHAR(32)")
	private String phone;

	@MappingComment("登记邮箱")
	@MappingType(tableType = "VARCHAR(254)")
	private String email;

	@MappingComment("凭证")
	@MappingType(tableType = "VARCHAR(128)")
	private String password;

	@MappingComment("用户类型。 [1: 普通用户, 2: 企业, 3: 系统用户]")
	@MappingType(tableType = "TINYINT", tableAttr = "DEFAULT 1")
	private int userType = 1;

	@MappingComment("资料补充状态")
	@MappingType(tableType = "TINYINT")
	private int profileState = RegistrationUserStatus.OnlyRegister;

	@MappingComment("账户可访问状态")
	@MappingType(tableType = "TINYINT")
	private int avaliableState = RegistrationUserAvaliableStatus.OK;

	@MappingComment("冻结持续到的时间戳（ms）。临时冻结时可用")
	private long frozenUntil = -1;

	@MappingComment("账户被冻结的原因")
	@MappingType(tableType = "VARCHAR(64)")
	private String frozenReason = null;

	@MappingComment("记录的创建时间")
	@MappingType(tableAttr = "DEFAULT CURRENT_TIMESTAMP")
	private Date createAt;

	@MappingComment("记录的创建时间")
	@MappingType(tableAttr = "NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date latestModifyAt;
	
	public RegistrationUser() {
		super();
	}
	
	/**
	 * 通过手机号创建账号
	 * @param userType
	 * @param phone
	 */
	public RegistrationUser(int userType, String phone, String email) {
		super();
		boolean isOk = false;
		if(isOk |= StringUtils.hasText(phone))
			this.phone = phone;
		if(isOk |= StringUtils.hasText(email))
			this.email = email;
		if(!isOk)
			throw new RuntimeException("非法用户标识!!!");
		this.userType = userType;
		this.createAt = new Date();
	}
	
	public void initialEmail(String email) {
		if(StringUtilx.isSenseful(this.email)) {
			throw new RuntimeException("Email 不能被修改!!!");
		}
		this.email = email;
	}
	
	public void markProfileExisted() {
		this.profileState = RegistrationUserStatus.ProfileExisted;
	}
	
	public void markProfileCompleted() {
		this.profileState = RegistrationUserStatus.ProfileCompleted;
	}
	
	public void frozen(String reason) {
		this.avaliableState = RegistrationUserAvaliableStatus.FronzenPermanently;
		this.setOrGenerateFrozenReason(reason);
	}
	
	public void frozen(String reason, long frozenTo) {
		this.avaliableState = RegistrationUserAvaliableStatus.FrozenTemporarily;
		this.frozenUntil = frozenTo;
		this.setOrGenerateFrozenReason(reason);
	}
	
	private void setOrGenerateFrozenReason(String reason) {
		this.frozenReason = StringUtils.hasText(phone) ? reason : fmt("Frozen At ({}), No reason provided.", new Date().toString());
	}
	
	public void unfrozen() {
		this.avaliableState = RegistrationUserAvaliableStatus.OK;
		this.frozenUntil = -1;
		this.frozenReason = fmt("Latest unfrozen at ({}).", new Date());
	}
	
	public void updatePassword(String oldPassword, String newPassword) {
		ensureAvaliable();
		if(!isPasswordOk(oldPassword))
			throw new RuntimeException("Original Password not correct !!!");
		// 本应跟一些不变信息一起混淆的，现在简化
		this.password = newPassword;
	}
	
	public boolean isPasswordOk(String passwd) {
		// 本应跟一些不变信息一起混淆的，现在简化
		return Objects.equals(this.password, passwd);
	}

	public void updatePasswordForce(String newPasswd) {
		// 本应跟一些不变信息一起混淆的，现在简化
		this.password = newPasswd;
	}
	
	public boolean isAvaliable() {
		if(avaliableState == RegistrationUserAvaliableStatus.OK)
			return true;
		if(avaliableState == RegistrationUserAvaliableStatus.FrozenTemporarily) {
			return frozenUntil < System.currentTimeMillis();
		}
		return false;
	}
	
	public void ensureAvaliable() {
		if(!isAvaliable())
			throw new RuntimeException("User status is not avaliable now !!!");
	}
	
	
	// ================= getter
	

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getUserType() {
		return userType;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public int getProfileState() {
		return profileState;
	}

	public int getAvaliableState() {
		return avaliableState;
	}

	public long getFrozenUntil() {
		return frozenUntil;
	}

	public String getFrozenReason() {
		return frozenReason;
	}
	
}
