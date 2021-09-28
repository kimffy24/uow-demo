package pro.jiefzz.demo.uow.demo1.aggrGenerate;

import java.io.IOException;

import com.github.kimffy24.uow.util.GenerateSqlMapperUtil;

import pro.jiefzz.demo.uowdemo.aggr.demo.BankAccount;

@Deprecated
public class BankAccountGeneration {

	public static void main(String[] args) {
		try {
			GenerateSqlMapperUtil.generateSqlMapper(
					BankAccount.class,
					"bank_account");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
