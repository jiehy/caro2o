package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class BusServiceItemVO extends BaseEntity
{

    /** 服务项名称 */
    private String name;

    /** 服务项原价 */
    private BigDecimal originalPrice;

    /** 服务项折扣价 */
    private BigDecimal discountPrice;

    /** 是否套餐【是/否】 */
    private Integer carPackage;

    /** 服务分类【维修/保养/其他】 */
    private Integer serviceCatalog;

    /** 备注信息 */
    private String info;

}