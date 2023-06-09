package cn.wolfcode.business.appointment.controller;

import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;
import cn.wolfcode.business.appointment.service.IBusAppointmentService;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 预约信息Controller
 * 
 * @author cjj
 * @date 2023-05-28
 */
@RestController
@RequestMapping("/business/appointment")
public class BusAppointmentController extends BaseController
{
    @Autowired
    private IBusAppointmentService busAppointmentService;

    /**
     * 查询预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusAppointment busAppointment)
    {
        startPage();
        List<BusAppointment> list = busAppointmentService.selectBusAppointmentList(busAppointment);
        return getDataTable(list);
    }

    /**
     * 导出预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:export')")
    @Log(title = "预约信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusAppointment busAppointment)
    {
        List<BusAppointment> list = busAppointmentService.selectBusAppointmentList(busAppointment);
        ExcelUtil<BusAppointment> util = new ExcelUtil<BusAppointment>(BusAppointment.class);
        util.exportExcel(response, list, "预约信息数据");
    }

    /**
     * 获取预约信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busAppointmentService.selectBusAppointmentById(id));
    }

    /**
     * 新增预约信息
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:add')")
    @Log(title = "预约信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusAppointmentVO busAppointment)
    {
        return toAjax(busAppointmentService.insertBusAppointment(busAppointment));
    }

    /**
     * 修改预约信息
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:edit')")
    @Log(title = "预约信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusAppointmentVO busAppointment)
    {
        return toAjax(busAppointmentService.updateBusAppointment(busAppointment));
    }

    /**
     * 删除预约信息
     */
    @PreAuthorize("@ss.hasPermi('business:appointment:remove')")
    @Log(title = "预约信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busAppointmentService.deleteBusAppointmentByIds(ids));
    }

    /**
     * 修改状态为已到店
     */
    @Log(title = "状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/arrival/{id}")
    public AjaxResult arrival(@PathVariable Long id)
    {
        return toAjax(busAppointmentService.updateBusAppointmentStatus(id));
    }
    /**
     * 取消预约
     */
    @Log(title = "取消预约", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{id}")
    public AjaxResult cancel(@PathVariable Long id)
    {
        return toAjax(busAppointmentService.cancel(id));
    }

}
