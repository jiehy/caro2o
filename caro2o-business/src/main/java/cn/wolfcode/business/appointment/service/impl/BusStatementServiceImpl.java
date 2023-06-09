package cn.wolfcode.business.appointment.service.impl;

import java.util.List;
import cn.wolfcode.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.mapper.BusStatementMapper;
import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.service.IBusStatementService;

/**
 * 结算单Service业务层处理
 * 
 * @author cjj
 * @date 2023-05-31
 */
@Service
public class BusStatementServiceImpl implements IBusStatementService 
{
    @Autowired
    private BusStatementMapper busStatementMapper;

    /**
     * 查询结算单
     * 
     * @param id 结算单主键
     * @return 结算单
     */
    @Override
    public BusStatement selectBusStatementById(Long id)
    {
        return busStatementMapper.selectBusStatementById(id);
    }

    /**
     * 查询结算单列表
     * 
     * @param busStatement 结算单
     * @return 结算单
     */
    @Override
    public List<BusStatement> selectBusStatementList(BusStatement busStatement)
    {
        return busStatementMapper.selectBusStatementList(busStatement);
    }

    /**
     * 新增结算单
     * 
     * @param busStatement 结算单
     * @return 结果
     */
    @Override
    public int insertBusStatement(BusStatement busStatement)
    {
        busStatement.setCreateTime(DateUtils.getNowDate());
        return busStatementMapper.insertBusStatement(busStatement);
    }

    /**
     * 修改结算单
     * 
     * @param busStatement 结算单
     * @return 结果
     */
    @Override
    public int updateBusStatement(BusStatement busStatement)
    {
        return busStatementMapper.updateBusStatement(busStatement);
    }

    /**
     * 批量删除结算单
     * 
     * @param ids 需要删除的结算单主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementByIds(Long[] ids)
    {
        return busStatementMapper.deleteBusStatementByIds(ids);
    }

    /**
     * 删除结算单信息
     * 
     * @param id 结算单主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementById(Long id)
    {
        return busStatementMapper.deleteBusStatementById(id);
    }
}
