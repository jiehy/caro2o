package cn.wolfcode.business.appointment.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 服务项对象 bus_service_item
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@Getter
@Setter
public class BusServiceItem extends BaseEntity
{

    private static final long serialVersionUID = 1L;
    public static final Integer CARPACKAGE_NO = 0;//不是套餐
    public static final Integer CARPACKAGE_YES = 1;//是套餐

    public static final Integer AUDITSTATUS_INIT = 0;//初始化
    public static final Integer AUDITSTATUS_AUDITING = 1;//审核中
    public static final Integer AUDITSTATUS_APPROVED = 2;//审核通过
    public static final Integer AUDITSTATUS_REPLY = 3;//重新调整
    public static final Integer AUDITSTATUS_NO_REQUIRED = 4;//无需审核

    public static final Integer SALESTATUS_OFF = 0;//下架
    public static final Integer SALESTATUS_ON = 1;//上架



    /** $column.columnComment */
    private Long id;

    /** 服务项名称 */
    @Excel(name = "服务项名称")
    private String name;

    /** 服务项原价 */
    @Excel(name = "服务项原价")
    private BigDecimal originalPrice;

    /** 服务项折扣价 */
    @Excel(name = "服务项折扣价")
    private BigDecimal discountPrice;

    /** 是否套餐【是/否】 */
    @Excel(name = "是否套餐【是/否】")
    private Integer carPackage;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;

    /** 服务分类【维修/保养/其他】 */
    @Excel(name = "服务分类【维修/保养/其他】")
    private Integer serviceCatalog;

    /** 审核状态【初始化/审核中/审核通过/审核拒绝/无需审核】 */
    @Excel(name = "审核状态【初始化/审核中/审核通过/审核拒绝/无需审核】")
    private Integer auditStatus;

    /** 上架状态【已上架/未上架】 */
    @Excel(name = "上架状态【已上架/未上架】")
    private Integer saleStatus;

}
