<template>
  <div class="home">
    <header class="header">
      <h1>欢迎来到e店邦管理平台</h1>
      <p>这是拥有管理车辆预约、客户关系维护等功能的后台系统</p>
    </header>
    <section class="content">
      <h2>关于我们</h2>
      <p>为了更好的进行商户门店运营，以及维护新老客户的信息，广州xxx汽车服务公司决定实施汽车门店管理系统。</p>
      <p>但市面上出售的系统价格昂贵，笨重，所以经过市场调研研究，单独对该公司，做一个轻量级的互联网门店运营系统，主要功能包括商铺维护，服务预约，消费结算，财务审核，客户关系维护，订单发货，物流跟踪，客户评价，报表分析等功能。</p>
      <button class="btn" onclick="forWardHome">查看更多</button>
    </section>
    <section class="featured">
      <h2>Featured Products</h2>
      <div class="product">
        <img src="../assets/images/ccc.png" alt="产品一" class="product-image">
        <h3>绿色兰博基尼</h3>
        <p>绿色兰博基尼是一款令人瞩目的超级跑车，以其独特的外观设计、强大的动力性能和豪华的内部装备而闻名。驾驶绿色兰博基尼将带来令人难以忘怀的驾驶体验，让人感受到速度和奢华的完美结合。</p>
        <button class="btn">服 务</button>
      </div>
      <div class="product">
        <img src="../assets/images/ddd.png" alt="产品二" class="product-image">
        <h3>黑色柯尼塞格</h3>
        <p>黑色柯尼塞格可能是柯尼塞格旗下特定车型的限量版或特殊配置，以进一步提高其独特性和稀缺性。 可能有特殊标识：该车型可能会带有特殊的黑色柯尼塞格徽标或其他标识，以凸显其独特身份。</p>
        <button class="btn">服 务</button>
      </div>
    </section>
    <footer class="footer">
      <p>&copy; 2023 My Website. All rights reserved.</p>
    </footer>
  </div>
</template>

<script>
  export default {
    name:'Home'
  };

</script>

<style>
  .home {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
  }

  .header {
    background-color: #333;
    color: #fff;
    padding: 20px;
    text-align: center;
  }

  .header h1 {
    font-size: 28px;
    margin-bottom: 10px;
  }

  .content {
    margin: 20px;
  }

  .content h2 {
    font-size: 24px;
  }

  .content p {
    line-height: 1.5;
  }

  .btn {
    background-color: #333;
    color: #fff;
    border: none;
    padding: 10px 20px;
    margin-top: 10px;
    cursor: pointer;
  }

  .featured {
    background-color: #f9f9f9;
    padding: 20px;
    margin-top: 20px;
  }

  .product {
    display: inline-block;
    width: 200px;
    margin: 10px;
    text-align: center;
  }

  .product-image {
    width: 100%;
    max-height: 200px;
  }

  .product h3 {
    font-size: 18px;
  }

</style>

<!--  <script>-->
<!--export default {-->
<!--  name: "Index",-->
<!--  data() {-->
<!--    return {-->
<!--      // 版本号-->
<!--      version: "3.8.3",-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    goTarget(href) {-->
<!--      window.open(href, "_blank");-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped lang="scss">-->
<!--.home {-->
<!--  blockquote {-->
<!--    padding: 10px 20px;-->
<!--    margin: 0 0 20px;-->
<!--    font-size: 17.5px;-->
<!--    border-left: 5px solid #eee;-->
<!--  }-->
<!--  hr {-->
<!--    margin-top: 20px;-->
<!--    margin-bottom: 20px;-->
<!--    border: 0;-->
<!--    border-top: 1px solid #eee;-->
<!--  }-->
<!--  .col-item {-->
<!--    margin-bottom: 20px;-->
<!--  }-->

<!--  ul {-->
<!--    padding: 0;-->
<!--    margin: 0;-->
<!--  }-->

<!--  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;-->
<!--  font-size: 13px;-->
<!--  color: #676a6c;-->
<!--  overflow-x: hidden;-->

<!--  ul {-->
<!--    list-style-type: none;-->
<!--  }-->

<!--  h4 {-->
<!--    margin-top: 0px;-->
<!--  }-->

<!--  h2 {-->
<!--    margin-top: 10px;-->
<!--    font-size: 26px;-->
<!--    font-weight: 100;-->
<!--  }-->

<!--  p {-->
<!--    margin-top: 10px;-->

<!--    b {-->
<!--      font-weight: 700;-->
<!--    }-->
<!--  }-->

<!--  .update-log {-->
<!--    ol {-->
<!--      display: block;-->
<!--      list-style-type: decimal;-->
<!--      margin-block-start: 1em;-->
<!--      margin-block-end: 1em;-->
<!--      margin-inline-start: 0;-->
<!--      margin-inline-end: 0;-->
<!--      padding-inline-start: 40px;-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</style>-->

