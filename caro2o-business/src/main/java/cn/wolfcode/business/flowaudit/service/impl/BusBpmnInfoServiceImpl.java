package cn.wolfcode.business.flowaudit.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.StringUtils;
import cn.wolfcode.common.utils.file.FileUploadUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.flowaudit.mapper.BusBpmnInfoMapper;
import cn.wolfcode.business.flowaudit.domain.BusBpmnInfo;
import cn.wolfcode.business.flowaudit.service.IBusBpmnInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程定义Service业务层处理
 * 
 * @author cjj
 * @date 2023-06-05
 */
@Service
public class BusBpmnInfoServiceImpl implements IBusBpmnInfoService 
{
    @Autowired
    private BusBpmnInfoMapper busBpmnInfoMapper;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询流程定义
     * 
     * @param id 流程定义主键
     * @return 流程定义
     */
    @Override
    public BusBpmnInfo selectBusBpmnInfoById(Long id)
    {
        return busBpmnInfoMapper.selectBusBpmnInfoById(id);
    }

    /**
     * 查询流程定义列表
     * 
     * @param busBpmnInfo 流程定义
     * @return 流程定义
     */
    @Override
    public List<BusBpmnInfo> selectBusBpmnInfoList(BusBpmnInfo busBpmnInfo)
    {
        return busBpmnInfoMapper.selectBusBpmnInfoList(busBpmnInfo);
    }

    /**
     * 新增流程定义
     * 
     * @param busBpmnInfo 流程定义
     * @return 结果
     */
    @Override
    public int insertBusBpmnInfo(BusBpmnInfo busBpmnInfo)
    {
        return busBpmnInfoMapper.insertBusBpmnInfo(busBpmnInfo);
    }

    /**
     * 修改流程定义
     * 
     * @param busBpmnInfo 流程定义
     * @return 结果
     */
    @Override
    public int updateBusBpmnInfo(BusBpmnInfo busBpmnInfo)
    {
        return busBpmnInfoMapper.updateBusBpmnInfo(busBpmnInfo);
    }

    /**
     * 批量删除流程定义
     * 
     * @param ids 需要删除的流程定义主键
     * @return 结果
     */
    @Override
    public int deleteBusBpmnInfoByIds(Long[] ids)
    {
        return busBpmnInfoMapper.deleteBusBpmnInfoByIds(ids);
    }

    /**
     * 删除流程定义信息
     * 
     * @param id 流程定义主键
     * @return 结果
     */
    @Override
    public int deleteBusBpmnInfoById(Long id)
    {
        return busBpmnInfoMapper.deleteBusBpmnInfoById(id);
    }

    @Override
    @Transactional
    public void deploy(MultipartFile file, String bpmnLabel, Integer bpmnType, String info) throws IOException {
        //参数校验
        if(file == null){
            throw new RuntimeException("上传文件不能为空");
        }
        if(StringUtils.isEmpty(bpmnLabel) ||
                StringUtils.isEmpty(bpmnType+"") ||
                StringUtils.isEmpty(info)){
            throw new RuntimeException("非法参数");
        }
        //校验文件格式是否为 bpmn 格式文件
        String ext = FileUploadUtils.getExtension(file);
        if(!"bpmn".equals(ext)){
            throw new RuntimeException("文件格式必须为 bpmn 格式");
        }
        //把 bpmn 文件部署到 actviti 中
        Deployment deploy = repositoryService.
                createDeployment().
                addInputStream(bpmnLabel, file.getInputStream()).
                deploy();

        //查询流程定义
        ProcessDefinition processDefinition = repositoryService.
                createProcessDefinitionQuery().
                deploymentId(deploy.getId()).singleResult();
        //把 bpmnInfo 信息保存到数据库中
        BusBpmnInfo busBpmnInfo = new BusBpmnInfo();
        busBpmnInfo.setBpmnLabel(bpmnLabel);
        busBpmnInfo.setBpmnType(bpmnType);
        busBpmnInfo.setDeployTime(new Date());
        busBpmnInfo.setInfo(info);
        busBpmnInfo.setProcessDefinitionKey(processDefinition.getKey());
        busBpmnInfo.setVersion(Long.parseLong(processDefinition.getVersion()+""));
        busBpmnInfoMapper.insertBusBpmnInfo(busBpmnInfo);
    }

    @Override
    public void bpmnCancel(Long id) {
        if(StringUtils.isEmpty(id+"")){
            throw new RuntimeException("非法操作");
        }
        //删除 bpmnInfo 信息
        BusBpmnInfo busBpmnInfo = busBpmnInfoMapper.selectBusBpmnInfoById(id);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(busBpmnInfo.getProcessDefinitionKey()).
                processDefinitionVersion(busBpmnInfo.getVersion().intValue()).singleResult();
        /**.
         * TODO: 要查看这个流程定义下是否有流程实例,如果有需要找到流程实例
         * 根据流程实例找到 businessKey , 然后在把数据修改成初始化状态
         */
        busBpmnInfoMapper.deleteBusBpmnInfoById(id);

        //删除流程定义信息
        repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
    }

    @Override
    public InputStream getBpmn(Long id, String type) {
        //参数校验
        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(id+"")){
            throw new  RuntimeException("非法操作");
        }
        BusBpmnInfo busBpmnInfo = busBpmnInfoMapper.selectBusBpmnInfoById(id);
        if(busBpmnInfo == null){
            throw new RuntimeException("非法操作");
        }
        InputStream inputStream = null;
        //判断类型是 xml 还是 png,如果是 xml 响应 xml 如果是图片响应图片
        //查询流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
                processDefinitionKey(busBpmnInfo.getProcessDefinitionKey()).
                processDefinitionVersion(busBpmnInfo.getVersion().intValue()).singleResult();
        if("xml".equals(type)){
            inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());

        }else if("png".equals(type)){
            DefaultProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            /**
             * 第一个参数: 流程定义模型
             * 第二个参数: 高亮节点集合
             * 第三个参数: 高亮连线集合
             */
            inputStream = processDiagramGenerator.generateDiagram(bpmnModel,
                    Collections.emptyList(),
                    Collections.emptyList(),
                    "宋体",
                    "宋体",
                    "宋体");

        }else{
            throw new RuntimeException("非法操作");
        }
        return inputStream;
    }

    @Override
    public BusBpmnInfo queryType(int serviceItemType) {
        BusBpmnInfo busBpmnInfo = busBpmnInfoMapper.selectBusBpmnInfoByqueryType(serviceItemType);
        return busBpmnInfo;
    }
}
