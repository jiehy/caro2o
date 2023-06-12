package cn.wolfcode.business.appointment.service;

import java.util.List;
import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.domain.info.ServiceItemAuditInfo;
import cn.wolfcode.business.appointment.domain.vo.ServiceItemAuditVo;

/**
 * 服务项Service接口
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public interface IBusServiceItemService 
{
    /**
     * 查询服务项
     * 
     * @param id 服务项主键
     * @return 服务项
     */
    public BusServiceItem selectBusServiceItemById(Long id);

    /**
     * 查询服务项列表
     * 
     * @param busServiceItem 服务项
     * @return 服务项集合
     */
    public List<BusServiceItem> selectBusServiceItemList(BusServiceItem busServiceItem);

    /**
     * 新增服务项
     * 
     * @param busServiceItem 服务项
     * @return 结果
     */
    public int insertBusServiceItem(BusServiceItem busServiceItem);

    /**
     * 修改服务项
     * 
     * @param busServiceItem 服务项
     * @return 结果
     */
    public int updateBusServiceItem(BusServiceItem busServiceItem);

    /**
     * 批量删除服务项
     * 
     * @param ids 需要删除的服务项主键集合
     * @return 结果
     */
    public int deleteBusServiceItemByIds(Long[] ids);

    /**
     * 删除服务项信息
     * 
     * @param id 服务项主键
     * @return 结果
     */
    public int deleteBusServiceItemById(Long id);

    ServiceItemAuditInfo auditInfo(Long id);

    void startAudit(ServiceItemAuditVo auditVo);

    int updateBusServiceItemStatus(Long id);
}
