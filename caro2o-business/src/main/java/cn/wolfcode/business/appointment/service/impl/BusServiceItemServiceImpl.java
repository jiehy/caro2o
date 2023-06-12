package cn.wolfcode.business.appointment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.domain.info.ServiceItemAuditInfo;
import cn.wolfcode.business.appointment.domain.vo.ServiceItemAuditVo;
import cn.wolfcode.business.flowaudit.domain.BusBpmnInfo;
import cn.wolfcode.business.flowaudit.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowaudit.mapper.BusCarPackageAuditMapper;
import cn.wolfcode.business.flowaudit.service.IBusBpmnInfoService;
import cn.wolfcode.common.core.domain.entity.SysUser;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.system.mapper.SysConfigMapper;
import cn.wolfcode.system.mapper.SysUserMapper;
import cn.wolfcode.system.service.ISysConfigService;
import cn.wolfcode.system.service.ISysUserService;
import io.jsonwebtoken.lang.Assert;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.mapper.BusServiceItemMapper;
import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.service.IBusServiceItemService;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeMBeanException;

/**
 * 服务项Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
@Transactional
public class BusServiceItemServiceImpl implements IBusServiceItemService 
{
    @Autowired
    private BusServiceItemMapper busServiceItemMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

//    @Autowired
//    private SysConfigMapper sysConfigMapper;
    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IBusBpmnInfoService busBpmnInfoService;

    @Autowired
   private RuntimeService runtimeService;

    @Autowired
    private BusCarPackageAuditMapper busCarPackageAuditMapper;

    /**
     * 查询服务项
     * 
     * @param id 服务项主键
     * @return 服务项
     */
    @Override
    public BusServiceItem selectBusServiceItemById(Long id)
    {
        return busServiceItemMapper.selectBusServiceItemById(id);
    }

    /**
     * 查询服务项列表
     * 
     * @param busServiceItem 服务项
     * @return 服务项
     */
    @Override
    public List<BusServiceItem> selectBusServiceItemList(BusServiceItem busServiceItem)
    {
        return busServiceItemMapper.selectBusServiceItemList(busServiceItem);
    }

    /**
     * 新增服务项
     * 
     * @param busServiceItem 服务项
     * @return 结果
     */
    @Override
    public int insertBusServiceItem(BusServiceItem busServiceItem)
    {
        busServiceItem.setCreateTime(DateUtils.getNowDate());
        return busServiceItemMapper.insertBusServiceItem(busServiceItem);
    }

    /**
     * 修改服务项
     * 
     * @param busServiceItem 服务项
     * @return 结果
     */
    @Override
    public int updateBusServiceItem(BusServiceItem busServiceItem)
    {
        return busServiceItemMapper.updateBusServiceItem(busServiceItem);
    }

    /**
     * 批量删除服务项
     * 
     * @param ids 需要删除的服务项主键
     * @return 结果
     */
    @Override
    public int deleteBusServiceItemByIds(Long[] ids)
    {
        return busServiceItemMapper.deleteBusServiceItemByIds(ids);
    }

    /**
     * 删除服务项信息
     * 
     * @param id 服务项主键
     * @return 结果
     */
    @Override
    public int deleteBusServiceItemById(Long id)
    {
        return busServiceItemMapper.deleteBusServiceItemById(id);
    }

    @Override
    public ServiceItemAuditInfo auditInfo(Long id) {
        Assert.notNull(id,"参数不合法");
        BusServiceItem busServiceItem = busServiceItemMapper.selectBusServiceItemById(id);
        Assert.notNull(busServiceItem,"参数不合法");
        if (!BusServiceItem.CARPACKAGE_YES.equals(busServiceItem.getCarPackage())) {
            throw new RuntimeException("必须是套餐才可以审核");
        }
        //把数据封装到 vo 对象中
        ServiceItemAuditInfo info = new ServiceItemAuditInfo();
        //判断是否处于初始化或者是重新调整
        if(BusServiceItem.AUDITSTATUS_INIT.equals(busServiceItem.getAuditStatus())||
                BusServiceItem.AUDITSTATUS_REPLY.equals(busServiceItem.getAuditStatus())){
            info.setServiceItem(busServiceItem);//设置该条数据进去
            //查询店长
            List<SysUser> shopOwners = sysUserMapper.selectUserByRoleName("店长");
            info.setShopOwners(shopOwners);
            //查询配置金额限制
            String obj = sysConfigService.selectConfigByKey("discountPriceLimit");
//            sysConfigMapper.checkConfigKeyUnique("discountPriceLimit");//和上面查询的结果一样
            info.setDiscountPrice(new BigDecimal(obj));
            if(busServiceItem.getDiscountPrice().compareTo(new BigDecimal(obj)) > 0){
                //查询财务
                List<SysUser> finances = sysUserMapper.selectUserByRoleName("财务");
                info.setFinances(finances);
            }
        }else {
            throw new RuntimeException("非法操作");
        }
        return info;
    }

    @Override
    @Transactional
    public void startAudit(ServiceItemAuditVo auditVo) {
        //参数校验
        if(auditVo == null){
            throw new RuntimeException("非法操作");
        }
        //根据 id 查询服务单项数据
        BusServiceItem busServiceItem = busServiceItemMapper.selectBusServiceItemById(auditVo.getId());
        if(busServiceItem == null){
            throw new RuntimeException("非法操作");
        }
        //判断是否为套餐
        if(!BusServiceItem.CARPACKAGE_YES.equals(busServiceItem.getCarPackage())){
            throw new RuntimeException("必须为套餐");
        }
        //判断是否为下架状态
        if(!BusServiceItem.SALESTATUS_OFF.equals(busServiceItem.getSaleStatus())){
            throw new RuntimeException("状态必须为下架");
        }
        //判断状态必须为初始化或者重新调整
        if(!(BusServiceItem.AUDITSTATUS_INIT.equals(busServiceItem.getAuditStatus())
                || BusServiceItem.AUDITSTATUS_REPLY.equals(busServiceItem.getAuditStatus()))){
            throw new RuntimeException("状态必须为重新调整或者初始化");
        }
        //查询bpmnInfo 数据
        BusBpmnInfo busBpmnInfo = busBpmnInfoService.queryType(BusBpmnInfo.SERVICE_ITEM_TYPE);
        if(busBpmnInfo == null){
            throw new RuntimeException("先部署流程定义");
        }
        //把数据保存到 CarPackageAudit中
        BusCarPackageAudit carPackageAudit = new BusCarPackageAudit();
        carPackageAudit.setCreateTime(new Date());
        carPackageAudit.setCreatorId(SecurityUtils.getUserId().toString());
        carPackageAudit.setInfo(auditVo.getInfo());
        carPackageAudit.setServiceItemId(busServiceItem.getId());
        carPackageAudit.setServiceItemName(busServiceItem.getName());
        carPackageAudit.setServiceItemInfo(busServiceItem.getInfo());
        carPackageAudit.setServiceItemPrice(busServiceItem.getDiscountPrice());
        carPackageAudit.setStatus(BusCarPackageAudit.STATUS_IN_ROGRESS);
        //设置流程变量
        Map<String,Object> map = new HashMap<>();
        map.put("shopOwnerId",auditVo.getShopOwnerId());
        if(auditVo.getFinanceId() !=null){
            map.put("financeId",auditVo.getFinanceId());
        }
        map.put("disCountPrice",busServiceItem.getDiscountPrice().longValue());
        //启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(busBpmnInfo.getProcessDefinitionKey(), auditVo.getId().toString(), map);
        carPackageAudit.setInstanceId(processInstance.getId());
        //套餐审核信息记录表则新增一条数据
        busCarPackageAuditMapper.insertBusCarPackageAudit(carPackageAudit);
        //服务单项表的状态则修改为审核中
        busServiceItemMapper.changeStatus(auditVo.getId(),BusServiceItem.AUDITSTATUS_AUDITING);
    }

    @Override
    public int updateBusServiceItemStatus(Long id) {
        Assert.notNull(id,"参数不合法");
        BusServiceItem busServiceItem = busServiceItemMapper.selectBusServiceItemById(id);
        Assert.notNull(busServiceItem,"查询的数据为空");
        if (busServiceItem.getSaleStatus().equals(BusServiceItem.SALESTATUS_ON)) {
            busServiceItem.setSaleStatus(BusServiceItem.SALESTATUS_OFF);
            return  busServiceItemMapper.updateBusServiceItem(busServiceItem);//只有为上架状态可以下架
        }else if (busServiceItem.getAuditStatus().equals(BusServiceItem.AUDITSTATUS_APPROVED) ||
        busServiceItem.getAuditStatus().equals(BusServiceItem.AUDITSTATUS_NO_REQUIRED)) {
            busServiceItem.setSaleStatus(BusServiceItem.SALESTATUS_ON);
            return busServiceItemMapper.updateBusServiceItem(busServiceItem);//上架
        }else {
            throw new RuntimeException("状态必须为审核通过或无需审核才可以上下架");
        }

    }
}
/**总结与分析
 * 1、使用sysConfigService.selectConfigByKey()或sysConfigMapper.checkConfigKeyUnique()都可以查询config
 * 区别点：sysConfigService会优先去redis缓存里面查找,没有的话设置key到config对象再去查找，其中是引入了selectConfigVO和sqlwhereSearch
 *      而sysConfigMapper则是直接去数据库查找，该方法SQL语句的where条件同样是configKey,只有后面加了limit 1限制一条数据
 * 2、在sysConfigService和sysConfigMapper的选择上，若缓存有数据一般是选择sysConfigService，若方法在两者都没有实现则在sysConfigMapper实现，
 *     原因是体现责任分离的原则
 */
