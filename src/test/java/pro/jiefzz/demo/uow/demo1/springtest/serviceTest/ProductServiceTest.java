package pro.jiefzz.demo.uow.demo1.springtest.serviceTest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pro.jiefzz.demo.uow.demo1.springtest.RootTest;
import pro.jiefzz.demo.uowdemo.aggr.product.Product;
import pro.jiefzz.demo.uowdemo.service.ProductService;

class ProductServiceTest extends RootTest {

	private final static Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

	@Autowired
	private ProductService productService;

	@Test
	void testCreateNewSelfOperatedProduction() {
		Product p = productService.createNewSelfOperatedProduction("100只 现货正品一次性口罩三层无纺布薄款防尘透气冬天防风防寒女");
		logger.info("new Production: {}", p);
	}

//	@Test
//	void testMarkHot() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testMarkOnSale() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testMarkOffSale() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateNewTitme() {
//		fail("Not yet implemented");
//	}

}
