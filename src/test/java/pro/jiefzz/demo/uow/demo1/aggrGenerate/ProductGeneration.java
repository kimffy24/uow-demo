package pro.jiefzz.demo.uow.demo1.aggrGenerate;

import java.io.IOException;

import com.github.kimffy24.uow.util.GenerateSqlMapperUtil;

import pro.jiefzz.demo.uowdemo.aggr.product.Product;

@Deprecated
public class ProductGeneration {

	public static void main(String[] args) {
		try {
			GenerateSqlMapperUtil.generateSqlMapper(
					Product.class,
					"product");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
