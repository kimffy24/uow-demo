package pro.jiefzz.demo.uowdemo.aggr.demo;

import static pro.jk.ejoker.common.system.enhance.StringUtilx.fmt;
import static pro.jk.ejoker.common.system.enhance.StringUtilx.isSenseful;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.github.kimffy24.uow.annotation.MappingComment;
import com.github.kimffy24.uow.annotation.MappingTableAttribute;
import com.github.kimffy24.uow.annotation.MappingType;
import com.github.kimffy24.uow.annotation.RBind;
import com.github.kimffy24.uow.export.skeleton.AggregateRootLifeCycleAware;
import com.github.kimffy24.uow.export.skeleton.SimpleAggregateRoot;

import net.minidev.json.JSONAwareEx;
import net.minidev.json.JSONValue;
//import pro.jiefzz.demo.uow.demo1.mapper.BandAccountUoWMapper;
import pro.jk.ejoker.common.context.annotation.persistent.PersistentIgnore;

//@RBind(BandAccountUoWMapper.class)
@MappingTableAttribute(
		tableName = "t_bank_account",
		alterAppends = {
				"ALTER TABLE {} add constraint `uk_un_cc` unique(`user_name`,`currency_code`)",
				"-- 测试"
		}
)
public class BankAccount extends SimpleAggregateRoot<Long> implements AggregateRootLifeCycleAware {

	@MappingComment("登记用户")
	@MappingType(tableType = "VARCHAR(32)")
	private String userName;

	@MappingComment("货币代码，例如USD，CNY这种")
	@MappingType(tableType = "VARCHAR(6)")
	private String currencyCode;

	@MappingComment("余额")
	@MappingType(tableType = "DECIMAL(18,4)", tableAttr = "DEFAULT 0")
	private Double balance;

	@MappingComment("冻结标记")
	@MappingType(tableAttr = "DEFAULT false")
	private boolean ban;

	@MappingComment("冻结资金集合")
	@MappingType(tableType = "TEXT", jdbcType="BLOB", tableAttr = "DEFAULT NULL")
	private String lockBalace;
	
	/**
	 * 映射lockBalace属性
	 */
	@PersistentIgnore
	private Map<String, Double> __lockBalace;

	@Override
	public void postCreation() {
		// uow 返回前执行用户的转换
		JSONAwareEx parse = (JSONAwareEx )JSONValue.parse(lockBalace);
		if(parse instanceof Map)
			__lockBalace = (Map<String, Double> )parse;
	}

	@Override
	public void preCommit() {
		if(__lockBalace instanceof Map) {
			if(((Map )__lockBalace).isEmpty())
				lockBalace = null;
			else
				lockBalace = JSONValue.toJSONString(__lockBalace);
		}
	}
	
	/**
	 * 检查可用余额是否足够给请求的资金
	 * @param value
	 */
	private void checkEnough(Double value) {
		double avaliable = this.getAvaliableBalance();
		if(avaliable - value < 0)
			throw new RuntimeException("余额不足，无法提现/转账 !!!");
	}
	
	public void lock(Long bussId, Double value) {
		checkEnough(value);
		if(null == __lockBalace)
			__lockBalace = new TreeMap<>();
		Double previousLocked = __lockBalace.putIfAbsent("" + bussId, value);
		if(null != previousLocked) {
			throw new RuntimeException(fmt("This buss id has locked !!! [accountId: {}, bussId: {}]", this.getId(), bussId));
		}
	}
	
	/**
	 * 初始化正好，并设置必要信息
	 * @param userName
	 * @param currencyCode
	 * @param balance
	 */
	public void initAccount(String userName, String currencyCode, Double balance) {
		if(isSenseful(this.userName)) {
			throw new RuntimeException("不能重复初始化账户 !!!");
		}
		this.userName = userName;
		this.currencyCode = currencyCode;
		this.balance = balance;
	}
	
	public void initAccount(String userName, String currencyCode) {
		this.initAccount(userName, currencyCode, 0d);
	}
	
	/**
	 * 简化业务：存入资金
	 * @param currencyCode
	 * @param value
	 */
	public void deposit(String currencyCode, Double value) {
		if(!Objects.equals(this.currencyCode, currencyCode)) {
			throw new RuntimeException("不同币种无法存入 !!!"); // 模拟见到的业务规则校验
		}
		this.balance += value;
	}

	/**
	 * 简化业务：转出资金
	 * @param currencyCode
	 * @param value
	 */
	public void withdraw(String currencyCode, Double value) {
		if(!Objects.equals(this.currencyCode, currencyCode)) {
			throw new RuntimeException("账户币种不匹配 !!!"); // 模拟见到的业务规则校验
		}
		this.checkEnough(value);
		this.balance -= value;
	}
	
	// ==================== getter
	
	public Double getAvaliableBalance() {
		Double sum = this.balance;
		if(null != __lockBalace) {
			sum -= __lockBalace.values().stream().mapToDouble(i -> i).sum();
		}
		return sum;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public Double getBalance() {
		return balance;
	}

	public boolean isForzen() {
		return ban;
	}
	
}
