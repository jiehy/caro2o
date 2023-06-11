package cn.wolfcode.business.information.mapper;

import java.util.List;
import cn.wolfcode.business.information.domain.BusCustomerFile;

/**
 * 客户档案Mapper接口
 * 
 * @author HH
 * @date 2023-06-09
 */
public interface BusCustomerFileMapper 
{
    /**
     * 查询客户档案
     * 
     * @param id 客户档案主键
     * @return 客户档案
     */
    public BusCustomerFile selectBusCustomerFileById(Long id);

    /**
     * 查询客户档案列表
     * 
     * @param busCustomerFile 客户档案
     * @return 客户档案集合
     */
    public List<BusCustomerFile> selectBusCustomerFileList(BusCustomerFile busCustomerFile);

    /**
     * 新增客户档案
     * 
     * @param busCustomerFile 客户档案
     * @return 结果
     */
    public int insertBusCustomerFile(BusCustomerFile busCustomerFile);

    /**
     * 修改客户档案
     * 
     * @param busCustomerFile 客户档案
     * @return 结果
     */
    public int updateBusCustomerFile(BusCustomerFile busCustomerFile);

    /**
     * 删除客户档案
     * 
     * @param id 客户档案主键
     * @return 结果
     */
    public int deleteBusCustomerFileById(Long id);

    /**
     * 批量删除客户档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusCustomerFileByIds(Long[] ids);
}