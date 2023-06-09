package cn.wolfcode.business.flowaudit.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 套餐审核对象 bus_car_package_audit
 * 
 * @author cjj
 * @date 2023-06-05
 */
@Getter
@Setter
@ToString
public class BusCarPackageAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public static final Integer STATUS_IN_ROGRESS = 0;//审核中
    public static final Integer STATUS_REJECT = 1;//审核拒绝
    public static final Integer STATUS_PASS = 2;//审核通过
    public static final Integer STATUS_CANCEL = 3;//审核撤销

    /** 主键 */
    private Long id;

    /** 服务单项id */
    @Excel(name = "服务单项id")
    private Long serviceItemId;

    /** $column.columnComment */
    private String serviceItemName;

    /** 服务单项备注 */
    @Excel(name = "服务单项备注")
    private String serviceItemInfo;

    /** 服务单项审核价格 */
    @Excel(name = "服务单项审核价格")
    private BigDecimal serviceItemPrice;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String instanceId;

    /** 创建者 */
    @Excel(name = "创建者")
    private String creatorId;

    /** 备注 */
    @Excel(name = "备注")
    private String info;

    /** 状态【进行中0/审核拒绝1/审核通过2/审核撤销3】 */
    @Excel(name = "状态【进行中0/审核拒绝1/审核通过2/审核撤销3】")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;
}