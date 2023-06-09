package cn.wolfcode.business.flowaudit.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.mapper.BusServiceItemMapper;
import cn.wolfcode.business.flowaudit.domain.BusBpmnInfo;
import cn.wolfcode.business.flowaudit.domain.vo.BusCarPackageAuditVo;
import cn.wolfcode.business.flowaudit.domain.vo.HistoryCommentInfo;
import cn.wolfcode.business.flowaudit.mapper.BusBpmnInfoMapper;
import cn.wolfcode.common.utils.DateUtils;
import cn.wolfcode.common.utils.PageUtils;
import cn.wolfcode.common.utils.SecurityUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.flowaudit.mapper.BusCarPackageAuditMapper;
import cn.wolfcode.business.flowaudit.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowaudit.service.IBusCarPackageAuditService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 套餐审核Service业务层处理
 * 
 * @author cjj
 * @date 2023-06-05
 */
@Service
public class BusCarPackageAuditServiceImpl implements IBusCarPackageAuditService 
{
    @Autowired
    private BusCarPackageAuditMapper busCarPackageAuditMapper;
    @Autowired
    private BusServiceItemMapper itemMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private BusBpmnInfoMapper busBpmnInfoMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     * 查询套餐审核
     * 
     * @param id 套餐审核主键
     * @return 套餐审核
     */
    @Override
    public BusCarPackageAudit selectBusCarPackageAuditById(Long id)
    {
        return busCarPackageAuditMapper.selectBusCarPackageAuditById(id);
    }

    /**
     * 查询套餐审核列表
     * 
     * @param busCarPackageAudit 套餐审核
     * @return 套餐审核
     */
    @Override
    public List<BusCarPackageAudit> selectBusCarPackageAuditList(BusCarPackageAudit busCarPackageAudit)
    {
        return busCarPackageAuditMapper.selectBusCarPackageAuditList(busCarPackageAudit);
    }

    /**
     * 新增套餐审核
     * 
     * @param busCarPackageAudit 套餐审核
     * @return 结果
     */
    @Override
    public int insertBusCarPackageAudit(BusCarPackageAudit busCarPackageAudit)
    {
        busCarPackageAudit.setCreateTime(DateUtils.getNowDate());
        return busCarPackageAuditMapper.insertBusCarPackageAudit(busCarPackageAudit);
    }

    /**
     * 修改套餐审核
     * 
     * @param busCarPackageAudit 套餐审核
     * @return 结果
     */
    @Override
    public int updateBusCarPackageAudit(BusCarPackageAudit busCarPackageAudit)
    {
        return busCarPackageAuditMapper.updateBusCarPackageAudit(busCarPackageAudit);
    }

    /**
     * 批量删除套餐审核
     * 
     * @param ids 需要删除的套餐审核主键
     * @return 结果
     */
    @Override
    public int deleteBusCarPackageAuditByIds(Long[] ids)
    {
        return busCarPackageAuditMapper.deleteBusCarPackageAuditByIds(ids);
    }

    /**
     * 删除套餐审核信息
     * 
     * @param id 套餐审核主键
     * @return 结果
     */
    @Override
    public int deleteBusCarPackageAuditById(Long id)
    {
        return busCarPackageAuditMapper.deleteBusCarPackageAuditById(id);
    }

    @Override
    public void cancel(Long id) {
        //参数校验
        if(id == null){
            throw new RuntimeException("非法操作");
        }

        //根据 id 查询数据
        BusCarPackageAudit carPackageAudit = busCarPackageAuditMapper.selectBusCarPackageAuditById(id);
        if(!BusCarPackageAudit.STATUS_IN_ROGRESS.equals(carPackageAudit.getStatus())){
            throw new RuntimeException("非法操作");
        }

        //修改 carPackageAudit 状态
        busCarPackageAuditMapper.updateStatus(id,BusCarPackageAudit.STATUS_CANCEL);
        //修改 item 状态
        itemMapper.changeStatus(carPackageAudit.getServiceItemId(),BusServiceItem.AUDITSTATUS_INIT);
        //删除流程实例
        runtimeService.deleteProcessInstance(carPackageAudit.getInstanceId(),"123");
    }
    @Override
    public InputStream process(Long id) {
        //参数校验
        if(id == null){
            throw new RuntimeException("非法操作");
        }
        //根据 id 查询数据
        BusCarPackageAudit carPackageAudit = busCarPackageAuditMapper.selectBusCarPackageAuditById(id);
        if(carPackageAudit == null){
            throw new RuntimeException("非法操作");
        }
        //查询流程定义信息记录表是否有数据
        BusBpmnInfo busBpmnInfo = busBpmnInfoMapper.selectBusBpmnInfoByqueryType(BusBpmnInfo.SERVICE_ITEM_TYPE);
        //获取流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
                processDefinitionKey(busBpmnInfo.getProcessDefinitionKey()).
                processDefinitionVersion(Integer.parseInt(busBpmnInfo.getVersion() + "")).singleResult();


        List<String> activeActivityIds = new ArrayList<>();
        //查询到的套餐审核记录信息状态是否为审核中
        if(carPackageAudit.getStatus().equals(BusCarPackageAudit.STATUS_IN_ROGRESS)){
            activeActivityIds = runtimeService.getActiveActivityIds(carPackageAudit.getInstanceId());
        }else{
            activeActivityIds = Collections.emptyList();
        }
        //流程图生成器对象processDiagramGenerator
        DefaultProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        /**
         * 第一个参数: 流程定义模型
         * 第二个参数: 高亮节点集合
         * 第三个参数: 高亮连线集合
         */
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel,
                activeActivityIds,
                Collections.emptyList(),
                "宋体",
                "宋体",
                "宋体");
        return inputStream;
    }

    @Override
    @Transactional
    public void audit(BusCarPackageAuditVo vo) {
        //参数校验
        if(vo == null){
            throw new RuntimeException("非法操作");
        }
        //根据 id 查询 carPackgeAudit
        BusCarPackageAudit carPackageAudit = busCarPackageAuditMapper.selectBusCarPackageAuditById(vo.getId());
        if(carPackageAudit == null){
            throw new RuntimeException("非法操作");
        }
        //判断状态是否为审批中
        if(!BusCarPackageAudit.STATUS_IN_ROGRESS.equals(carPackageAudit.getStatus())){
            throw new RuntimeException("非法操作");
        }
        //通过流程实例获取到当前任务节点
        Task currentTask = taskService.
                createTaskQuery().
                processInstanceId(carPackageAudit.getInstanceId()).
                singleResult();
        //添加批注
        String message = "";
        if(vo.isAuditStatus()){
            message ="审批人" +  SecurityUtils.getUsername() + "同意-审批意见[" + vo.getInfo() + "]";
        }else{
            message ="审批人" +  SecurityUtils.getUsername() + "同意-审批拒绝[" + vo.getInfo() + "]";
        }

        taskService.addComment(currentTask.getId(),carPackageAudit.getInstanceId(),message);

        // 设置环境变量
        Map<String,Object> map = new HashMap<>();
        map.put("shopOwner",Boolean.valueOf(vo.isAuditStatus()));

        //调用activiti api 去完成任务
        taskService.complete(currentTask.getId(),map);
        //判断是同意还是拒绝
        if(vo.isAuditStatus()){
            //如果是同意, 看是否有下一个节点, 如果没有下一个节点,修改状态为同意状态和单项状态为同意状态
            Task nextTask = taskService.
                    createTaskQuery().
                    processInstanceId(carPackageAudit.getInstanceId()).
                    singleResult();
            //如果有不做任何事情
            if(nextTask == null){
                //如果是拒绝,修改 carPackageAudit 状态为拒绝状态和单项状态为拒绝状态
                busCarPackageAuditMapper.updateStatus(vo.getId(),BusCarPackageAudit.STATUS_PASS);
                itemMapper.changeStatus(carPackageAudit.getServiceItemId(), BusServiceItem.AUDITSTATUS_APPROVED);
            }
        }else{
            //如果是拒绝,修改 carPackageAudit 状态为拒绝状态和单项状态为拒绝状态
            busCarPackageAuditMapper.updateStatus(vo.getId(),BusCarPackageAudit.STATUS_REJECT);
            itemMapper.changeStatus(carPackageAudit.getServiceItemId(), BusServiceItem.AUDITSTATUS_REPLY);
        }
    }


    @Override
    public List<HistoryCommentInfo> listHistory(Long instanceId) {
        //参数校验
        if(instanceId == null){
            throw new RuntimeException("非法操作");
        }

        //查询流程定义信息记录表是否有数据
        BusBpmnInfo busBpmnInfo = busBpmnInfoMapper.selectBusBpmnInfoByqueryType(BusBpmnInfo.SERVICE_ITEM_TYPE);
        //根据流程实例 id 查询历史任务表
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().
                processInstanceId(instanceId + "").
//                taskAssignee(SecurityUtils.getUserId() + "").
        processDefinitionKey(busBpmnInfo.getProcessDefinitionKey()).
                        finished().
                        list();

        List<HistoryCommentInfo> vos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (HistoricTaskInstance task : list) {
            HistoryCommentInfo vo = new HistoryCommentInfo();
            vo.setTaskName(task.getName());
            vo.setStartTime(sdf.format(task.getStartTime()));
            vo.setEndTime(sdf.format(task.getEndTime()));
            vo.setDurationInMillis(task.getDurationInMillis()/1000+"s");
            List<Comment> comments = taskService.getTaskComments(task.getId());
            if(comments.size()>0){
                vo.setComment(comments.get(0).getFullMessage());
            }
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<BusCarPackageAudit> doneQuery(BusCarPackageAuditVo auditVo) {
        //查询流程定义信息记录表是否有数据
        BusBpmnInfo busBpmnInfo = busBpmnInfoMapper.selectBusBpmnInfoByqueryType(BusBpmnInfo.SERVICE_ITEM_TYPE);
        if(busBpmnInfo == null){
            throw new RuntimeException("非法操作");
        }
        //查询待办任务
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().
                taskAssignee(SecurityUtils.getUserId().toString()).
                processDefinitionKey(busBpmnInfo.getProcessDefinitionKey()).list();

        if(list == null || list.size() == 0){
            return null;
        }
        //根据任务查询流程实例 id集合
        List<String> keys = new ArrayList<>();
        for (HistoricTaskInstance task : list) {
            HistoricProcessInstance historicProcessInstance = historyService.
                    createHistoricProcessInstanceQuery().
                    processInstanceId(task.getProcessInstanceId()).
                    singleResult();
            String businessKey = historicProcessInstance.getBusinessKey();
            keys.add(businessKey);
        }
        //根据流程实例 id 查询,businesskey 结合
        PageUtils.startPage();
        //根据 userId 去查询
        List<BusCarPackageAudit> carPackageAudits = busCarPackageAuditMapper.queryBusinessKeys(keys);
        if (carPackageAudits == null) {
            return Collections.emptyList();
        }
        return carPackageAudits;
    }
}
