import request from '@/utils/request'

// 查询客户信息列表
export function listFile(query) {
  return request({
    url: '/customer/file/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息详细
export function getFile(id) {
  return request({
    url: '/customer/file/' + id,
    method: 'get'
  })
}

// 新增客户信息
export function addFile(data) {
  return request({
    url: '/customer/file',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateFile(data) {
  return request({
    url: '/customer/file',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delFile(id) {
  return request({
    url: '/customer/file/' + id,
    method: 'delete'
  })
}
