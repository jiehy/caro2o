<template>
  <div>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="customer.status ? 24 : 16" class="height-handle">
            <!-- 客户信息 -->
            <el-row :span="24" class="customer_info">
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">客户姓名: </el-col>
                  <el-col :span="12">{{ customer.customerName }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">联系方式: </el-col>
                  <el-col :span="12">{{ customer.customerPhone }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">车牌号码：</el-col>
                  <el-col :span="12">{{ customer.licensePlate }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">汽车类型：</el-col>
                  <el-col :span="12">{{ customer.carSeries }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">服务类型：</el-col>
                  <el-col :span="12">
                    <dict-tag
                      :options="dict.type.service_type"
                      :value="customer.serviceType"
                    >
                    </dict-tag>
                  </el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">到店时间：</el-col>
                  <el-col :span="12">{{ customer.actualArrivalTime }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">总消费金额：</el-col>
                  <el-col :span="12" v-if="customer.status">{{
                    customer.totalAmount
                    }}</el-col>
                  <el-col :span="12" v-else>{{ totalSum }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">实付价格： </el-col>
                  <el-col :span="12" v-if="customer.status"
                  >{{ customer.totalAmount - customer.discountAmount }}
                  </el-col>
                  <el-col :span="12" v-else>{{ actualPrice }}</el-col>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="form-group">
                  <el-col :span="12">优惠价格：</el-col>
                  <el-col :span="12" v-if="customer.status">{{
                    customer.discountAmount
                    }}</el-col>
                  <el-col :span="12" v-else>
                    <el-input
                      size="mini"
                      v-model="discountAmount"
                      :min="0"
                      :max="totalSum"
                      @blur="discountAmountHandle"
                    >
                    </el-input>
                  </el-col>
                </div>
              </el-col>
            </el-row>
            <!-- 服务项列表 -->
            <el-row :span="24" class="service_item_box">
              <el-row :gutter="10" class="mb8">
                <el-col :span="6" v-if="!customer.status">
                  <el-button
                    type="primary"
                    plain
                    icon="el-icon-plus"
                    size="mini"
                    @click="save"
                  >保存</el-button
                  >
                  <el-button
                    type="warning"
                    plain
                    icon="el-icon-plus"
                    size="mini"
                    @click="pay"
                  >支付</el-button
                  >
                </el-col>
                <right-toolbar @queryTable="getDetail"></right-toolbar>
              </el-row>
              <el-table :data="consumptionList" style="width: 100%">
                <el-table-column prop="itemName" label="服务名称">
                </el-table-column>
                <el-table-column prop="itemPrice" label="服务价格">
                </el-table-column>
                <el-table-column prop="itemQuantity" label="数量">
                </el-table-column>
                <el-table-column
                  label="操作"
                  align="center"
                  v-if="!customer.status"
                >
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      icon="el-icon-plus"
                      type="primary"
                      @click="plusHandle(scope.$index)"
                    >
                    </el-button>
                    <el-button
                      size="mini"
                      icon="el-icon-minus"
                      type="danger"
                      @click="minusHandle(scope.$index)"
                    >
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>

              <div class="page">
                显示第 1 到第 {{ consumptionList.length }} 条记录，总共
                {{ consumptionList.length }} 条记录
              </div>
            </el-row>
          </el-col>
          <el-col :span="8" v-if="!customer.status" class="height-handle">
            <!-- 服务项搜索 -->
            <el-row>
              <el-col :span="24" class="serach_box">
                <el-form ref="form" :model="queryParams" label-width="100px">
                  <el-form-item label="名称：">
                    <el-input v-model="queryParams.name" clearable></el-input>
                  </el-form-item>
                  <el-form-item label="是否套餐：">
                    <el-select v-model="queryParams.carPackage" clearable>
                      <el-option
                        v-for="dict in dict.type.service_item_car_package"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="服务分类：">
                    <el-select v-model="queryParams.serviceCatalog" clearable>
                      <el-option
                        v-for="dict in dict.type.service_type"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item>
                    <el-button
                      type="success"
                      icon="el-icon-search"
                      round
                      @click="search"
                    >搜索</el-button
                    >
                  </el-form-item>
                </el-form>
              </el-col>
            </el-row>
            <!-- 服务项显示 -->
            <el-row>
              <el-col :span="24" class="service_list_box">
                <el-table :data="serviceItemList" style="width: 100%">
                  <el-table-column prop="name" label="服务名称">
                  </el-table-column>
                  <el-table-column prop="originalPrice" label="服务价格">
                  </el-table-column>
                  <el-table-column label="操作">
                    <template slot-scope="scope">
                      <el-button
                        size="mini"
                        icon="el-icon-plus"
                        type="primary"
                        @click="serviceItemPlus(scope.row)"
                      ></el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <div class="page">
                  显示第 1 到第 {{ serviceItemList.length }} 条记录，总共
                  {{ serviceItemList.length }} 条记录
                </div>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>
<script>
  // import { getStatement } from "@/api/business/statement";
  // import { addStatementItem,listStatementItem,payStatement} from "@/api/business/statementItem";
  // import { listServiceItem } from "@/api/business/serviceItem";
  export default {
    name: "StatementItem",
    dicts: ["service_type", "service_item_car_package"],
    data() {
      return {
        //优惠价格
        discountAmount: 0,
        //结算单Id
        id: this.$route.query.id,
        // 用户信息
        customer: {},
        // 查询参数
        queryParams: {
          saleStatus: 1,
          pageNum: 1,
          pageSize: 5,
          name: undefined,
          carPackage: undefined,
          serviceCatalog: undefined,
        },
        //消费参数
        itemQueryParams: {
          statementId: this.$route.query.id,
          pageNum: 1,
          pageSize: 5,
        },
        //已消费总数
        total: 0,
        //消费服务项
        serviceItemList: [],
        //消费服务项
        consumptionList: [],
      };
    },
    created() {
      this.getDetail();

    },
    methods: {
      getDetail() {

        // 获取服务单项列表
        listServiceItem(this.queryParams).then((response) => {
          this.serviceItemList = response.rows;
        });
        // 消费列表
        listStatementItem(this.itemQueryParams).then((response) => {
          this.total = response.total;
          this.consumptionList = response.rows;
        });
        // 用户信息
        getStatement(this.id).then((response) => {
          this.discountAmount = response.data.discountAmount;
          this.customer = response.data;
          //处理服务类类型显示问题
          // this.customer.serviceCatalog=this.customer.serviceCatalog.toString();
        });
      },
      //搜索按钮
      search() {
        // 获取服务单项列表
        listServiceItem(this.queryParams).then((response) => {
          this.serviceItemList = response.rows;
        });
      },
      //服务单项添加按钮
      serviceItemPlus(row) {
        let idx = this.searchServiceItemIndex(row);
        if (idx != -1) {
          //更新数量
          this.consumptionList[idx].itemQuantity++;
        } else {
          //添加服务项
          this.consumptionList.push(this.serviceItem(row));
        }
      },
      //服务项是否已经添加服务列表了
      searchServiceItemIndex(row) {
        let idx = -1;
        if (this.consumptionList.length < 1) {
          return -1;
        }
        this.consumptionList.forEach((item, index) => {
          if (item.itemId == row.id) {
            idx = index;
            return;
          }
        });
        return idx;
      },
      //服务单项数据处理
      serviceItem(row) {
        return {
          statementId: this.id,
          itemId: row.id,
          itemName: row.name,
          itemPrice: row.originalPrice,
          itemQuantity: 1,
        };
      },
      // 服务项目数量增加按钮操作
      plusHandle(index) {
        this.consumptionList[index].itemQuantity++;
      },
      //服务项目数量减按钮操作
      minusHandle(index) {
        if (this.consumptionList[index].itemQuantity <= 1) {
          this.consumptionList.splice(index, 1);
          return;
        }
        this.consumptionList[index].itemQuantity--;
        // 减去商品后，实际价格为负数
        this.actualPriceHandle();
      },
      //保存服务选项按钮
      save() {
        let data={
          "busStatementItems":this.consumptionList,
          "discountPrice":this.discountAmount
        }
        addStatementItem(data).then(response=>{
          this.$modal.msgSuccess(response.msg);
          this.getDetail();
        })
      },
      //支付按钮操作
      pay() {
        payStatement(this.id).then(response=>{
          this.$modal.msgSuccess(response.msg);
          this.getDetail();
        })
      },
      //优惠价格
      discountAmountHandle() {
        if (this.discountAmount > this.totalSum || this.discountAmount < 0) {
          this.$alert("输入优惠价格有误", {
            confirmButtonText: "确定",
            callback: (action) => {
              this.discountAmount = 0;
            },
          });
        }
      },
      actualPriceHandle() {
        if (this.actualPrice < 0) {
          this.$alert("实际价格为负数,重置优惠价格", {
            confirmButtonText: "确定",
            callback: (action) => {
              this.discountAmount = 0;
            },
          });
        }
      },
    },
    computed: {
      //实时计算总价格
      totalSum() {
        let total = 0;
        this.consumptionList.forEach((item) => {
          total += item.itemPrice * item.itemQuantity;
        });
        return total;
      },
      // 真实价格处理:总价格减去折扣价
      actualPrice() {
        return this.totalSum - this.discountAmount;
      },
    },
  };
</script>

<style lang="scss" scoped>
  .customer_info {
    background-color: #fff;
    border-radius: 6px;
    padding-top: 5px;
    padding-bottom: 13px;
    box-shadow: 1px 1px 3px rgb(0 0 0 / 20%);
    padding-left: 45px;
    color: #333;
    font-size: 14px;

    .form-group {
      line-height: 25px;
    }
  }

  .service_item_box {
    background: #fff;
    border-radius: 6px;
    margin-top: 10px;
    box-shadow: 1px 1px 3px rgb(0 0 0 / 20%);
    padding: 15px;
  }

  .serach_box {
    width: 100%;
    background: #fff;
    border-radius: 6px;
    margin-left: 15px;
    padding-top: 5px;
    box-shadow: 1px 1px 3px rgb(0 0 0 / 20%);

    .el-input {
      max-width: 217px;
    }
  }

  .service_list_box {
    width: 100%;
    background: #fff;
    border-radius: 6px;
    margin-top: 10px;
    padding: 15px;
    box-shadow: 1px 1px 3px rgb(0 0 0 / 20%);
    margin-left: 15px;
  }

  .page {
    line-height: 34px;
    font-size: 13px;
    color: #676a6c;
  }

  .el-form-item {
    margin-bottom: 15px;
  }
</style>
