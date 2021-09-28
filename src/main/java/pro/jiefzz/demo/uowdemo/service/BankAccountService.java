package pro.jiefzz.demo.uowdemo.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.kimffy24.uow.annotation.AutoCommit;
import com.github.kimffy24.uow.export.UoWContextLocator;

import pro.jiefzz.demo.uowdemo.aggr.demo.BankAccount;

@Service
public class BankAccountService {

	@AutoCommit
	public BankAccount createAccount(String userName, String currencyCode) {
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.initAccount(userName, currencyCode);
		
		UoWContextLocator.addToContext(bankAccount);
		
		// 对象未提交到数据库时，是未获得id的，现在直接返回业务对象即可;
		// 退出AutoCommit方法是，UoW会把自增id值写入业务对象的id属性中；
		return bankAccount;
		
	}

	@AutoCommit
	public void depositToAccount(Long accountId, String currencyCode, Double balance) {
		BankAccount account = UoWContextLocator.curr().fetch(BankAccount.class, accountId);
		account.deposit(currencyCode, balance);
		// 无异常返回即成功
	}

	@AutoCommit
	@Transactional
	public void transfer(String currencyCode, Double balance, Long outAccountId, Long inAccountId) {
		BankAccount account1 = UoWContextLocator.curr().fetch(BankAccount.class, outAccountId);
		BankAccount account2 = UoWContextLocator.curr().fetch(BankAccount.class, inAccountId);
		account1.withdraw(currencyCode, balance);
		account2.deposit(currencyCode, balance);
		// 无异常返回即成功
	}

	@AutoCommit
	public void lock(String currencyCode, Double balance, Long accountId) {
		BankAccount account = UoWContextLocator.curr().fetch(BankAccount.class, accountId);
		if(!Objects.equals(currencyCode, account.getCurrencyCode())) {
			throw new RuntimeException("币种不匹配 !!!");
		}
		account.lock(9000003l, balance);
	}
}
