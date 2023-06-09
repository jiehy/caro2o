package cn.wolfcode.business.report.service.impl;

import cn.wolfcode.business.flowaudit.domain.BusBpmnInfo;
import cn.wolfcode.business.report.mapper.ReportMapper;
import cn.wolfcode.business.report.qo.ShopInfoQueryObject;
import cn.wolfcode.business.report.service.IReportService;
import cn.wolfcode.business.report.vo.ReportShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportServiceImpl implements IReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public List<ReportShopVO> selectReportShopList(ShopInfoQueryObject qo) {
        List<ReportShopVO> list = reportMapper.selectReportShopList(qo);
        return list;
    }
}
