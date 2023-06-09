package cn.wolfcode.business.report.controller;

import cn.wolfcode.business.flowaudit.domain.BusBpmnInfo;
import cn.wolfcode.business.report.qo.ShopInfoQueryObject;
import cn.wolfcode.business.report.service.IReportService;
import cn.wolfcode.business.report.vo.ReportShopVO;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report/shop")
public class ReportController extends BaseController {
    @Autowired
    private IReportService reportService;

    /**
     * 查询店铺消费信息统计报表
     */
    @GetMapping
    public TableDataInfo list(ShopInfoQueryObject qo)
    {
        startPage();
        List<ReportShopVO> list = reportService.selectReportShopList(qo);
        return getDataTable(list);
    }
}
