package cn.wolfcode.business.appointment.mapper;

import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约信息Mapper接口
 * 
 * @author cjj
 * @date 2023-05-28
 */
public interface BusAppointmentMapper 
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
    public int insertBusAppointment(BusAppointment busAppointment);

    /**
     * 修改预约信息
     * 
     * @param busAppointment 预约信息
     * @return 结果
     */
    public int updateBusAppointment(BusAppointment busAppointment);

    /**
     * 删除预约信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteBusAppointmentById(Long id);

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusAppointmentByIds(Long[] ids);

    /**
     * 修改预约状态
     *
     * @param id,status 修改的id和status
     * @return 结果
     */
    public int arrivalShop(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 取消预约状态
     *
     * @param id,status 取消的id和status
     * @return 结果
     */
    public int cancel(@Param("id") Long id, @Param("status") Integer status);

    List<BusAppointment> selectBusAppointmentByPhone(String phone);

    void updatePayStatus(@Param("id") Long id, @Param("status") Integer status);
}
