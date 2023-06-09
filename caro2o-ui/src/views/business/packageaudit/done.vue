<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="操作时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.package_audit_status"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['flowaudit:packageaudit:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="packageauditList" @selection-change="handleSelectionChange">
      <el-table-column label="套餐名称" align="center" prop="serviceItemName" />
      <el-table-column label="套餐价格" align="center" prop="serviceItemPrice" />
      <el-table-column label="套餐备注" align="center" prop="serviceItemInfo" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.package_audit_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="listHistory(scope.row.instanceId)"
          >审批历史</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="viewProcess(scope.row.id)"
          >进度查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!--查看历史流程图-->
    <el-dialog
      title="流程进度图"
      :visible.sync="processDialog.open"
      width="1200px"
      append-to-body>
      <div v-html="processDialog.img"></div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeProcessDialog">关 闭</el-button>
      </div>
    </el-dialog>
    <!--查看审批历史-->
    <el-dialog title="审批历史" :visible.sync="historyDialog.open" width="1200px" append-to-body>
      <el-table ref="tables" v-loading="historyDialog.loading" :data="historyDialog.list" :default-sort="defaultSort">
        <el-table-column label="任务名称" align="center" prop="taskName"/>
        <el-table-column label="开始时间" align="center" prop="startTime" width="180"/>
        <el-table-column label="结束时间" align="center" prop="endTime" width="180" :show-overflow-tooltip="true"/>
        <el-table-column label="耗时" align="center" prop="durationInMillis" width="180"/>
        <el-table-column label="审批意见" align="center" prop="comment" width="180"/>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listPackageaudit, getPackageaudit, delPackageaudit, addPackageaudit, updatePackageaudit,
    listTodo,listDone,carPackageAudit,carPackageAuditHistory,carPackageAuditProcess
  } from "@/api/business/packageaudit";
  export default {
    name: "Packageaudit",
    dicts: ['package_audit_status'],
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 套餐审核表格数据
        packageauditList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          status: null,
          createTime: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        },
        // 审批历史弹窗
        historyDialog: {
          open: false, // 显示状态
          loading: false, // 加载状态
          list: [], // 列表数据
        },
        // 流程进度图弹窗
        processDialog: {
          open: false,
          img: '',
        },
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询套餐审核列表 */
      getList() {
        this.loading = true;
        listDone(this.queryParams).then(
          (response) => {
            this.list = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          serviceItemId: null,
          serviceItemName: null,
          serviceItemInfo: null,
          serviceItemPrice: null,
          instanceId: null,
          creatorId: null,
          info: null,
          status: null,
          createTime: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加套餐审核";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getPackageaudit(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改套餐审核";
        });
      },
      /** 提交按钮 */
      submitAuditForm() {
        this.$refs['auditForm'].validate(valid =>{
          if(!valid) return;
          carPackageAudit(this.auditDialog.data).then(res=>{
            this.auditDialog.open = false;
            this.getList();
            this.$modal.msgSuccess("审批成功");
          }).catch(() => {});;
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除套餐审核编号为"' + ids + '"的数据项？').then(function() {
          return delPackageaudit(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('flowaudit/packageaudit/export', {
          ...this.queryParams
        }, `packageaudit_${new Date().getTime()}.xlsx`)
      },
      handleAudit(id) {
        this.resetForm("auditForm");
        this.auditDialog.data={};
        this.auditDialog.open=true;
        this.auditDialog.data.id= id;
      },
      listHistory(instanceId){
        this.historyDialog.open = true
        this.historyDialog.loading = true
        // 请求审批历史接口的方法
        carPackageAuditHistory(instanceId).then((res) => {
          this.historyDialog.list = res.rows;
          this.historyDialog.loading = false;
        });
      },
      // 查看进度
      viewProcess(id) {
        this.processDialog.open = true;
        carPackageAuditProcess(id).then((res) => {
          this.processDialog.img = res;
        });
      }
    },
  };
</script>
