package cn.wolfcode.business.appointment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.domain.info.ServiceItemAuditInfo;
import cn.wolfcode.business.appointment.domain.vo.ServiceItemAuditVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.service.IBusServiceItemService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 服务项Controller
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/business/serviceitem")
public class BusServiceItemController extends BaseController
{
    @Autowired
    private IBusServiceItemService busServiceItemService;

    /**
     * 查询服务项列表
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusServiceItem busServiceItem)
    {
        startPage();
        List<BusServiceItem> list = busServiceItemService.selectBusServiceItemList(busServiceItem);
        return getDataTable(list);
    }

    /**
     * 导出服务项列表
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:export')")
    @Log(title = "服务项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusServiceItem busServiceItem)
    {
        List<BusServiceItem> list = busServiceItemService.selectBusServiceItemList(busServiceItem);
        ExcelUtil<BusServiceItem> util = new ExcelUtil<BusServiceItem>(BusServiceItem.class);
        util.exportExcel(response, list, "服务项数据");
    }

    /**
     * 获取服务项详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busServiceItemService.selectBusServiceItemById(id));
    }

    /**
     * 新增服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:add')")
    @Log(title = "服务项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusServiceItem busServiceItem)
    {
        return toAjax(busServiceItemService.insertBusServiceItem(busServiceItem));
    }

    /**
     * 上架服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:onshelf')")
    @Log(title = "上架", businessType = BusinessType.UPDATE)
    @PutMapping("/onshelf/{id}")
    public AjaxResult OnShelf(@PathVariable Long id)
    {
        return toAjax(busServiceItemService.updateBusServiceItemStatus(id));
    }

    /**
     * 下架服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:offshelf')")
    @Log(title = "上架", businessType = BusinessType.UPDATE)
    @PutMapping("/offshelf/{id}")
    public AjaxResult OffShelf(@PathVariable Long id)
    {
        return toAjax(busServiceItemService.updateBusServiceItemStatus(id));
    }

    /**
     * 删除服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:remove')")
    @Log(title = "服务项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busServiceItemService.deleteBusServiceItemByIds(ids));
    }

    /**
     * 修改服务项
     */
    @PreAuthorize("@ss.hasPermi('business:serviceitem:edit')")
    @Log(title = "服务项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusServiceItem busServiceItem)
    {
        return toAjax(busServiceItemService.updateBusServiceItem(busServiceItem));
    }

    @GetMapping("/audit/{id}")
    @PreAuthorize("@ss.hasPermi('business:serviceItem:audit')")
    public AjaxResult auditInfo(@PathVariable Long id){
        ServiceItemAuditInfo auditInfo = busServiceItemService.auditInfo(id);
        return AjaxResult.success(auditInfo);
    }

    @PutMapping("/audit")
    @PreAuthorize("@ss.hasPermi('business:serviceItem:audit')")
    public AjaxResult startAudit(@RequestBody ServiceItemAuditVo auditVo){
        busServiceItemService.startAudit(auditVo);
        return AjaxResult.success();
    }
}
