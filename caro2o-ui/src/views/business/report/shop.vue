<template>
  <el-main>
    <el-row>
      <el-form ref="queryForm" :model="queryParams" :inline="true" >
        <el-form-item label="时间维度" >
          <el-select  placeholder="时间维度"  v-model="queryParams.timeDim"  clearable>
            <el-option
              v-for="dict in statisTimeDim"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="统计类型" >
          <el-select  placeholder="统计类型"   v-model="queryParams.groupDim" clearable>
            <el-option
              v-for="dict in groupDim"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-row>
      <el-col :span="10">
        <el-table v-loading="loading" :data="dataList" >
          <el-table-column label="统计时间" align="center" prop="time" />
          <el-table-column label="订单数据" align="center" prop="count" />
        </el-table>
      </el-col>
      <el-col :span="14">
        <div ref="chart" style="width:100%;height:700px"></div>
      </el-col>
      <el-col :span="24">
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
  </el-main>

</template>
<script>
  import { statisticShop} from "@/api/business/report";
  import * as echarts from 'echarts';
  export default {
    name: "xxx",
    data() {
      return {
        statisTimeDim:[{label:"年",value:1},{label: "月", value:2},{label: "周",value:3}, {label:"日", value:4}],
        groupDim:[{label:"结算单",value:1},{label: "预约单", value:2}],
        // 遮罩层
        loading: true,
        dataList:[],
        // 总条数
        total: 0,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          status: null,
          timeDim:null,
          groupDim:null
        },
        // 表单参数
        form: {},
        chartData:[]
      };
    },
    created() {
      this.getList();
    },
    mounted(){
      this.initChart()
    },
    methods: {
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.queryParams= {
          pageNum: 1,
          pageSize: 10,
          status: null,
          timeDim:null,
          groupDim:null
        }
      },
      getList(){
        statisticShop(this.queryParams).then(resp=>{
          this.dataList = resp.rows;
          this.total = resp.total;
          this.loading = false;

          //处理图形
          this.chartData = [];
          this.dataList.forEach(item=>{
            let data = {
              value:item.count,
              name:item.time
            }
            this.chartData.push(data)
            //初始化barChart
            this.initChart();
          })
        })
      },
      initChart(){
        const chart = this.$refs.chart;
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
            left: 'right'
          },
          series: [
            {
              type: 'pie',
              radius: '50%',
              data: this.chartData,
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
