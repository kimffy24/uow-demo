package pro.jiefzz.demo.uowdemo.aggr.profile.contraint;

public class ProfileReviewStatus {
	
	/**
	 * 提交中/待审核
	 */
	public final static int COMMITING = -1;

	public final static int OK = 0;
	
	/**
	 * 审核不通过
	 */
	public final static int NOT_PASSES = 1;
	
	/**
	 * 资料过期
	 */
	public final static int OUT_OF_DATE = 2;
	
	/**
	 * 冻结，一般是遇到非法情况
	 */
	public final static int FROZEN = 4;
}
