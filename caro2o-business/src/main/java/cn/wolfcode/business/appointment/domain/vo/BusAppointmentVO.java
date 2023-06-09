package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BusAppointmentVO {
    /** id */
    private Long id;
    /** 客户姓名 */
    private String customerName;
    /** 客户联系方式 */
    private String customerPhone;
    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date appointmentTime;
    /** 车牌号码 */
    private String licensePlate;
    /** 汽车类型 */
    private String carSeries;
    /** 服务类型【维修0/保养1】 */
    private Long serviceType;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;
    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;
    /** 状态【预约中0/已到店1/用户取消2/超时取消3/已结算4/已支付5】 */
    private Integer status;
}
