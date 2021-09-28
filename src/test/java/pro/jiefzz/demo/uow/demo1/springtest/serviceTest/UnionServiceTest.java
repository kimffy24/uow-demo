package pro.jiefzz.demo.uow.demo1.springtest.serviceTest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pro.jiefzz.demo.uow.demo1.springtest.RootTest;
import pro.jiefzz.demo.uowdemo.service.UnionService;

class UnionServiceTest extends RootTest {
	
	private final static Logger logger = LoggerFactory.getLogger(UnionServiceTest.class);

	@Autowired
	private UnionService unionService;
	
	@Test
	void testsetBothEmail() {
		
		unionService.setBothEmail();
		
	}

}
