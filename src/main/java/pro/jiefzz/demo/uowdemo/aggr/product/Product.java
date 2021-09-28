package pro.jiefzz.demo.uowdemo.aggr.product;

import java.util.Date;
import java.util.UUID;

import com.github.kimffy24.uow.annotation.MappingComment;
import com.github.kimffy24.uow.annotation.MappingType;
import com.github.kimffy24.uow.annotation.RBind;
import com.github.kimffy24.uow.export.skeleton.AbstractAggregateRoot;

//import pro.jiefzz.demo.uow.demo1.mapper.ProductUoWMapper;

/**
 * 产品
 * 
 * @author kimffy
 *
 */
//@RBind(ProductUoWMapper.class)
public class Product extends AbstractAggregateRoot<Long> {

	@MappingComment("产品的运营长号")
	@MappingType(tableType = "VARCHAR(32)")
	private String productOperationId;

	@MappingComment("店铺号")
	@MappingType(tableType = "VARCHAR(32)")
	private String shopId;

	@MappingComment("产品的长标题")
	@MappingType(tableType = "VARCHAR(64)")
	private String showTitle;

	@MappingComment("产品类型。 [1: 实物, 2: 服务, 3: 虚拟商品]")
	@MappingType(tableType = "TINYINT", tableAttr = "DEFAULT 1")
	private int productType = 1;

	@MappingComment("上架状态。 [1: 上架, 2: 未上架, 3: 已下架]")
	@MappingType(tableType = "TINYINT", tableAttr = "DEFAULT 2")
	private int productSaleState = 2;

	@MappingComment("爆款标记。 [0: 普通商品 1: 爆款]")
	@MappingType(tableType = "TINYINT", tableAttr = "DEFAULT 0")
	private int hotSaleFlag = 0;

	@MappingComment("记录的创建时间")
	@MappingType(tableAttr = "DEFAULT CURRENT_TIMESTAMP")
	private Date createAt;

	@MappingComment("记录的最后更新时间")
	@MappingType(tableAttr = "NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date latestModifyAt;

	public Product() {
		super();
	}

	public Product(String shopId, String showTitle) {
		super();
		this.shopId = shopId;
		this.showTitle = showTitle;
		this.productType = 2;
		
		this.productOperationId = UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public void markOnSales() {
		this.productSaleState = 1;
	}
	
	public void markOnSalesPause() {
		this.productSaleState = 2;
	}
	
	public void markOffSales() {
		this.productSaleState = 3;
	}
	
	public void markHotSaleFlag() {
		this.hotSaleFlag = 1;
	}
	
	public void unmarkHotSaleFlag() {
		this.hotSaleFlag = 0;
	}
	
	/**
	 * 设置一个业务规则：一旦修改商品信息，默认同时会把商品下架掉。
	 * @param newTitle
	 */
	public void updateShowTitle(String newTitle) {
		this.showTitle = newTitle;
		this.markOnSalesPause();
		this.unmarkHotSaleFlag();
	}
	
	// ==================== 扩展UoW中自定义Id字典的部分
	
	@MappingComment("商品的唯一主键")
	@MappingType(tableType = "BIGINT", tableAttr = "AUTO_INCREMENT")
	private Long productionId;

	@Override
	protected void setId(Long pid) {
		this.productionId = pid;
	}

	@Override
	public Long getId() {
		return productionId;
	}

	@Override
	public String getIdFieldName() {
		return "productionId";
	}
	
	// ==================== getter
	
	public String getProductOperationId() {
		return productOperationId;
	}

	public String getShopId() {
		return shopId;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public int getProductType() {
		return productType;
	}

	public int getProductSaleState() {
		return productSaleState;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getLatestModifyAt() {
		return latestModifyAt;
	}
	
}
