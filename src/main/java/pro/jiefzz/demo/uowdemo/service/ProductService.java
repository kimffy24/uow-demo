package pro.jiefzz.demo.uowdemo.service;

import org.springframework.stereotype.Service;

import com.github.kimffy24.uow.annotation.AutoCommit;
import com.github.kimffy24.uow.export.UoWContextLocator;

import pro.jiefzz.demo.uowdemo.aggr.product.Product;

@Service
public class ProductService {

	@AutoCommit
	public Product createNewSelfOperatedProduction(String showTitle) {
		return UoWContextLocator.curr().add(new Product("10000", showTitle));
	}

	@AutoCommit
	public void markHot(Long productionId) {
		Product p = UoWContextLocator.curr().fetch(Product.class, productionId);
		p.markHotSaleFlag();
	}

	@AutoCommit
	public void markOnSale(Long productionId) {
		Product p = UoWContextLocator.curr().fetch(Product.class, productionId);
		p.markOnSales();
	}

	@AutoCommit
	public void markOffSale(Long productionId) {
		Product p = UoWContextLocator.curr().fetch(Product.class, productionId);
		p.markOffSales();
	}

	@AutoCommit
	public void updateNewTitme(Long productionId, String newTitle) {
		Product p = UoWContextLocator.curr().fetch(Product.class, productionId);
		p.updateShowTitle(newTitle);
	}
}
