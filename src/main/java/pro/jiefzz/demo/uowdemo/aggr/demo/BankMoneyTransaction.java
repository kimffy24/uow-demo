package pro.jiefzz.demo.uowdemo.aggr.demo;

import com.github.kimffy24.uow.annotation.MappingComment;
import com.github.kimffy24.uow.annotation.MappingType;

/**
 * 模拟一个资金业务的执行类
 * @author kimffy
 *
 */
public class BankMoneyTransaction {

	@MappingComment("业务状态。 [-2: 取消 -1: 未执行, 0: 执行中, 1: 完成, 2: 执行失败]")
	@MappingType(tableType = "TINYINT", tableAttr = "DEFAULT -1")
	private int state;

	@MappingComment("业务类型。 [-2: 取消 -1: 未执行, 0: 执行中, 1: 完成, 2: 执行失败]")
	@MappingType(tableType = "VARCHAR(24)", tableAttr = "NOT NULL")
	private String bussType;
	
	@MappingComment("涉及账号；一个半角逗号(,)未分隔符组成的数组")
	@MappingType(tableType = "VARCHAR(128)", tableAttr = "NOT NULL")
	private String accounts;
	
}
