<template>
  <el-container>
    <!--工具栏-->
    <el-row>
      <el-menu>
        <el-menu-item index="1">
          <!--用户信息-->
          <span style="margin-top: 22px;">{{user}}</span><span style="margin-top: 10px;cursor: pointer;"><el-avatar icon="el-icon-user-solid"></el-avatar></span>
        </el-menu-item>
      </el-menu>
    </el-row>
  </el-container>
</template>
<script>
  import Cookies from 'js-cookie';
  export default {
    name: "headBar",
    data() {
      return {
        user: sessionStorage.getItem("user")
      }
    },
    methods: {},
    mounted() {
    }
  }
</script>
