<template>
  <el-container :style="'height:'+fullHeight+'px;'">
    <el-header>
      <el-aside v-show="isShow"><img src="../assets/logo.png" alt="mango" title="mango" width="100" height="30"/><span>Mango</span></el-aside>
      <el-main>
        <el-row style="float: left;"><span class="fa fa-th showHide" aria-hidden="true" title="显示/隐藏" @click="showOrHide"></span></el-row>
        <el-row>
          <span style="margin-top: 22px;">{{user}}</span>
          <el-popover placement="bottom" width="150px" trigger="click">
            <div class="userAboutDiv">
              <el-row class="userRow"><span class="el-icon-s-custom">&nbsp;个人中心</span></el-row>
              <el-row class="userRow"><span class="el-icon-setting">&nbsp;账号设置</span></el-row>
              <el-row class="userRow"><span class="el-icon-chat-dot-square">&nbsp;消息</span></el-row>
              <el-row class="userRow"><span class="fa fa-sign-out" @click="logout">&nbsp;退出登录</span></el-row>
            </div>
            <span style="margin-top: 10px;cursor: pointer;" slot="reference"><el-avatar icon="el-icon-user-solid"></el-avatar></span>
          </el-popover>
        </el-row>
      </el-main>
    </el-header>
    <el-main>
      <el-aside v-show="isShow">
        <el-row class="el-dropdown">
          <el-button type="primary" class="fa fa-plus-circle" @click="addMenu('add')">&nbsp;新增</el-button>
          <el-button type="primary" class="fa fa-pencil-square" @click="addMenu('edit')">&nbsp;修改</el-button>
          <el-button type="primary" class="fa fa-trash" @click="delMenu">&nbsp;删除</el-button>
        </el-row>
        <el-row class="menuTree">
          <!--<el-tree ref="menuTree" :data="treeDatas" @node-click="handleNodeClick" highlight-current node-key="id" :default-expanded-keys="[]"></el-tree>-->
          <!-- 菜单栏 -->
          <Menu class="menu" ref="asideMenu" theme="dark" width="100%" @on-select="selectMenuCallback"
                accordion :open-names="openMenus" :active-name="currentPage" @on-open-change="menuChange">
            <!-- 动态菜单 -->
            <div v-for="(item, index) in menuItems" :key="index">
              <Submenu :class="isShowAsideTitle? '' : 'shrink'" v-if="item.children" :name="index">
                <template slot="title">
                  <!--<Icon :size="item.size" :type="item.type"/>-->
                  <span v-show="isShowAsideTitle">{{item.text}}</span>
                </template>
                <div v-for="(subItem, i) in item.children" :key="index + i">
                  <Submenu :class="isShowAsideTitle? '' : 'shrink'" v-if="subItem.children" :name="index + '-' + i">
                    <template slot="title">
                      <!--<Icon :size="subItem.size" :type="subItem.type"/>-->
                      <span v-show="isShowAsideTitle">{{subItem.text}}</span>
                    </template>
                    <MenuItem :class="isShowAsideTitle? '' : 'shrink'" class="menu-level-3"
                              v-for="(threeItem, k) in subItem.children" :name="threeItem.name" :key="index + i + k">
                      <template v-if="!threeItem.hidden">
                        <!--<Icon :size="threeItem.size" :type="threeItem.type"/>-->
                        <span v-show="isShowAsideTitle">{{threeItem.text}}</span>
                      </template>
                    </MenuItem>
                  </Submenu>
                  <MenuItem :class="isShowAsideTitle? '' : 'shrink'" v-else-if="!subItem.hidden" :name="subItem.name">
                   <!-- <Icon :size="subItem.size" :type="subItem.type"/>-->
                    <span v-show="isShowAsideTitle">{{subItem.text}}</span>
                  </MenuItem>
                </div>
              </Submenu>
              <MenuItem :class="isShowAsideTitle? '' : 'shrink'" v-else-if="!item.hidden" :name="item.name">
                <!--<Icon :size="item.size" :type="item.type" />-->
                <span v-show="isShowAsideTitle">{{item.text}}</span>
              </MenuItem>
            </div>
          </Menu>
        </el-row>
        <el-dialog :title="addOrEdit" :visible.sync="addMenuVis" :close-on-click-modal="false">
          <el-form ref="addMenuForm" :model="addMenuForm" :rules="addMenuRules" size="mini" label-width="120px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="目录/菜单名称:" prop="name">
                  <el-input v-model="addMenuForm.name" placeholder="请输入目录/菜单名称" clearable></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="类型:" prop="type">
                  <el-select v-model="addMenuForm.type" placeholder="请选择类型" style="width: 260px;" @change="typeChange">
                    <el-option style="padding-left: 10px;" v-for="item in type_options" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="上级目录:" prop="upName">
                  <el-input v-model="addMenuForm.upName" disabled = disabled></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="菜单图标:" prop="icon">
                  <el-input v-model="addMenuForm.icon" placeholder="请输入菜单图标链接地址" clearable></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="排序:" prop="orderNum">
                  <el-input v-model.number="addMenuForm.orderNum" placeholder="请输入排序序号" clearable></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="菜单路径:" prop="url" v-if="urlIf">
                  <el-input v-model="addMenuForm.url" placeholder="请输入菜单路径" clearable></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row class="buttonRow">
              <el-button size="mini" type="primary" class="el-icon-error" @click="addMenuVis = false"> 取消 </el-button>
              <el-button size="mini" type="primary" class="el-icon-success" @click="create('addMenuForm')" style="margin-right: 40px;"> 确定 </el-button>
            </el-row>
          </el-form>
        </el-dialog>
      </el-aside>
      <el-main>
        <!-- 标签栏相关功能 -->
        <div class="div-icons">
          <div class="refresh-c" @click="reloadPage" title="刷新当前标签页">
            <Icon type="md-refresh" />
          </div>
          <div class="tag-options" title="关闭标签">
            <Dropdown trigger="click" @on-click="closeTags">
              <Icon type="ios-options" />
              <DropdownMenu slot="list">
                <DropdownItem name="1">关闭其他标签</DropdownItem>
                <DropdownItem name="2">关闭所有标签</DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </div>
        </div>
        <!--组件渲染-->
        <router-view></router-view>
      </el-main>
    </el-main>
  </el-container>
</template>
<script>
  import SysIntroduce from '@/views/introduce/SysIntroduce.vue'

  export default {
    name: 'Home',
    data() {
      return {
        fullHeight: document.documentElement.clientHeight,
        user: '',
        isShow: true,
        // 用于储存页面路径
        paths: {},
        // 当前显示页面
        currentPage: '',
        openMenus: [], // 要打开的菜单名字 name属性
        menuCache: [], // 缓存已经打开的菜单
        tagsArry: [],
        treeDatas: [],
        selectTree: [],
        addOrEdit: '',
        addOrEditIcon: '',
        addMenuVis: false,
        urlIf: false,
        addMenuForm: {
          name: '',
          type: 0,
          url: '',
          icon: '',
          orderNum: '',
          parentId: '',
          upName: ''
        },
        addMenuRules: {
          name: [
            {required: true, message: '请输入目录/菜单名称', trigger: 'blur'},
            {min:1, max:15, message: '请输入1-15个字符', trigger: 'blur'},
            {validator:this.checkName, trigger: 'blur'}
          ],
          url: [
            {required: true, message: '请输入菜单路径', trigger: 'blur'},
            {min:1, max:80, message: '请输入1-80个字符', trigger: 'blur'}
          ],
          orderNum: [
            {required: true, message: '请输入排序序号', trigger: 'blur'},
            {type: 'number', message: '请输入有效数字'}
          ],
          icon: [
            {min:1, max:80, message: '请输入1-80个字符', trigger: 'blur'}
          ]
        },
        type_options: [{
          value: 0,
          label: "目录"
        },{
          value: 1,
          label: "菜单"
        }]
      }
    },
    watch: {
      $route(to) {
        const name = to.name;
        this.currentPage = name;
        if (name === 'error') {
          this.crumbs = '404';
          return
        }
        if (!this.keepAliveData.includes(name)) {
          // 如果标签超过8个 则将第一个标签删除
          if (this.tagsArry.length === 8) {
            this.tagsArry.shift()
          }
          this.tagsArry.push({ name, text: this.nameToTitle[name] })
        }
        setTimeout(() => {
          this.crumbs = this.paths[name]
        }, 0)
      },
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
    computed: {
      // 菜单栏
      menuItems() {
        return this.$store.state.menuItems
      },
      // 需要缓存的路由
      keepAliveData() {
        return this.tagsArry.map(item => item.name)
      },
      // 由于iView的导航菜单比较坑 只能设定一个name参数
      // 所以需要在这定义组件名称和标签栏标题的映射表 有多少个页面就有多少个映射条数
      nameToTitle() {
        const obj = {};
        this.menuItems.forEach(e => {
          this.processNameToTitle(obj, e)
        });
        return obj
      },
    },
    comments: {
    },
    methods: {
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

      // 获取cookies
      getUsers() {
        let _this = this;
        _this.user = sessionStorage.getItem("user");
      },

      // 菜单栏显示/隐藏
      showOrHide() {
        let _this = this;
        _this.isShow = !_this.isShow;
      },

      // 退出
      logout() {
        let _this = this;
        _this.$confirm("确定退出？", "提示", {
          type: "warning"
        }).then(() => {
          sessionStorage.removeItem("user");
          localStorage.removeItem('Authorization');
          _this.$router.push("/");
        });
      },

      // 获取菜单
      initTree() {
        debugger;
        let _this = this;
        _this.selectTree = [];
        _this.$api.getMenus().then((res) => {
          if (res.data.code === 200) {
            debugger;
            _this.treeDatas = res.data.data;
          } else {
            _this.$message({
              type: "warning",
              message: "菜单查询失败"
            });
          }
        });
      },

      getMenus(name) {
        let menus;
        const tagTitle = this.nameToTitle[name];
        for (let i = 0, l = this.menuItems.length; i < l; i++) {
          const item = this.menuItems[i];
          menus = [];
          menus[0] = i;
          if (item.text === tagTitle) {
            return menus
          }
          if (item.children) {
            for (let j = 0, ll = item.children.length; j < ll; j++) {
              const child = item.children[j];
              menus[1] = i + '-' + j;
              menus.length = 2;
              if (child.text === tagTitle) {
                return menus
              }
              if (child.children) {
                for (let k = 0, lll = child.children.length; k < lll; k++) {
                  const grandson = child.children[k];
                  menus[2] = i + '-' + j + '-' + k;
                  if (grandson.text === tagTitle) {
                    return menus
                  }
                }
              }
            }
          }
        }
      },

      // 判断当前标签页是否激活状态
      isActive(name) {
        return this.$route.name === name
      },

      // 跳转页面 路由名称和参数
      gotoPage(name, params) {
        this.currentPage = name;
        this.crumbs = this.paths[name];
        this.$router.replace({ name, params });
        if (!this.keepAliveData.includes(name)) {
          // 如果标签超过8个 则将第一个标签删除
          if (this.tagsArry.length === 8) {
            this.tagsArry.shift()
          }
          this.tagsArry.push({ name, text: this.nameToTitle[name] })
        }
      },

      // 选择菜单回调函数
      selectMenuCallback(name) {
        this.gotoPage(name);
        this.expandAside();
        setTimeout(() => {
          this.isShowAsideTitle = true
        }, 200)
      },

      // 判断
      isShrinkAside() {
        if (this.isShowAsideTitle) {
          this.shrinkAside()
        } else {
          this.expandAside()
        }
      },
      // 收缩
      shrinkAside() {
        for (let i = 0, len = this.asideArrowIcons.length; i < len; i++) {
          this.asideArrowIcons[i].style.display = 'none'
        }
        this.isShowAsideTitle = false;
        this.openMenus = [];
        this.$nextTick(() => {
          this.$refs.asideMenu.updateOpened()
        });
        setTimeout(() => {
          this.asideClassName = '';
          this.main.style.marginLeft = '90px'
        }, 0)
      },
      // 展开
      expandAside() {
        setTimeout(() => {
          this.isShowAsideTitle = true;
          for (let i = 0, len = this.asideArrowIcons.length; i < len; i++) {
            this.asideArrowIcons[i].style.display = 'block'
          }
          this.openMenus = this.menuCache;
          this.$nextTick(() => {
            this.$refs.asideMenu.updateOpened()
          })
        }, 200);
        this.asideClassName = 'aside-big';
        this.main.style.marginLeft = '220px'
      },
      // 刷新当前标签页
      reloadPage() {
        let name = this.$route.name;
        let index = this.keepAliveData.indexOf(name);
        this.$nextTick(() => {
          if (this.tagsArry.length) {
            this.isShowRouter = false;
            this.tagsArry.splice(index, 1);
            this.$nextTick(() => {
              this.tagsArry.splice(index, 0, { name, text: this.nameToTitle[name] });
              this.gotoPage(name);
              this.isShowRouter = true
            })
          } else {
            this.isShowRouter = false;
            this.$nextTick(() => {
              this.tagsArry.push({ name, text: this.nameToTitle[name] });
              this.gotoPage(name);
              this.isShowRouter = true
            })
          }
        })
      },
      // 关闭单个标签
      closeTag(i) {
        let name = this.tagsArry[i].name;
        this.tagsArry.splice(i, 1);
        window.event.stopPropagation();
        // 如果关闭的是当前标签 则激活前一个标签
        // 如果关闭的是第一个标签 则激活后一个标签
        if (this.tagsArry.length) {
          if (this.isActive(name)) {
            if (i !== 0) {
              this.gotoPage(this.tagsArry[i - 1].name)
            } else {
              this.gotoPage(this.tagsArry[i].name)
            }
          }
        } else if (name !== this.home) {
          // 如果没有标签则跳往首页
          this.gotoPage(this.home)
        } else {
          this.reloadPage()
        }
      },
      // 根据路由名称关闭页面
      closeName(name) {
        for (let i = 0, len = this.tagsArry.length; i < len; i++) {
          if (this.tagsArry[i].name === name) {
            this.closeTag(i);
            break
          }
        }
      },
      // 批量关闭标签
      closeTags(flag) {
        if (flag === 1) {
          // 关闭其他标签
          this.tagsArry = [];
          this.gotoPage(this.$route.name)
        } else {
          // 关闭所有标签
          this.tagsArry = [];
          this.gotoPage(this.home);
          this.reloadPage()
        }
      },
      // 激活标签
      activeTag(i) {
        this.gotoPage(this.tagsArry[i].name)
      },

      // 菜单栏改变事件
      menuChange(data) {
        this.menuCache = data
      },

      processNameToTitle(obj, data, text) {
        if (data.name) {
          obj[data.name] = data.text;
          this.paths[data.name] = text ? `${text} / ${data.text}` : data.text
        }
        if (data.children) {
          data.children.forEach(e => {
            this.processNameToTitle(obj, e, text ? `${text} / ${data.text}` : data.text)
          })
        }
      },

      // 点击菜单树
      handleNodeClick(node, resolve) {
        let _this = this;
        debugger;
        let routes = [];
        _this.selectTree = node;
        if (node.data.type === 1) {
          routes.push({
            path: '',
            name: '',
            component: resolve => require(['@/views/introduce/SysIntroduce.vue'], resolve) //(compont[compont.length-1]).split(".")[0]
          });
          _this.$router.options.routes[1].children = routes;
          _this.$router.addRoutes(routes);
        }
      },

      // 添加/修改
      addMenu(type) {
        debugger;
        let _this = this;
        if (_this.selectTree.length === 0) {
          _this.$message({
            type: "warning",
            message: "请选择目录或菜单"
          });
          return;
        }
        _this.$nextTick(()=>{
          _this.$refs.addMenuForm.resetFields();
        });
        if (type === "add") {
          // 菜单下不允许新建目录或菜单
          if (_this.selectTree.data.type === 1) {
            _this.$message({
              type: "warning",
              message: "菜单节点不允许新建目录或菜单"
            });
            return;
          }
          _this.urlIf = false;
          _this.addOrEditIcon = "add";
          _this.addMenuVis = true;
          _this.addOrEdit = "添加目录/菜单";
          _this.addMenuForm = {
            name: '',
            type: 0,
            icon: '',
            url: '',
            orderNum: '',
            parentId: '',
            upName: ''
          };
          if (_this.selectTree.id === 10000) {
            _this.addMenuForm.upName = "无";
            _this.addMenuForm.parentId = 0;
          } else {
            _this.addMenuForm.upName = _this.selectTree.label;
            _this.addMenuForm.parentId = _this.selectTree.id;
          }
        } else if (type === "edit") {
          debugger;
          if (_this.selectTree.id === 10000) {
            _this.$message({
              type: "warning",
              message: "请选择目录或菜单"
            });
            return;
          }
          _this.addOrEditIcon = "edit";
          _this.addMenuVis = true;
          _this.addOrEdit = "修改目录/菜单";
          _this.addMenuForm = {
            name: _this.selectTree.data.name,
            type: _this.selectTree.data.type,
            icon: _this.selectTree.data.icon,
            url: _this.selectTree.data.url,
            orderNum: _this.selectTree.data.orderNum,
            parentId: _this.selectTree.data.parentId,
            upName: ''
          };
          if (_this.selectTree.data.type === 0) {
            _this.urlIf = false;
          } else if (_this.selectTree.data.type === 1) {
            _this.urlIf = true;
          }
          if (_this.selectTree.data.parentId === 0) {
            _this.addMenuForm.upName = "无";
            _this.addMenuForm.parentId = 0;
          } else {
            let params = {
              id: _this.selectTree.data.parentId
            };
            _this.$api.selectById(params).then((res) => {
              if (res.data.code === 200) {
                _this.addMenuForm.upName = res.data.data.name;
              }
            });
            _this.addMenuForm.parentId = _this.selectTree.data.parentId;
          }
        }
      },

      // 删除
      delMenu() {
        debugger;
        let _this = this;
        if (!_this.selectTree || _this.selectTree.length === 0 || _this.selectTree.id === 10000) {
          _this.$message({
            type: "warning",
            message: "请选择目录或菜单"
          });
          return;
        }
        _this.$confirm("确定删除该条记录及其子记录？", {title: "提示"}, {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            id: _this.selectTree.data.id
          };
          _this.$api.deleteMenu(params).then((res) => {
            if (res.data.code === 200) {
              _this.$message({
                type: "success",
                message: "操作成功"
              });
              _this.initTree();
            } else {
              _this.$message({
                type: "warning",
                message: res.data.msg
              });
            }
          });
        });
      },

      // 提交添加/修改
      create(formName) {
        let _this = this;
        if (_this.addOrEditIcon === "add") {
          _this.$refs[formName].validate((validate) => {
            if (validate) {
              let params = {
                name: _this.addMenuForm.name,
                type: _this.addMenuForm.type,
                icon: _this.addMenuForm.icon,
                url: _this.addMenuForm.url,
                orderNum: _this.addMenuForm.orderNum,
                parentId: _this.addMenuForm.parentId
              };

              _this.$api.addMenu(params).then((res) => {
                if (res.data.code === 200) {
                  _this.$message({
                    type: "success",
                    message: "操作成功"
                  });
                  _this.addMenuVis = false;
                  _this.initTree();
                } else {
                  _this.$message({
                    type: "warning",
                    message: res.data.msg
                  });
                }
              });
            }
          });
        } else if (_this.addOrEditIcon === "edit") {
          _this.$refs[formName].validate((validate) => {
            if (validate) {
              let params = {
                name: _this.addMenuForm.name,
                type: _this.addMenuForm.type,
                icon: _this.addMenuForm.icon,
                url: _this.addMenuForm.url,
                orderNum: _this.addMenuForm.orderNum,
                id: _this.selectTree.data.id
              };

              debugger;
              _this.$api.updateById(params).then((res) => {
                if (res.data.code === 200) {
                  _this.$message({
                    type: "success",
                    message: "操作成功"
                  });
                  _this.addMenuVis = false;
                  _this.initTree();
                } else {
                  _this.$message({
                    type: "warning",
                    message: res.data.msg
                  });
                }
              });
            }
          });
        }
      },

      // 校验名称是否存在
      checkName(rule, value, callback) {
        let _this = this;
        if (_this.addOrEditIcon === "add") {
          let params = {
            name: _this.addMenuForm.name,
            parentId: _this.addMenuForm.parentId
          };
          _this.$api.checkMenuName(params).then((res) => {
            if (res.data.code === 200) {
              if (res.data.data === false) {
                return callback(new Error("目录/菜单名称已存在"));
              }
              callback();
            } else {
              return callback(new Error("校验目录/菜单名称失败"));
            }
          });
        } else {
          callback();
        }
      },

      typeChange() {
        let _this = this;
        debugger;
        if (_this.addMenuForm.type === 0) {
          _this.urlIf = false;
        } else {
          _this.urlIf = true;
        }
      }

    },
    mounted() {
      let _this = this;
      _this.get_bodyHeight(); // 主页面高度=浏览器高度
      _this.getUsers();
      _this.initTree();
      // 第一个标签
      const name = this.$route.name;
      _this.currentPage = name;
      _this.tagsArry.push({
        text: _this.nameToTitle[name],
        name,
      });
      // 根据路由打开对应的菜单栏
      _this.openMenus = _this.getMenus(name);
      _this.$nextTick(() => {
        _this.$refs.asideMenu.updateOpened()
      })
    }
  }
</script>
<style scoped>
  * {
    padding: 0;
    margin: 0;
  }

  .el-header {
    height: 300px;
    background-color: #2c3e50;
  }
  .el-header .el-aside {
    height: 100%;
    width: 300px;
    padding-top: 10px;
    padding-bottom: 10px;
    border-right: #ffffff 1px solid;
    float: left;
  }

  .el-header span {
    color: #F8F8F8;
    font-weight: bold;
    font-size: 18px;
  }

  .showHide {
    float: left;
    margin-top: 20px;
    padding-left: 10px;
    cursor: pointer;
  }

  .el-header .el-main .el-row {
    color: #F8F8F8;
    float: right;
    margin-right: 60px;
  }

  .el-header .el-main .el-row span {
    float: left;
    margin-left: 5px;
    font-weight: normal;
    font-size: 14px;
  }

  .el-main .el-aside {
    width: 300px;
    border-right: #ffffff 1px solid;
    background-color: #dddddd;
    min-height: 500px;
    height: 100%;
    float: left;
  }

  .el-tree {
    background-color: #dddddd;
    width: 299px;
    overflow: hidden;
  }

  .userRow {
    margin-bottom: 9px;
    cursor: pointer;
    padding-left: 10px;
  }

  button {
    padding: 8px 13px;
  }

  .el-dropdown {
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 8px;
  }

  .el-dropdown .el-button {

  }

  .menuTree {
    border-top: #ffffff 1px solid;
    padding-top: 10px;
  }

</style>
