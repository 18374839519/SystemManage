<template>
  <el-container :style="'height:'+fullHeight+'px;'">
    <el-main>
      <el-row class="sysTitle"><span>mango后台管理系统</span></el-row>
      <el-form class="loginForm" :model="loginInfo" :rules="loginRules" ref="loginForm" :size="size" label-width="100px" :v-loading="loading">
        <p class="sysLoginTitle">系统登录</p>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名:" prop="userName">
              <el-input v-model="loginInfo.userName" placeholder="请输入用户名" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="密码:" prop="password">
              <el-input v-model="loginInfo.password" placeholder="请输入密码" show-password></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="验证码:" prop="captcha">
              <el-input v-model="loginInfo.captcha" placeholder="请输入验证码" style="width: 160px;float: left;" clearable></el-input>
              <img src="../assets/images/kaptcha.jpg" style="width: 110px; height: 27px;border-radius: 3px;cursor: pointer;" alt="" title="点击图片刷新" @click="refreshImg"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="buttonRow">
          <el-button :size="size" type="primary" class="el-icon-refresh" @click="reSet"> 重置 </el-button>
          <el-button :size="size" type="primary" class="el-icon-success" @click="login('loginForm')"> 登录 </el-button>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>
<script>
  import { mapMutations } from 'vuex';
  export default {
    name: 'Login',
    data() {
      return {
        fullHeight: document.documentElement.clientHeight,
        size: 'mini',
        loading: false,
        loginInfo: {
          userName: '',
          password: '',
          captcha: '',
          captcha2: ''
        },
        loginRules: {
          userName: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          captcha: [
            {required: true, message: '请输入验证码', trigger: 'blur'},
            {validator:this.checkCaptcha, trigger: 'blur'}
          ]
        },
      }
    },
    watch: {
      fullHeight (val) {//监控浏览器高度变化
        if(!this.timer) {
          this.fullHeight = val;
          this.timer = true;
          let that = this;
          setTimeout(function (){
            that.timer = false
          },400)
        }

      }
    },
    methods: {
      ...mapMutations(['changeLogin']),
      //动态获取浏览器高度
      get_bodyHeight () {
        const _this = this;
        window.onresize = () => {
          return (() => {
            window.fullHeight = document.documentElement.clientHeight;
            _this.fullHeight = window.fullHeight
          })()
        }
      },

      // 获取验证码
      getCaptcha() {
        let _this = this;
        _this.$api.getCaptcha().then((res) => {
          if (res.data.code === 200) {
            _this.loginInfo.captcha2 = res.data.data;
          }
        });
      },

      // 刷新验证码
      refreshImg() {
        let _this = this;
        _this.getCaptcha();
      },

      // 登录
      login(formName) {
        let _this = this;
        _this.$refs[formName].validate((validate) => {
          if (validate) {
            let params = {
              account: _this.loginInfo.userName,
              password: _this.loginInfo.password,
              captcha: _this.loginInfo.captcha
            };

            _this.loading = true;
            _this.$api.login(params).then((res) => {
              _this.loading = false;
              if (res.data.code === 200) {
                _this.userToken = res.data.data.token;
                // 将用户token保存到vuex中
                _this.changeLogin({ Authorization: _this.userToken });
                sessionStorage.setItem("user", _this.loginInfo.userName); // 保存用户到本地会话
                _this.$router.push("/Home"); // 登录成功，跳转到主页
              } else {
                _this.$message({
                  type: "warning",
                  message: res.data.msg
                });
              }
            });
          }
        });
      },

      // 重置
      reSet() {
        let _this = this;
        _this.$refs.loginForm.resetFields();
      },

      // 验证码校验
      checkCaptcha(rule, value, callback) {
        let _this = this;
        if (_this.loginInfo.captcha.toLowerCase() !== _this.loginInfo.captcha2.toLowerCase()) {
          return callback(new Error('验证码错误'));
        }
        callback();
      }
    },
    mounted() {
      let _this = this;
      _this.get_bodyHeight(); // 主页面高度=浏览器高度
      _this.getCaptcha();
    }
  }
</script>
<style scoped>
  * {
    padding: 0;
    margin: 0;
  }

  .sysTitle {
    height: 250px;
    padding-top: 150px;
    padding-left: 0;
    font-size: 24px;
    font-weight: bold;
  }

  .loginForm .el-row {
    padding-left: 160px;
  }
  .loginForm {
    margin: 0 auto;
    border: darkgray 1px solid;
  }

  .sysLoginTitle {
    margin-top: 10px;
    margin-bottom: 20px;
    font-weight: bold;
  }

  .buttonRow {
    text-align: center;
    margin-right: 80px;
    margin-bottom: 10px;
  }

  .buttonRow .el-button {
    padding: 8px 26px;
  }
</style>
