package cn.wolfcode.business.flowaudit.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import cn.wolfcode.business.flowaudit.domain.BusBpmnInfo;
import cn.wolfcode.business.flowaudit.service.IBusBpmnInfoService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程定义Controller
 * 
 * @author cjj
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/flowaudit/bpmninfo")
public class BusBpmnInfoController extends BaseController
{
    @Autowired
    private IBusBpmnInfoService busBpmnInfoService;

    /**
     * 查询流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:bpmninfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusBpmnInfo busBpmnInfo)
    {
        startPage();
        List<BusBpmnInfo> list = busBpmnInfoService.selectBusBpmnInfoList(busBpmnInfo);
        return getDataTable(list);
    }

    /**
     * 导出流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:bpmninfo:export')")
    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusBpmnInfo busBpmnInfo)
    {
        List<BusBpmnInfo> list = busBpmnInfoService.selectBusBpmnInfoList(busBpmnInfo);
        ExcelUtil<BusBpmnInfo> util = new ExcelUtil<BusBpmnInfo>(BusBpmnInfo.class);
        util.exportExcel(response, list, "流程定义数据");
    }

    /**
     * 获取流程定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:bpmninfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busBpmnInfoService.selectBusBpmnInfoById(id));
    }

    /**
     * 新增流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:bpmninfo:add')")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping("/flow")
    public AjaxResult add(MultipartFile file, String bpmnLabel, Integer bpmnType, String info) throws IOException {
        busBpmnInfoService.deploy(file,bpmnLabel,bpmnType,info);
        return AjaxResult.success();
    }

    /**
     * 修改流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:bpmninfo:edit')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusBpmnInfo busBpmnInfo)
    {
        return toAjax(busBpmnInfoService.updateBusBpmnInfo(busBpmnInfo));
    }

    /**
     * 删除流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowaudit:bpmninfo:remove')")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        busBpmnInfoService.bpmnCancel(id);
        return AjaxResult.success();
    }
    /**
     * 查看流程定义
     */
    @PreAuthorize("@ss.hasPermi('business:bpmnInfo:readResource')")
    @GetMapping("/{type}/{id}")
    public void readResource(HttpServletResponse response,@PathVariable Long id,@PathVariable String type) throws IOException{
        InputStream inputStream = busBpmnInfoService.getBpmn(id, type);
//        FileOutputStream fout=new FileOutputStream("D:\\test.svg");
        IOUtils.copy(inputStream,response.getOutputStream());
    }
}
