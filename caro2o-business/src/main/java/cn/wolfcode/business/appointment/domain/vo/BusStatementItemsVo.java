package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.business.appointment.domain.BusStatementItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BusStatementItemsVo {
    private List<BusStatementItem> busStatementItems;
    //优惠价格
    private BigDecimal discountPrice;
}