package pro.jiefzz.demo.uowdemo.aggr.user.constraint;

public final class RegistrationUserStatus {

	public final static int None = 0;
	
	/**
	 * 仅仅完成注册，没有补充资料
	 */
	public final static int OnlyRegister = 1;
	
	/**
	 * 非有效状态
	 */
	public final static int ProfileExisted = 2;
	
	/**
	 * 资料完整，并审核
	 */
	public final static int ProfileCompleted = 3;
	
}
