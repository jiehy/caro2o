package cn.wolfcode.business.information.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 客户档案对象 bus_customer_file
 * 
 * @author HH
 * @date 2023-06-09
 */
public class BusCustomerFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户名字 */
    @Excel(name = "客户名字")
    private String customerName;

    /** 客户电话 */
    @Excel(name = "客户电话")
    private String customerPhone;

    /** 客户性别 */
    @Excel(name = "客户性别")
    private Integer customerGender;

    /** 车辆信息 */
    @Excel(name = "车辆信息")
    private String customerCarInfo;

    /** 录入人 */
    @Excel(name = "录入人")
    private String customerCreateMan;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customerCreateTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setCustomerPhone(String customerPhone) 
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() 
    {
        return customerPhone;
    }
    public void setCustomerGender(Integer customerGender) 
    {
        this.customerGender = customerGender;
    }

    public Integer getCustomerGender() 
    {
        return customerGender;
    }
    public void setCustomerCarInfo(String customerCarInfo) 
    {
        this.customerCarInfo = customerCarInfo;
    }

    public String getCustomerCarInfo() 
    {
        return customerCarInfo;
    }
    public void setCustomerCreateMan(String customerCreateMan) 
    {
        this.customerCreateMan = customerCreateMan;
    }

    public String getCustomerCreateMan() 
    {
        return customerCreateMan;
    }
    public void setCustomerCreateTime(Date customerCreateTime) 
    {
        this.customerCreateTime = customerCreateTime;
    }

    public Date getCustomerCreateTime() 
    {
        return customerCreateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("customerPhone", getCustomerPhone())
            .append("customerGender", getCustomerGender())
            .append("customerCarInfo", getCustomerCarInfo())
            .append("customerCreateMan", getCustomerCreateMan())
            .append("customerCreateTime", getCustomerCreateTime())
            .toString();
    }
}
