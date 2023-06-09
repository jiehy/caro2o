package cn.wolfcode.business.appointment.service;

import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;

import java.util.List;

/**
 * 预约信息Service接口
 * 
 * @author cjj
 * @date 2023-05-28
 */
public interface IBusAppointmentService 
{
    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    public BusAppointment selectBusAppointmentById(Long id);

    /**
     * 查询预约信息列表
     * 
     * @param busAppointment 预约信息
     * @return 预约信息集合
     */
    public List<BusAppointment> selectBusAppointmentList(BusAppointment busAppointment);

    /**
     * 新增预约信息
     * 
     * @param busAppointment 预约信息
     * @return 结果
     */
    public int insertBusAppointment(BusAppointmentVO busAppointment);

    /**
     * 修改预约信息
     * 
     * @param busAppointment 预约信息
     * @return 结果
     */
    public int updateBusAppointment(BusAppointmentVO busAppointment);

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的预约信息主键集合
     * @return 结果
     */
    public int deleteBusAppointmentByIds(Long[] ids);

    /**
     * 删除预约信息信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteBusAppointmentById(Long id);

    /**
     * 修改预约状态
     *
     * @param id 修改的id
     * @return 结果
     */
    public int updateBusAppointmentStatus(Long id);

    /**
     * 取消预约
     *
     * @param id 取消的id
     * @return 结果
     */
    public int cancel(Long id);
}
