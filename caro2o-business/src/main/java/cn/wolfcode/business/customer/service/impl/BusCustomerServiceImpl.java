package cn.wolfcode.business.customer.service.impl;

import java.util.Date;
import java.util.List;

import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.mapper.BusAppointmentMapper;
import cn.wolfcode.business.appointment.mapper.BusStatementMapper;
import cn.wolfcode.business.customer.domain.BusCustomer;
import cn.wolfcode.business.customer.mapper.BusCustomerMapper;
import cn.wolfcode.business.customer.service.IBusCustomerService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 客户信息Service业务层处理
 * 
 * @author cjj
 * @date 2023-06-10
 */
@Service
public class BusCustomerServiceImpl implements IBusCustomerService
{
    @Autowired
    private BusCustomerMapper busCustomerMapper;
    @Autowired
    private BusAppointmentMapper busAppointmentMapper;
    @Autowired
    private BusStatementMapper statementMapper;

    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public BusCustomer selectBusCustomerById(Long id)
    {
        return busCustomerMapper.selectBusCustomerById(id);
    }

    /**
     * 查询客户信息列表
     * 
     * @param busCustomer 客户信息
     * @return 客户信息
     */
    @Override
    public List<BusCustomer> selectBusCustomerList(BusCustomer busCustomer)
    {
        return busCustomerMapper.selectBusCustomerList(busCustomer);
    }

    /**
     * 新增客户信息
     * 
     * @param busCustomer 客户信息
     * @return 结果
     */
    @Override
    public int insertBusCustomer(BusCustomer busCustomer)
    {
        Assert.notNull(busCustomer,"参数不合法");
        Assert.notNull(busCustomer.getPhone(),"手机号码不能为空");
        String phone = busCustomer.getPhone();
        List<BusAppointment> busAppointments = busAppointmentMapper.selectBusAppointmentByPhone(phone);
        List<BusStatement> statements = statementMapper.selectBusStatementByPhone(phone);
        if (busAppointments.size() == 0 || statements.size() == 0) {
            busCustomer.setEnterMan("admin");
            busCustomer.setEnterTime(new Date());
        }else {
            for (BusAppointment busAppointment : busAppointments) {
                for (BusStatement statement : statements) {
                    if (busAppointment.getLicensePlate().equals(statement.getLicensePlate())) {
                        busCustomer.setId(statement.getId());
                        busCustomer.setCustomerName(statement.getCustomerName());
                        busCustomer.setPhone(statement.getCustomerPhone());
                        busCustomer.setEnterMan("admin");
                        busCustomer.setEnterTime(statement.getActualArrivalTime());
                        busCustomerMapper.insertBusCustomer(busCustomer);
                    }else {
                        busCustomer.setId(busAppointment.getId());
                        busCustomer.setCustomerName(busAppointment.getCustomerName());
                        busCustomer.setPhone(busAppointment.getCustomerPhone());
                        busCustomer.setEnterMan("admin");
                        busCustomer.setEnterTime(busAppointment.getAppointmentTime());
                        busCustomerMapper.insertBusCustomer(busCustomer);
                    }
                }
            }
            throw new RuntimeException("预约单或结算单存在数据已经为您拉取过来，请刷新页面！");
        }
        return busCustomerMapper.insertBusCustomer(busCustomer);
    }

    /**
     * 修改客户信息
     * 
     * @param busCustomer 客户信息
     * @return 结果
     */
    @Override
    public int updateBusCustomer(BusCustomer busCustomer)
    {
        return busCustomerMapper.updateBusCustomer(busCustomer);
    }

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteBusCustomerByIds(Long[] ids)
    {
        return busCustomerMapper.deleteBusCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteBusCustomerById(Long id)
    {
        return busCustomerMapper.deleteBusCustomerById(id);
    }
}
