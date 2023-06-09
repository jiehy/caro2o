package cn.wolfcode.business.appointment.domain.vo;

import lombok.Data;

@Data
public class ServiceItemAuditVo {
    /**
     * 服务项id
     */
    private Long id;
    /**
     * 店长id
     */
    private Long shopOwnerId;
    /**
     * 财务id
     */
    private Long financeId;
    /**
     * 备注
     */
    private String info;
}

