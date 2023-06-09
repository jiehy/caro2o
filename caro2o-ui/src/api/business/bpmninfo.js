import request from '@/utils/request'

// 查询流程定义列表
export function listBpmninfo(query) {
  return request({
    url: '/flowaudit/bpmninfo/list',
    method: 'get',
    params: query
  })
}

// 查询流程定义详细
export function getBpmninfo(id) {
  return request({
    url: '/flowaudit/bpmninfo/' + id,
    method: 'get'
  })
}

// 新增流程定义
export function addBpmninfo(data) {
  return request({
    url: '/flowaudit/bpmninfo',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateBpmninfo(data) {
  return request({
    url: '/flowaudit/bpmninfo',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delBpmninfo(id) {
  return request({
    url: '/flowaudit/bpmninfo/' + id,
    method: 'delete'
  })
}
export function getBpmnInfoFile(params){
  return request({
    url:`/flowaudit/bpmninfo/${params.type}/${params.id}`,
    method:'get'
  })
}

// 审批流程文件提交
export function deployBpmnInfo(data){
  return request(
    {
      url:"/flowaudit/bpmninfo/flow",
      method: 'post',
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      data:data
    }
  )
}
