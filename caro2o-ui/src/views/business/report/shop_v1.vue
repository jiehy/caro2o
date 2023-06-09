<template>
  <div ref="chart" style="width:100%;height:600px"></div>
</template>
<script>
  import { statisticShop} from "@/api/business/report";
  import * as echarts from 'echarts';
  export default {
    name: "xxx",
    data() {
      return {
      };
    },
    created() {

    },
    mounted(){
      this.initChart()
    },
    methods: {
      initChart(){
        const chart = this.$refs.chart
        let option = {
          title: {
            text: '店铺消费统计报表',
            subtext: '——饼状图',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: '50%',
              data: [
                { value: 1048, name: 'Search Engine' },
                { value: 735, name: 'Direct' },
                { value: 580, name: 'Email' },
                { value: 484, name: 'Union Ads' },
                { value: 300, name: 'Video Ads' }
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        };
        echarts.init(chart).setOption(option);
      }
    }
  };
</script>
