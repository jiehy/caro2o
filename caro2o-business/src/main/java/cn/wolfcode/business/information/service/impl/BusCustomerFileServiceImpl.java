package cn.wolfcode.business.information.service.impl;

import java.util.List;

import cn.wolfcode.business.information.domain.BusCustomerFile;
import cn.wolfcode.business.information.mapper.BusCustomerFileMapper;
import cn.wolfcode.business.information.service.IBusCustomerFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户档案Service业务层处理
 * 
 * @author HH
 * @date 2023-06-09
 */
@Service
public class BusCustomerFileServiceImpl implements IBusCustomerFileService
{
    @Autowired
    private BusCustomerFileMapper busCustomerFileMapper;

    /**
     * 查询客户档案
     * 
     * @param id 客户档案主键
     * @return 客户档案
     */
    @Override
    public BusCustomerFile selectBusCustomerFileById(Long id)
    {
        return busCustomerFileMapper.selectBusCustomerFileById(id);
    }

    /**
     * 查询客户档案列表
     * 
     * @param busCustomerFile 客户档案
     * @return 客户档案
     */
    @Override
    public List<BusCustomerFile> selectBusCustomerFileList(BusCustomerFile busCustomerFile)
    {
        return busCustomerFileMapper.selectBusCustomerFileList(busCustomerFile);
    }

    /**
     * 新增客户档案
     *
     * @param busCustomerFile 客户档案
     * @return 结果
     */
    @Override
    public int insertBusCustomerFile(BusCustomerFile busCustomerFile)
    {
        return busCustomerFileMapper.insertBusCustomerFile(busCustomerFile);
    }

    /**
     * 修改客户档案
     * 
     * @param busCustomerFile 客户档案
     * @return 结果
     */
    @Override
    public int updateBusCustomerFile(BusCustomerFile busCustomerFile)
    {
        return busCustomerFileMapper.updateBusCustomerFile(busCustomerFile);
    }

    /**
     * 批量删除客户档案
     * 
     * @param ids 需要删除的客户档案主键
     * @return 结果
     */
    @Override
    public int deleteBusCustomerFileByIds(Long[] ids)
    {
        return busCustomerFileMapper.deleteBusCustomerFileByIds(ids);
    }

    /**
     * 删除客户档案信息
     * 
     * @param id 客户档案主键
     * @return 结果
     */
    @Override
    public int deleteBusCustomerFileById(Long id)
    {
        return busCustomerFileMapper.deleteBusCustomerFileById(id);
    }
}
