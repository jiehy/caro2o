package cn.wolfcode.business.report.mapper;


import cn.wolfcode.business.report.qo.ShopInfoQueryObject;
import cn.wolfcode.business.report.vo.ReportShopVO;

import java.util.List;

public interface ReportMapper {
    List<ReportShopVO> selectReportShopList(ShopInfoQueryObject qo);
}

