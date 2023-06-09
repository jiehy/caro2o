package cn.wolfcode.business.report.service;
import cn.wolfcode.business.report.qo.ShopInfoQueryObject;
import cn.wolfcode.business.report.vo.ReportShopVO;

import java.util.List;

public interface IReportService {
    List<ReportShopVO> selectReportShopList(ShopInfoQueryObject qo);
}
