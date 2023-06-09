package cn.wolfcode.business.flowaudit.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.flowaudit.domain.vo.BusCarPackageAuditVo;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.business.flowaudit.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowaudit.service.IBusCarPackageAuditService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 套餐审核Controller
 * 
 * @author cjj
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/flowaudit/packageaudit")
public class BusCarPackageAuditController extends BaseController
{
    @Autowired
    private IBusCarPackageAuditService busCarPackageAuditService;

    /**
     * 查询套餐审核列表
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:packageaudit:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusCarPackageAudit busCarPackageAudit)
    {
        startPage();
        List<BusCarPackageAudit> list = busCarPackageAuditService.selectBusCarPackageAuditList(busCarPackageAudit);
        return getDataTable(list);
    }

    /**
     * 导出套餐审核列表
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:packageaudit:export')")
    @Log(title = "套餐审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusCarPackageAudit busCarPackageAudit)
    {
        List<BusCarPackageAudit> list = busCarPackageAuditService.selectBusCarPackageAuditList(busCarPackageAudit);
        ExcelUtil<BusCarPackageAudit> util = new ExcelUtil<BusCarPackageAudit>(BusCarPackageAudit.class);
        util.exportExcel(response, list, "套餐审核数据");
    }

    /**
     * 获取套餐审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:packageaudit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busCarPackageAuditService.selectBusCarPackageAuditById(id));
    }

    /**
     * 新增套餐审核
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:packageaudit:add')")
    @Log(title = "套餐审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusCarPackageAudit busCarPackageAudit)
    {
        return toAjax(busCarPackageAuditService.insertBusCarPackageAudit(busCarPackageAudit));
    }

    /**
     * 修改套餐审核
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:packageaudit:edit')")
    @Log(title = "套餐审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCarPackageAudit busCarPackageAudit)
    {
        return toAjax(busCarPackageAuditService.updateBusCarPackageAudit(busCarPackageAudit));
    }

    /**
     * 删除套餐审核
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:packageaudit:remove')")
    @Log(title = "套餐审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busCarPackageAuditService.deleteBusCarPackageAuditByIds(ids));
    }

    /**
     * 撤销审核
     * @param id
     * @return
     */
    @PutMapping("/cancel/{id}")
    @PreAuthorize("@ss.hasPermi('business:carPackageAudit:cancel')")
    public AjaxResult cancelApply(@PathVariable Long id) {
        busCarPackageAuditService.cancel(id);
        return AjaxResult.success();
    }

    /**
     * 查看审核
     * @param id
     * @return
     */
    @GetMapping("/process/{id}")
    public void readResource(HttpServletResponse response,@PathVariable Long id) throws IOException{
        InputStream inputStream = busCarPackageAuditService.process(id);
        IOUtils.copy(inputStream,response.getOutputStream());
    }

    @Log(title = "套餐审核", businessType = BusinessType.OTHER)
    @PostMapping("/audit")
    public AjaxResult audit(@RequestBody BusCarPackageAuditVo auditVo)
    {
        busCarPackageAuditService.audit(auditVo);
        return AjaxResult.success();
    }

    /**
     * 审批历史
     * @param instanceId
     * @return
     */
    @GetMapping("/history/{instanceId}")
    @PreAuthorize("@ss.hasPermi('business:carPackageAudit:history')")
    public TableDataInfo listHistory(@PathVariable Long instanceId) {
        startPage();
        return getDataTable(busCarPackageAuditService.listHistory(instanceId));
    }

    /**
     * 我的已办
     *
     * @param auditVo
     * @return
     */
    @GetMapping("/done")
    @PreAuthorize("@ss.hasPermi('business:carPackageAudit:done')")
    public TableDataInfo doneQuery(BusCarPackageAuditVo auditVo) {
        List<BusCarPackageAudit> carPackageAudits = busCarPackageAuditService.doneQuery(auditVo);
        List<String> list = new ArrayList<>();
        list.add("抱歉没有查询到数据");
        if (carPackageAudits == null) {
            return getDataTable(list);
        }
        return getDataTable(carPackageAudits);
    }
}
