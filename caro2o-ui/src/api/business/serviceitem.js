import request from '@/utils/request'

// 查询服务项列表
export function listServiceitem(query) {
  return request({
    url: '/business/serviceitem/list',
    method: 'get',
    params: query
  })
}

// 查询服务项详细
export function getServiceitem(id) {
  return request({
    url: '/business/serviceitem/' + id,
    method: 'get'
  })
}

// 新增服务项
export function addServiceitem(data) {
  return request({
    url: '/business/serviceitem',
    method: 'post',
    data: data
  })
}

// 修改服务项
export function updateServiceitem(data) {
  return request({
    url: '/business/serviceitem',
    method: 'put',
    data: data
  })
}

// 上架服务项
export function OnshelfServiceitem(id) {
  return request({
    url: `/business/serviceitem/onshelf/${id}`,
    method: 'put'
  })
}

// 下架服务项
export function OffshelfServiceitem(id) {
  return request({
    url: `/business/serviceitem/offshelf/${id}`,
    method: 'put'
  })
}
//发起审核按钮操作
export function serviceItemAuditInfo(params){
  return request({
    url: '/business/serviceitem/audit/' +params.id,
    method: 'get'
  });
}
//审核提交操作
export function serviceItemStartAudit(params) {
  return request({
    url: '/business/serviceitem/audit',
    method: 'put',
    data: params
  })
}
