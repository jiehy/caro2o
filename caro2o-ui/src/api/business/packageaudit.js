import request from '@/utils/request'

// 查询套餐审核列表
export function listPackageaudit(query) {
  return request({
    url: '/flowaudit/packageaudit/list',
    method: 'get',
    params: query
  })
}

// 查询套餐审核详细
export function getPackageaudit(id) {
  return request({
    url: '/flowaudit/packageaudit/' + id,
    method: 'get'
  })
}

// 新增套餐审核
export function addPackageaudit(data) {
  return request({
    url: '/flowaudit/packageaudit',
    method: 'post',
    data: data
  })
}

// 修改套餐审核
export function updatePackageaudit(data) {
  return request({
    url: '/flowaudit/packageaudit',
    method: 'put',
    data: data
  })
}

// 删除套餐审核
export function delPackageaudit(id) {
  return request({
    url: '/flowaudit/packageaudit/' + id,
    method: 'delete'
  })
}


// 我的待办
export function listTodo(params){
  return request({
    url: '/flowaudit/packageaudit/todo',
    method: 'get',
    params
  })
}

// 审核
export function carPackageAudit(data){
  return request({
    url: '/flowaudit/packageaudit/audit',
    method: 'post',
    data
  })
}

// 审批历史
export function carPackageAuditHistory(instanceId) {
  return request({
    url: '/flowaudit/packageaudit/history/' + instanceId,
    method: 'get',
  })
}

// 查看进度
export function carPackageAuditProcess(id) {
  return request({
    url: '/flowaudit/packageaudit/process/' + id,
    method: 'get',
  })
}

// 我的已办
export function listDone(params) {
  return request({
    url: '/flowaudit/packageaudit/done',
    method: 'get',
    params
  })
}
// 撤销套餐审核
export function cancelCarPackageAudit(id){
  return request({
    url: '/flowaudit/packageaudit/cancel/'+id,
    method: 'put'
  })
}
