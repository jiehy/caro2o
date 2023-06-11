package cn.wolfcode.business.information.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.information.domain.BusCustomerFile;
import cn.wolfcode.business.information.service.IBusCustomerFileService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
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
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 客户档案Controller
 * 
 * @author HH
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/business/customer/information")
public class BusCustomerFileController extends BaseController
{
    @Autowired
    private IBusCustomerFileService busCustomerFileService;

    /**
     * 查询客户档案列表
     */
    @PreAuthorize("@ss.hasPermi('business/customer:information:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusCustomerFile busCustomerFile)
    {
        startPage();
        List<BusCustomerFile> list = busCustomerFileService.selectBusCustomerFileList(busCustomerFile);
        return getDataTable(list);
    }

    /**
     * 导出客户档案列表
     */
    @PreAuthorize("@ss.hasPermi('business/customer:information:export')")
    @Log(title = "客户档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusCustomerFile busCustomerFile)
    {
        List<BusCustomerFile> list = busCustomerFileService.selectBusCustomerFileList(busCustomerFile);
        ExcelUtil<BusCustomerFile> util = new ExcelUtil<BusCustomerFile>(BusCustomerFile.class);
        util.exportExcel(response, list, "客户档案数据");
    }

    /**
     * 获取客户档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('business/customer:information:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busCustomerFileService.selectBusCustomerFileById(id));
    }

    /**
     * 新增客户档案
     */
    @PreAuthorize("@ss.hasPermi('business/customer:information:add')")
    @Log(title = "客户档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusCustomerFile busCustomerFile)
    {
        return toAjax(busCustomerFileService.insertBusCustomerFile(busCustomerFile));
    }

    /**
     * 修改客户档案
     */
    @PreAuthorize("@ss.hasPermi('business/customer:information:edit')")
    @Log(title = "客户档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCustomerFile busCustomerFile)
    {
        return toAjax(busCustomerFileService.updateBusCustomerFile(busCustomerFile));
    }

    /**
     * 删除客户档案
     */
    @PreAuthorize("@ss.hasPermi('business/customer:information:remove')")
    @Log(title = "客户档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busCustomerFileService.deleteBusCustomerFileByIds(ids));
    }
}
