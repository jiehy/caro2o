package cn.wolfcode.business.appointment.service.impl;

import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;
import cn.wolfcode.business.appointment.mapper.BusAppointmentMapper;
import cn.wolfcode.business.appointment.service.IBusAppointmentService;
import cn.wolfcode.business.appointment.uitl.RegexUtils;
import cn.wolfcode.business.appointment.uitl.VehiclePlateNoUtil;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 预约信息Service业务层处理
 * 
 * @author cjj
 * @date 2023-05-28
 */
@Service
public class BusAppointmentServiceImpl implements IBusAppointmentService
{
    @Autowired
    private BusAppointmentMapper busAppointmentMapper;

    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public BusAppointment selectBusAppointmentById(Long id)
    {
        return busAppointmentMapper.selectBusAppointmentById(id);
    }

    /**
     * 查询预约信息列表
     * 
     * @param busAppointment 预约信息
     * @return 预约信息
     */
    @Override
    public List<BusAppointment> selectBusAppointmentList(BusAppointment busAppointment)
    {
        return busAppointmentMapper.selectBusAppointmentList(busAppointment);
    }

    /**
     * 新增预约信息
     * 
     * @param busAppointment 预约信息
     * @return 结果
     */
    @Override
    public int insertBusAppointment(BusAppointmentVO busAppointment)
    {
        //参数非空校验(针对postman接口测试)
        if (busAppointment== null) {
            throw new RuntimeException("添加数据不能为空");
        }
        //手机号码格式校验
        boolean phoneLegal = RegexUtils.isPhoneLegal(busAppointment.getCustomerPhone());
        if (!phoneLegal) {
            throw new RuntimeException("手机号码格式不正确");
        }
        //车牌号校验
        VehiclePlateNoUtil.VehiclePlateNoEnum vehiclePlateNo = VehiclePlateNoUtil.getVehiclePlateNo(busAppointment.getLicensePlate());
        if (vehiclePlateNo == null) {
            throw new RuntimeException("车牌号码不正确");
        }
        //日期合理性校验
        Date nowDate = DateUtils.getNowDate();
        if (!busAppointment.getAppointmentTime().after(nowDate)) {
            throw new IllegalArgumentException("预约时间应该在当前时间之后");
        }
        //设置隐藏字段封装到对象中
        busAppointment.setCreateTime(DateUtils.getNowDate());//通过工具类获取当前时间
        BusAppointment busAppointment1 = new BusAppointment();
        //转化成另外一个对象
//        BeanUtils.copyBeanProp(busAppointment,busAppointment1);//参数顺序(Object dest, Object src)
        BeanUtils.copyProperties(busAppointment,busAppointment1);
        return busAppointmentMapper.insertBusAppointment(busAppointment1);
    }

    /**
     * 修改预约信息
     * 
     * @param busAppointment 预约信息
     * @return 结果
     */
    @Override
    public int updateBusAppointment(BusAppointmentVO busAppointment)
    {
        //参数非空校验(针对postman接口测试)
        if(busAppointment == null) {
            throw new RuntimeException("添加数据不能为空");
        }
        //手机号码格式校验
        boolean phoneLegal = RegexUtils.isPhoneLegal(busAppointment.getCustomerPhone());
        if (!phoneLegal) {
            throw new RuntimeException("手机号码格式不正确");
        }
        //车牌号校验
        VehiclePlateNoUtil.VehiclePlateNoEnum vehiclePlateNo = VehiclePlateNoUtil.getVehiclePlateNo(busAppointment.getLicensePlate());
        if (vehiclePlateNo == null) {
            throw new RuntimeException("车牌号码不正确");
        }
        //日期合理性校验
        Date nowDate = DateUtils.getNowDate();
        if (!busAppointment.getAppointmentTime().after(nowDate)) {
            throw new IllegalArgumentException("预约时间应该在当前时间之后");
        }
        //根据id查询数据是否存在
        BusAppointment appointment = busAppointmentMapper.selectBusAppointmentById(busAppointment.getId());
        if (appointment == null) {
            throw new RuntimeException("非法操作");
        }
        //判断预约状态是否为预约中
        if (!BusAppointment.STATUS_APPOINTMENT.equals(appointment.getId())) {
            throw new IllegalArgumentException("该状态不是预约中,不能操作");
        }
        BusAppointment busAppointment1 = new BusAppointment();
        //转化成另外一个对象
//        BeanUtils.copyBeanProp(busAppointment1,busAppointment);//自定义工具类的方法
        BeanUtils.copyProperties(busAppointment,busAppointment1);//(Object src, Object dest)
        return busAppointmentMapper.updateBusAppointment(busAppointment1);
    }

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的预约信息主键
     * @return 结果
     */
    @Override
    public int deleteBusAppointmentByIds(Long[] ids)
    {
        return busAppointmentMapper.deleteBusAppointmentByIds(ids);
    }

    /**
     * 删除预约信息信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    @Override
    public int deleteBusAppointmentById(Long id){
        if(id == null){
            throw new RuntimeException("非法操作");
        }
        //根据 id 查询数据
        BusAppointment busAppointment = busAppointmentMapper.selectBusAppointmentById(id);
        if(busAppointment == null){
            throw new RuntimeException("非法操作");
        }

        //判断状态必须是预约中或者是取消
        if(!BusAppointment.STATUS_APPOINTMENT.equals(busAppointment.getStatus()) || !BusAppointment.STATUS_CANCEL.equals(busAppointment.getStatus())){
            throw new RuntimeException("该状态不是预约中或是已取消,不能操作");
        }
        return busAppointmentMapper.deleteBusAppointmentById(id);
    }

    /**
     * 修改预约状态为到店
     *
     * @param id 修改的id
     * @return 结果
     */
    @Override
    public int updateBusAppointmentStatus(Long id) {
        //参数非空校验(针对postman接口测试)
        if(id == null) {
            throw new RuntimeException("非法操作");
        }
        //根据id查询数据是否存在
        BusAppointment appointment = busAppointmentMapper.selectBusAppointmentById(id);
        if (appointment == null) {
            throw new RuntimeException("非法操作");
        }
        //判断预约状态是否为预约中
        if (!BusAppointment.STATUS_APPOINTMENT.equals(appointment.getStatus())) {
            throw new IllegalArgumentException("该状态不是预约中,不能操作");
        }
        //修改到店状态的同时也需要将到店时间设置进去(mapper.xml设置或者在实现类使用工具类获取时间)
        return busAppointmentMapper.arrivalShop(id,BusAppointment.STATUS_ARRIVAL);
    }
    @Override
    public int cancel(Long id) {
        //参数校验
        if(id == null){
            throw new RuntimeException("非法操作");
        }
        //判断预约状态是否为预约中
        BusAppointment busAppointment = this.selectBusAppointmentById(id);
        if(!BusAppointment.STATUS_APPOINTMENT.equals(busAppointment.getStatus())){
            throw new RuntimeException("该状态不是预约中,不能操作");
        }
        return busAppointmentMapper.cancel(id,BusAppointment.STATUS_CANCEL);
    }
}

