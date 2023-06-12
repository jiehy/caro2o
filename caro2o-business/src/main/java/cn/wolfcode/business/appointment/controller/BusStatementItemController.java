package cn.wolfcode.business.appointment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.domain.vo.BusStatementItemsVo;
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
import cn.wolfcode.business.appointment.domain.BusStatementItem;
import cn.wolfcode.business.appointment.service.IBusStatementItemService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 结算单明细Controller
 * 
 * @author cjj
 * @date 2023-06-11
 */
@RestController
@RequestMapping("/business/statementItem")
public class BusStatementItemController extends BaseController
{
    @Autowired
    private IBusStatementItemService busStatementItemService;

    /**
     * 查询结算单明细列表
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusStatementItem busStatementItem)
    {
        startPage();
        List<BusStatementItem> list = busStatementItemService.selectBusStatementItemList(busStatementItem);
        return getDataTable(list);
    }

    /**
     * 导出结算单明细列表
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:export')")
    @Log(title = "结算单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusStatementItem busStatementItem)
    {
        List<BusStatementItem> list = busStatementItemService.selectBusStatementItemList(busStatementItem);
        ExcelUtil<BusStatementItem> util = new ExcelUtil<BusStatementItem>(BusStatementItem.class);
        util.exportExcel(response, list, "结算单明细数据");
    }

    /**
     * 获取结算单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busStatementItemService.selectBusStatementItemById(id));
    }

    /**
     * 新增结算单明细
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:add')")
    @Log(title = "结算单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusStatementItemsVo vo)
    {
        busStatementItemService.insertBusStatementItem(vo);
        return AjaxResult.success();
    }

    /**
     * 修改结算单明细
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:edit')")
    @Log(title = "结算单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusStatementItem busStatementItem)
    {
        return toAjax(busStatementItemService.updateBusStatementItem(busStatementItem));
    }
    /**
     * 支付结算单明细
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:pay')")
    @Log(title = "结算单明细", businessType = BusinessType.UPDATE)
    @PutMapping("/pay/{id}")
    public AjaxResult pay(@PathVariable Long id)
    {
        return toAjax(busStatementItemService.pay(id));//该id为结算单Id,通过this.$route.query.id赋值的
    }
    /**
     * 删除结算单明细
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:remove')")
    @Log(title = "结算单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busStatementItemService.deleteBusStatementItemByIds(ids));
    }
}
