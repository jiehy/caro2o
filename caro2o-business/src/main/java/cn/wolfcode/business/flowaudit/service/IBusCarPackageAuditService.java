package cn.wolfcode.business.flowaudit.service;

import java.io.InputStream;
import java.util.List;
import cn.wolfcode.business.flowaudit.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowaudit.domain.vo.BusCarPackageAuditVo;
import cn.wolfcode.business.flowaudit.domain.vo.HistoryCommentInfo;

/**
 * 套餐审核Service接口
 * 
 * @author cjj
 * @date 2023-06-05
 */
public interface IBusCarPackageAuditService 
{
    /**
     * 查询套餐审核
     * 
     * @param id 套餐审核主键
     * @return 套餐审核
     */
    public BusCarPackageAudit selectBusCarPackageAuditById(Long id);

    /**
     * 查询套餐审核列表
     * 
     * @param busCarPackageAudit 套餐审核
     * @return 套餐审核集合
     */
    public List<BusCarPackageAudit> selectBusCarPackageAuditList(BusCarPackageAudit busCarPackageAudit);

    /**
     * 新增套餐审核
     * 
     * @param busCarPackageAudit 套餐审核
     * @return 结果
     */
    public int insertBusCarPackageAudit(BusCarPackageAudit busCarPackageAudit);

    /**
     * 修改套餐审核
     * 
     * @param busCarPackageAudit 套餐审核
     * @return 结果
     */
    public int updateBusCarPackageAudit(BusCarPackageAudit busCarPackageAudit);

    /**
     * 批量删除套餐审核
     * 
     * @param ids 需要删除的套餐审核主键集合
     * @return 结果
     */
    public int deleteBusCarPackageAuditByIds(Long[] ids);

    /**
     * 删除套餐审核信息
     * 
     * @param id 套餐审核主键
     * @return 结果
     */
    public int deleteBusCarPackageAuditById(Long id);

    void cancel(Long id);

    InputStream process(Long id);

    void audit(BusCarPackageAuditVo auditVo);

    List<HistoryCommentInfo> listHistory(Long instanceId);

    List<BusCarPackageAudit> doneQuery(BusCarPackageAuditVo auditVo);
}
