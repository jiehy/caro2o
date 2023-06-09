package cn.wolfcode.business.report.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopInfoQueryObject {
    private Integer timeDim;//时间维度
    private int groupDim;//统计维度--结算单或预约单(有没有appointmentId)

    private String getDim() {
        if (timeDim == null){
            return "%Y-%m-%d";
        }else {
            if (timeDim == 1) {
                return "%Y";
            }else if (timeDim == 2) {
                return "%Y-%m";
            }else if (timeDim == 3) {
                return "%x年%v周";
            }else {
                return "%Y-%m-%d";
            }
        }
    }
}
