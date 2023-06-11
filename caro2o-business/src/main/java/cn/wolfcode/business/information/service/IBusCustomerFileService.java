package cn.wolfcode.business.information.service;
import cn.wolfcode.business.information.domain.BusCustomerFile;

import java.util.List;

/**
 * 客户档案Service接口
 * 
 * @author HH
 * @date 2023-06-09
 */
public interface IBusCustomerFileService 
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
     * 批量删除客户档案
     * 
     * @param ids 需要删除的客户档案主键集合
     * @return 结果
     */
    public int deleteBusCustomerFileByIds(Long[] ids);

    /**
     * 删除客户档案信息
     * 
     * @param id 客户档案主键
     * @return 结果
     */
    public int deleteBusCustomerFileById(Long id);
}
