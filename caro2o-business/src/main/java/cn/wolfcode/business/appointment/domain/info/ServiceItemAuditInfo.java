package cn.wolfcode.business.appointment.domain.info;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.common.core.domain.entity.SysUser;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServiceItemAuditInfo {
    private BusServiceItem serviceItem;
    private List<SysUser> shopOwners;
    private List<SysUser> finances;
    private BigDecimal discountPrice;
}
