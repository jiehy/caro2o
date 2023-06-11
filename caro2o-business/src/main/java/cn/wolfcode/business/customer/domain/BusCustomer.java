package cn.wolfcode.business.customer.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 客户信息对象 bus_customer
 * 
 * @author cjj
 * @date 2023-06-10
 */
public class BusCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 性别【0男/1女】 */
    @Excel(name = "性别【0男/1女】")
    private Integer sex;

    /** 录入人 */
    @Excel(name = "录入人")
    private String enterMan;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enterTime;

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
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setEnterMan(String enterMan) 
    {
        this.enterMan = enterMan;
    }

    public String getEnterMan() 
    {
        return enterMan;
    }
    public void setEnterTime(Date enterTime) 
    {
        this.enterTime = enterTime;
    }

    public Date getEnterTime() 
    {
        return enterTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("phone", getPhone())
            .append("sex", getSex())
            .append("enterMan", getEnterMan())
            .append("enterTime", getEnterTime())
            .toString();
    }
}
