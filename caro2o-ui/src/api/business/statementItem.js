import request from '@/utils/request'

// 查询结算单明细列表
export function listStatementItem(query) {
  return request({
    url: '/business/statementItem/list',
    method: 'get',
    params: query
  })
}

// 查询结算单明细详细
export function getStatementItem(id) {
  return request({
    url: '/business/statementItem/' + id,
    method: 'get'
  })
}

// 新增结算单明细
export function addStatementItem(data) {
  return request({
    url: '/business/statementItem',
    method: 'post',
    data: data
  })
}

// 修改结算单明细
export function updateStatementItem(data) {
  return request({
    url: '/business/statementItem',
    method: 'put',
    data: data
  })
}

// 删除结算单明细
export function delStatementItem(id) {
  return request({
    url: '/business/statementItem/' + id,
    method: 'delete'
  })
}

export function payStatement(id) {
  return request({
    url: '/business/statementItem/pay/'+id,
    method: 'put',
  })
}
