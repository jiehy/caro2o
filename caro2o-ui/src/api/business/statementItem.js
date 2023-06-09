// 新增结算单
export function addStatementItem(data) {
  return request({
    url: '/business/statementItem',
    method: 'post',
    data: data
  })
}
export function payStatement(id) {
  return request({
    url: '/business/statementItem/pay/'+id,
    method: 'put',
  })
}
