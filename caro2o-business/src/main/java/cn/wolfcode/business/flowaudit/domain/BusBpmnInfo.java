package cn.wolfcode.business.flowaudit.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 流程定义对象 bus_bpmn_info
 * 
 * @author cjj
 * @date 2023-06-05
 */
@Getter
@Setter
public class BusBpmnInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public static final int SERVICE_ITEM_TYPE = 2;//审核的流程类型为套餐审核

    /** 主键 */
    private Long id;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String bpmnLabel;

    /** 流程类型 */
    @Excel(name = "流程类型")
    private Integer bpmnType;

    /** activity流程定义生成的key */
    @Excel(name = "activity流程定义生成的key")
    private String processDefinitionKey;

    /** 部署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "部署时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deployTime;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long version;

    /** 描述信息 */
    @Excel(name = "描述信息")
    private String info;
}
