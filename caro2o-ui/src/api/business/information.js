import request from '@/utils/request'

// 查询客户档案列表
export function listInformation(query) {
  return request({
    url: '/business/customer/information/list',
    method: 'get',
    params: query
  })
}

// 查询客户档案详细
export function getInformation(id) {
  return request({
    url: '/business/customer/information/' + id,
    method: 'get'
  })
}

// 新增客户档案
export function addInformation(data) {
  return request({
    url: '/business/customer/information',
    method: 'post',
    data: data
  })
}

// 修改客户档案
export function updateInformation(data) {
  return request({
    url: '/business/customer/information',
    method: 'put',
    data: data
  })
}

// 删除客户档案
export function delInformation(id) {
  return request({
    url: '/business/customer/information/' + id,
    method: 'delete'
  })
}
