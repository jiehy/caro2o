package cn.wolfcode.business.appointment.service.impl;

import java.math.BigDecimal;
import java.util.List;

import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.domain.vo.BusStatementItemsVo;
import cn.wolfcode.business.appointment.mapper.BusAppointmentMapper;
import cn.wolfcode.business.appointment.mapper.BusStatementMapper;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.StringUtils;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.mapper.BusStatementItemMapper;
import cn.wolfcode.business.appointment.domain.BusStatementItem;
import cn.wolfcode.business.appointment.service.IBusStatementItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 结算单明细Service业务层处理
 * 
 * @author cjj
 * @date 2023-06-11
 */
@Service
public class BusStatementItemServiceImpl implements IBusStatementItemService 
{
    @Autowired
    private BusStatementItemMapper busStatementItemMapper;
    @Autowired
    private BusStatementMapper statementMapper;

    @Autowired
    private BusAppointmentMapper appointmentMapper;

    /**
     * 查询结算单明细
     * 
     * @param id 结算单明细主键
     * @return 结算单明细
     */
    @Override
    public BusStatementItem selectBusStatementItemById(Long id)
    {
        return busStatementItemMapper.selectBusStatementItemById(id);
    }

    /**
     * 查询结算单明细列表
     * 
     * @param busStatementItem 结算单明细
     * @return 结算单明细
     */
    @Override
    public List<BusStatementItem> selectBusStatementItemList(BusStatementItem busStatementItem)
    {

        return busStatementItemMapper.selectBusStatementItemList(busStatementItem);
    }

    /**
     * 新增结算单明细
     * 
     * @param vo 结算单明细
     * @return 结果
     */
    @Override
    @Transactional
    public void insertBusStatementItem(BusStatementItemsVo vo)
    {
        //1.做参数校验
        if(vo == null){
            throw new RuntimeException("非法操作");
        }
        if(vo.getDiscountPrice() == null){
            throw new RuntimeException("非法操作");
        }
        if(vo.getBusStatementItems()==null){
            throw new RuntimeException("非法操作");
        }
        //2.获取结算单 id,根据结算单 id 查询数据
        List<BusStatementItem> busStatementItems = vo.getBusStatementItems();
        BusStatementItem item = busStatementItems.get(0);//statementId都是相同的，获取哪个都一样，一般是第一个
        Long statementId = item.getStatementId();
        BusStatement busStatement = statementMapper.selectBusStatementById(statementId);
        if(busStatement == null){
            throw new RuntimeException("非法操作");
        }
        //3.判断状态是否有消费中
        if(!BusStatement.STATUS_CONSUME.equals(busStatement.getStatus())){
            throw new RuntimeException("状态必须为消费中状态");
        }
        //4 根据结算单 id 去查询是否有明细,如果有就删除
        List<BusStatementItem> items = busStatementItemMapper.selectByStatementId(statementId);
        if(!StringUtils.isEmpty(items)){
            busStatementItemMapper.deleteByStatementId(statementId);
        }
        BigDecimal totalAmount =  new BigDecimal("0");
        BigDecimal totalQuantity = new BigDecimal("0");
        //5.循环遍历
        for (BusStatementItem statementItem : busStatementItems) {
            //5.1.计算总金额(还没有设置进去busStatement结算单对象中)
            totalAmount = totalAmount.add(statementItem.getItemPrice().multiply(new BigDecimal(statementItem.getItemQuantity())));
            //5.2.计算总数量
            totalQuantity = totalQuantity.add(new BigDecimal(statementItem.getItemQuantity()));
            //5.3.插入明细
            busStatementItemMapper.insertBusStatementItem(statementItem);
        }
        //6.判断折扣金额不能大于总金额
        int count = vo.getDiscountPrice().compareTo(totalAmount);
        if(count>0){
            throw new RuntimeException("折扣金额不能大于总金额");
        }
        //7.更新结算单,总金额,总数量,折扣金额
        busStatement.setTotalAmount(totalAmount);
        busStatement.setTotalQuantity(totalQuantity);
        busStatement.setDiscountAmount(vo.getDiscountPrice());
        statementMapper.updateBusStatement(busStatement);
    }
    /**
     * 修改结算单明细
     * 
     * @param busStatementItem 结算单明细
     * @return 结果
     */
    @Override
    public int updateBusStatementItem(BusStatementItem busStatementItem)
    {
        return busStatementItemMapper.updateBusStatementItem(busStatementItem);
    }

    /**
     * 批量删除结算单明细
     * 
     * @param ids 需要删除的结算单明细主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementItemByIds(Long[] ids)
    {
        return busStatementItemMapper.deleteBusStatementItemByIds(ids);
    }

    /**
     * 删除结算单明细信息
     * 
     * @param id 结算单明细主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementItemById(Long id)
    {
        return busStatementItemMapper.deleteBusStatementItemById(id);
    }

    @Override
    public int pay(Long id) {
        Assert.notNull(id,"参数不合法");
        BusStatement statement = statementMapper.selectBusStatementById(id);
        Assert.notNull(statement,"数据为空");
        //判断状态必须为消费中
        if(!BusStatement.STATUS_CONSUME.equals(statement.getStatus())){
            throw new RuntimeException("状态必须消费中");
        }
        //修改收款人, 收款时间 ,状态
        Long userId = SecurityUtils.getUserId();
        statementMapper.updatePayStatus(id,userId,BusStatement.STATUS_PAID);
        //判断是否是养修预约生成的结算单, 如果是修改状态为已支付状态
        if(statement.getAppointmentId() != null) {
            appointmentMapper.updatePayStatus(statement.getAppointmentId(), BusAppointment.STATUS_PAYED);
        }
        return 0;
    }
}
