package pro.jiefzz.demo.uow.demo1.springtest.serviceTest;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import pro.jiefzz.demo.uow.demo1.springtest.RootTest;
import pro.jiefzz.demo.uowdemo.aggr.demo.BankAccount;
import pro.jiefzz.demo.uowdemo.service.BankAccountService;

@FixMethodOrder(MethodSorters.JVM)
class BankAccountServiceTest extends RootTest {
	
	@Autowired
	private BankAccountService bankAccountService;

//	@Autowired
//	private BandAccountUoWMapper bandAccountUoWMapper;

	@Test
	void testCreateAccount() {
		
		String userName = "寿司";
		String currencyCode= "CNY";
		
		BankAccount createAccount = bankAccountService.createAccount(userName, currencyCode);
		
		System.out.println("create new account: " + createAccount);
		
		
	}
	
//	@Test
	void testBankAccount2Deposit() {
		
		String currencyCode= "CNY";
		Double depositAmount = 200d;
		
		bankAccountService.depositToAccount(1l,
				currencyCode,
				depositAmount);
		
		
//		// 这里验证下数据库的数据
//		Map<String, Object> fetchById =
//				bandAccountUoWMapper.fetchById(1);
//		System.out.println("show db data: " + fetchById);
		
	}

	
//	@Test
	void testBankAccount3DepositFaild() {
		
		String currencyCode= "USD";
		Double depositAmount = 200d;
		
		RuntimeException e = Assertions.assertThrows(
                //扔出断言异常
                RuntimeException.class, () -> {
                	bankAccountService.depositToAccount(1l,
            				currencyCode,
            				depositAmount);
                });
		Assertions.assertTrue(
				"不同币种无法存入 !!!".equals(e.getMessage()),
				"预期异常不一致 !!!");
		
//		// 这里验证下数据库的数据
//		Map<String, Object> fetchById =
//				bandAccountUoWMapper.fetchById(1);
//		System.out.println("show db data: " + fetchById);
		
	}

//	@Test
	void testBankAccount3Transfer1() {
		String currencyCode= "CNY";
		Double depositAmount = 100d;
		
		try {
			bankAccountService.transfer(currencyCode, depositAmount, 1l, 2l);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ex: " + e.getMessage());
		}
		
		// 这里验证下数据库的数据
//		Map<String, Object> fetchById1 = bandAccountUoWMapper.fetchById(1);
//		System.out.println("show db data1: " + fetchById1);
//		Map<String, Object> fetchById2 = bandAccountUoWMapper.fetchById(2);
//		System.out.println("show db data2: " + fetchById2);
	}

//	@Test
	void testBankAccount3Transfer2() {
		String currencyCode= "CNY";
		Double depositAmount = 201d;
		
		try {
			bankAccountService.transfer(currencyCode, depositAmount, 1l, 3l);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ex: " + e.getMessage());
		}
		
		// 这里验证下数据库的数据
//		Map<String, Object> fetchById1 = bandAccountUoWMapper.fetchById(1);
//		System.out.println("show db data1: " + fetchById1);
//		Map<String, Object> fetchById3 = bandAccountUoWMapper.fetchById(3);
//		System.out.println("show db data3: " + fetchById3);
	}
	
//	@Test
	void testBankAccount3Transfer3() {
		String currencyCode= "CNY";
		Double depositAmount = 109d;
		
		try {
			bankAccountService.transfer(currencyCode, depositAmount, 1l, 3l);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ex: " + e.getMessage());
		}
		
//		// 这里验证下数据库的数据
//		Map<String, Object> fetchById1 = bandAccountUoWMapper.fetchById(1);
//		System.out.println("show db data1: " + fetchById1);
//		Map<String, Object> fetchById3 = bandAccountUoWMapper.fetchById(3);
//		System.out.println("show db data3: " + fetchById3);
	}
	
//	@Test
	void testBankAccount3Transfer4_1() {
		long accountId1 = 1l;
		
		String currencyCode= "CNY";
		Double depositAmount = 109d;
		

//		// 这里验证下数据库的数据 pre
//		Map<String, Object> fetchById1 = bandAccountUoWMapper.fetchById(accountId1);
//		System.out.println("show db data1: " + fetchById1);
		
		try {
			bankAccountService.lock(currencyCode, 200d, accountId1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ex: " + e.getMessage());
		}
		
//		// 这里验证下数据库的数据 post
//		fetchById1 = bandAccountUoWMapper.fetchById(accountId1);
//		System.out.println("show db data1: " + fetchById1);
	}
	
//	@Test
	void testBankAccount3Transfer4_2() {
		long accountId1 = 1l;
		long accountId2 = 3l;
		
		String currencyCode= "CNY";
		Double depositAmount = 80d;

		bankAccountService.lock(currencyCode, 1d, accountId1);
		
//		bankAccountService.transfer(currencyCode, depositAmount, accountId1, accountId2);

//		// 这里验证下数据库的数据
//		Map<String, Object> fetchById1 = bandAccountUoWMapper.fetchById(accountId1);
//		System.out.println("show db data1: " + fetchById1);
//		Map<String, Object> fetchById3 = bandAccountUoWMapper.fetchById(accountId2);
//		System.out.println("show db data3: " + fetchById3);
	}
}
