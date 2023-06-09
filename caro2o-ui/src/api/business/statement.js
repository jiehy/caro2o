import request from '@/utils/request'

// 查询结算单列表
export function listStatement(query) {
  return request({
    url: '/business/statement/list',
    method: 'get',
    params: query
  })
}

// 查询结算单详细
export function getStatement(id) {
  return request({
    url: '/business/statement/' + id,
    method: 'get'
  })
}

// 新增结算单
export function addStatement(data) {
  return request({
    url: '/business/statement',
    method: 'post',
    data: data
  })
}

// 修改结算单
export function updateStatement(data) {
  return request({
    url: '/business/statement',
    method: 'put',
    data: data
  })
}

// 删除结算单
export function delStatement(id) {
  return request({
    url: '/business/statement/' + id,
    method: 'delete'
  })
}
