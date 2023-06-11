package cn.wolfcode.business.customer.service;

import cn.wolfcode.business.customer.domain.BusCustomer;

import java.util.List;


/**
 * 客户信息Service接口
 * 
 * @author cjj
 * @date 2023-06-10
 */
public interface IBusCustomerService 
{
    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    public BusCustomer selectBusCustomerById(Long id);

    /**
     * 查询客户信息列表
     * 
     * @param busCustomer 客户信息
     * @return 客户信息集合
     */
    public List<BusCustomer> selectBusCustomerList(BusCustomer busCustomer);

    /**
     * 新增客户信息
     * 
     * @param busCustomer 客户信息
     * @return 结果
     */
    public int insertBusCustomer(BusCustomer busCustomer);

    /**
     * 修改客户信息
     * 
     * @param busCustomer 客户信息
     * @return 结果
     */
    public int updateBusCustomer(BusCustomer busCustomer);

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键集合
     * @return 结果
     */
    public int deleteBusCustomerByIds(Long[] ids);

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    public int deleteBusCustomerById(Long id);
}
