package com.shuwtech.commonsdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 发廊model
 */
public class Salon extends BaseModel {
    /**
     * "_id": "5b549004fb48b50011bbc216",  # 唯一标识
     * "name": "小虾米发廊",  # 发廊名
     * "city": "杭州",  # 城市
     * "province": "浙江",  # 省份
     * "area": "江干区",  # 江干区
     * "address": "新加坡科技园",  # 店详细地址地址
     * "number": 100001, # 发廊编号
     * "longitude": 123.003,  # 发廊经度
     * "latitude": 123.003,  # 发廊纬度
     * "mobile": "18329031976",  # 发廊联系电话
     * "certificate_img": "http://xdimg.meiyezhushou.com/xd/app/20180620/a6a081641b023510a6a081641b023510.jpeg",  # 客户营业执照
     * "legal_person": "小虾米",  # 法人名称
     * "legal_mobile": "18329031976",  # 法人联系方式
     * "idcard_front": "http://xxxx.jpg",  # 身份证列表
     * "idcard_back": "http://xxx.jpg",
     * "alipay_mobile": "18329031976",  # 支付宝账号
     * <p>
     * # 发廊主页字段与发型师主页字段保持一致
     * "work_start": 111,  # 上班时间
     * "work_end": 111,  # 下班时间
     * "rest_days": [],  # 休息时间  星期一、星期二、...
     * "cut_price": 100,  # 剪发价格
     * "dye_price": 152,  # 染发价格
     * "dye_price_end": 152,  # 染发价格
     * "perm_price": 188,  # 烫发价格
     * "perm_price_end": 188,  # 烫发价格
     * "icon_img": "http://xxxx.jpg",  # 发廊头图
     * "scene_img": "http://xxxx.jpg",  # 发廊环境信息
     * "productions": ["http://xxxx.jpg", "http://yyy.jpg"],  # 发廊作品集
     * <p>
     * "good": ['5a4eee8fc338121d3ccf875f'], # 点赞用户id列表
     * "good_num": 13,  # 点赞数量
     * "status": 0,  # 认证状态 0-未认证 1-认证通过 2-认证中 3-认证失败
     * "customer_strategy": 1, # 用户分流策略 1-用户自选 2-智能匹配 3-随机匹配
     * "order_postion": {
     * "area": "",  # 江干区
     * "city": "杭州",  # 城市
     * "province": "浙江",  # 省份
     * "address": "发型店位置", # 详细地址
     * "consignee": "收货人", # 收货人
     * "mobile": "18329031976" # 收货人手机号
     * },
     * "cdate": "2018-07-21 14:39:55.747Z",  # 创建的时间戳
     * "udate":  "2018-07-21 14:39:55.747Z" # 更新记录的时间戳
     * "have_stylist": 是否添加过发型师
     * "share_url": "http://www.xxx.jpg", // 绑定海报链接
     * "qrcodes": ["",""] //联系我们二维码s
     */

    public String name;
    public String city;
    public String province;
    public String area;
    public String address;
    public String number;
    public String longitude;
    public String latitude;
    public String mobile;
    @SerializedName("certificate_img")
    public String certificateImg;
    @SerializedName("legal_person")
    public String legalPerson;
    @SerializedName("legal_mobile")
    public String legalMobile;
    @SerializedName("idcard_front")
    public String idCardFront;
    @SerializedName("idcard_back")
    public String idCardBack;
    @SerializedName("alipay_mobile")
    public String aliPayMobile;

    @SerializedName("work_start")
    public String workStart;
    @SerializedName("work_end")
    public String workEnd;
    @SerializedName("rest_days")
    public List<String> restDays;
    @SerializedName("cut_price")
    public int cutPrice;
    @SerializedName("cut_price_end")
    public int curPriceEnd;
    @SerializedName("dye_price")
    public int dyePrice;
    @SerializedName("dye_price_end")
    public int dyePriceEnd;
    @SerializedName("perm_price")
    public int permPrice;
    @SerializedName("perm_price_end")
    public int permPriceEnd;
    @SerializedName("icon_img")
    public String iconImg;
    @SerializedName("scene_img")
    public List<String> sceneImg;
    public List<String> productions;
    @SerializedName("share_url")
    public String shareUrl;
    public List<String> good;
    @SerializedName("good_num")
    public int goodNum;
    public int status;
    @SerializedName("have_stylist")
    public int haveStylist;
    @SerializedName("customer_strategy")
    public int customerStrategy;
    @SerializedName("order_postion")
    public OrderPosition orderPostion;
    @SerializedName("salon_mobile")
    public String salonMobile;
    @SerializedName("use_salon_position")
    public int useSalonPosition;
    public List<String> qrcodes;

    public String getCover() {
        if (sceneImg == null || sceneImg.size() == 0 || status == 3) return "";
        return sceneImg.get(0);
    }
}
