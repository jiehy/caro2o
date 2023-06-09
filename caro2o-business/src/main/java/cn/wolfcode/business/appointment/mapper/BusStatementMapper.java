package cn.wolfcode.business.appointment.mapper;

import java.util.List;
import cn.wolfcode.business.appointment.domain.BusStatement;

/**
 * 结算单Mapper接口
 * 
 * @author cjj
 * @date 2023-05-31
 */
public interface BusStatementMapper 
{
    /**
     * 查询结算单
     * 
     * @param id 结算单主键
     * @return 结算单
     */
    public BusStatement selectBusStatementById(Long id);

    /**
     * 查询结算单列表
     * 
     * @param busStatement 结算单
     * @return 结算单集合
     */
    public List<BusStatement> selectBusStatementList(BusStatement busStatement);

    /**
     * 新增结算单
     * 
     * @param busStatement 结算单
     * @return 结果
     */
    public int insertBusStatement(BusStatement busStatement);

    /**
     * 修改结算单
     * 
     * @param busStatement 结算单
     * @return 结果
     */
    public int updateBusStatement(BusStatement busStatement);

    /**
     * 删除结算单
     * 
     * @param id 结算单主键
     * @return 结果
     */
    public int deleteBusStatementById(Long id);

    /**
     * 批量删除结算单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusStatementByIds(Long[] ids);
}
