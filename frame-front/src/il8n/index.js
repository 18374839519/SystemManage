/*国际化多语言*/
import Vue from "vue";
import VueIl8n from "vue-i18n";

Vue.use(VueIl8n);

// 注册il8n实例并引入语言文件，文件格式等一下解析
const il8n = new VueIl8n({
  locale: "zh_cn",
  messages: {
    "zh_cn": require('@/assets/languages/zh_cn.json'),
    "en_us": require('@/assets/languages/en_us.json')
  }
});

export default il8n;
