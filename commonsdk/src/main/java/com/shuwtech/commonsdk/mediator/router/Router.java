package com.shuwtech.commonsdk.mediator.router;

import com.shuwtech.commonsdk.BuildConfig;
import com.shuwtech.commonsdk.mediator.api.Urls;

/*
* 路由
* */
public interface Router {

    interface GROUP {
        String GROUP_APP = "/app";
        String GROUP_LOGIN = "/login";
        String GROUP_MARKETING = "/marketing";
        String GROUP_CUSTOMER = "/customer";
        String GROUP_TEAM = "/team";
        String GROUP_MINE = "/mine";
        String GROUP_PHOTO = "/photo";
        String GROUP_WEEX = "/weex";
    }

    interface PATH {
        //app
        //首页
        String PATH_APP_MAIN = GROUP.GROUP_APP + "/main";
        //web
        String PATH_APP_WEB = GROUP.GROUP_APP + "/web";
        //weex
        String PATH_APP_WEEX = GROUP.GROUP_APP + "/weex";

        //login
        //登陆
        String PATH_LOGIN_LOGIN = GROUP.GROUP_LOGIN + "/login";
        //验证码
        String PATH_VERIFY_CODE = GROUP.GROUP_LOGIN + "/verifyCode";
        //引导页
        String PATH_LOGIN_GUIDE = GROUP.GROUP_LOGIN + "/guide";

        //marketing
        //营销首页
        String PATH_MARKETING_MAIN = GROUP.GROUP_MARKETING + "/main";
        //营销首页fragment
        String PATH_MARKETING_FRAGMENT_HOME = GROUP.GROUP_MARKETING + "/fragmentHome";
        //分流设置
        String PATH_MARKETING_DIFFLUENCE = GROUP.GROUP_MARKETING + "/diffluence";
        //商品详情
        String PATH_MARKETING_GOODS_DETAIL = GROUP.GROUP_MARKETING + "/goodsDetail";
        //购物车页面
        String PATH_MARKETING_CART = GROUP.GROUP_MARKETING + "/cart";
        //支付商品页面
        String PATH_MARKETING_ORDER_PAY = GROUP.GROUP_MARKETING + "/orderPay";

        //customer
        //客户首页
        String PATH_CUSTOMER_MAIN = GROUP.GROUP_CUSTOMER + "/main";
        //客户首页fragment
        String PATH_CUSTOMER_FRAGMENT_HOME = GROUP.GROUP_CUSTOMER + "/fragmentHome";

        //team
        //团队首页
        String PATH_TEAM_MAIN = GROUP.GROUP_TEAM + "/main";
        //团队首页fragment
        String PATH_TEAM_FRAGMENT_HOME = GROUP.GROUP_TEAM + "/fragmentHome";
        /**
         * 商家版宝描页
         */
        String PATH_PERSON_TREASURE_DESCRIBE = GROUP.GROUP_TEAM + "/treasureDescribe";
        //排行
        String PATH_TEAM_RANK = GROUP.GROUP_TEAM + "/rank";
        String PATH_TEAM_MEMBER_DETAIL = GROUP.GROUP_TEAM + "/memberDetail";
        String PATH_TEAM_SHARE_POSTER = GROUP.GROUP_TEAM + "/sharePoster";
        String PATH_TEAM_BATCH_RECHARGE = GROUP.GROUP_TEAM + "/batchRecharge";
        String PATH_TEAM_PAY_VIP = GROUP.GROUP_TEAM + "/payVip";

        //mine
        String PATH_MINE_MAIN = GROUP.GROUP_MINE + "/main";
        String PATH_MINE_FRAGMENT_HOME = GROUP.GROUP_MINE + "/fragmentHome";
        String PATH_MINE_CHANGE_MOBILE = GROUP.GROUP_MINE + "/changeMobile";
        String PATH_MINE_CHANGE_MOBILE_WARN = GROUP.GROUP_MINE + "/changeMobileWarn";
        String PATH_MINE_SETTING = GROUP.GROUP_MINE + "/setting";
        String PATH_MINE_MY_ORDERS = GROUP.GROUP_MINE + "/myOrders";
        String PATH_MINE_ORDER_DETAIL = GROUP.GROUP_MINE + "/orderDetail";
        String PATH_MINE_MY_CARD = GROUP.GROUP_MINE + "/myCard";
        String PATH_MINE_USED_RECORD = GROUP.GROUP_MINE + "/usedRecord";
        String PATH_MINE_MEMBER_SELECT = GROUP.GROUP_MINE + "/memberSelect";

        //photo
        String PATH_PHOTO_ID_CAMERA = GROUP.GROUP_PHOTO + "/idCamera";
        String PATH_PHOTO_GALLERY = GROUP.GROUP_PHOTO + "/gallery";
        String PATH_PHOTO_VIEW_IMAGE = GROUP.GROUP_PHOTO + "/viewImage";

        //weex
        String PATH_WEEX_PAGE = GROUP.GROUP_WEEX + "/page";

        String PATH_WEEX_URL_SALON_WORK = "center/salon-works-list.js";
        String PATH_WEEX_URL_MY_ORDER = "center/my-order.js";
        String PATH_WEEX_URL_SALON_MAIN = "salon-page/salon-page.js";
        // scene-页面场景 1-第一次填写 2修改
        String PATH_WEEX_URL_CERTIFY_STEP = "center/certification-step-1.js?scene=%d";
        String PATH_WEEX_URL_CERTIFY_PREVIEW = "center/certification-info-preview.js";
        String PAHT_WEEX_CUSTOMER_DETAIL = "my-customer/my-customer.js?id=%s";
        //type-是否使用发廊地址 1-是 0-不是
        String PATH_WEEX_SHIPPING_ADDRESS = "marketing/shipping-address.js?type=%d&id=%s";
        String PATH_WEEX_ORDER_DETAIL = "center/order-detail.js?id=%s";

        //web
        String PATH_WEB_URL_USER_PROTOCOL = Urls.BASE_URL + "/agreement";
        String PATH_SALON_PAGE = BuildConfig.IS_DEVELOP_ENV ? "https://devsalon.meiyezhushou.com/static/falang/salon-page.html#/?id=%s&scene=3"
            : "https://salon.meiyezhushou.com/static/falang/salon-page.html#/?id=%s&scene=3";
    }
}
