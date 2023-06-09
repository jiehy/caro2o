import request from '@/utils/request'

//消费单
export function statisticShop(params) {
  return request({
    url: '/report/shop',
    method: 'get',
    params
  })
}

//手机号码，消费金额
export function statisticCustomer(params) {
  return request({
    url: '/report/customer',
    method: 'get',
    params
  })
}
