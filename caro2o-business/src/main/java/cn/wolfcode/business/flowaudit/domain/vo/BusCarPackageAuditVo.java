package cn.wolfcode.business.flowaudit.domain.vo;

import cn.wolfcode.business.flowaudit.domain.qo.QueryObject;
import lombok.Data;

@Data
public class BusCarPackageAuditVo extends QueryObject {
    /**
     * 套餐审核id
     */
    private Long id;
    /**
     * 审核状态：同意，拒绝
     */
    private boolean auditStatus;
    /**
     * 备注
     */
    private String info;
}
