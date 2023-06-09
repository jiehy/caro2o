import request from '@/utils/request'

// 查询预约信息列表
export function listAppointment(query) {
  return request({
    url: '/business/appointment/list',
    method: 'get',
    params: query
  })
}

// 查询预约信息详细
export function getAppointment(id) {
  return request({
    url: '/business/appointment/' + id,
    method: 'get'
  })
}

// 新增预约信息
export function addAppointment(data) {
  return request({
    url: '/business/appointment',
    method: 'post',
    data: data
  })
}

// 修改预约信息
export function updateAppointment(data) {
  return request({
    url: '/business/appointment',
    method: 'put',
    data: data
  })
}
// 修改状态为已到店
export function arrivalAppointment(id) {
  return request({
    url: '/business/appointment/arrival/' + id,
    method: 'put'
  })
}

// 删除预约信息
export function delAppointment(id) {
  return request({
    url: '/business/appointment/' + id,
    method: 'delete'
  })
}

// 取消预约信息
export function handlerCancel(id) {
  return request({
    url: '/business/appointment/cancel/' + id,
    method: 'put'
  })
}
