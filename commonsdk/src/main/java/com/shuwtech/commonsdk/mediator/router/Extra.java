package com.shuwtech.commonsdk.mediator.router;

/**
 * bundle extra names
 * */
public interface Extra {
    String EXTRA_BUNDLE = "extra_bundle";
    String EXTRA_MOBILE = "extra_mobile";
    String EXTRA_ID = "extra_id";
    String EXTRA_WEEX_URL = "extra_weex_url";
    String EXTRA_IMAGE_URL = "image_url";
    String EXTRA_UPLOAD = "extra_upload";
    String EXTRA_HEIGHT_SCALE = "height_scale";
    String EXTRA_CROP_RESULT_PATH = "crop_path";
    String EXTRA_ID_IMG_PATH = "extra_id_img_path";
    String EXTRA_MULTI_GALLERY_IMAGES = "extra_multi_gallery_images";
    String EXTRA_MAX = "extra_max";
    String EXTRA_ORDER = "extra_order";
    String EXTRA_URL = "extra_url";
    String EXTRA_TITLE = "extra_title";
    String EXTRA_TYPE = "extra_type";

    String EXTRA_VERIFY_CODE_FROM = "extra_verify_code_from";
    String EXTRA_PUSH = "extra_push";
    String EXTRA_ID_FRONT = "extra_id_front";
    String EXTRA_ORDER_ID = "extra_order_id";

    //flat
    int FLAG_VERIFY_CODE_FROM_LOGIN = 0x01;
    int FLAG_VERIFY_CODE_FROM_CHANGE_MOBILE = 0x02;
    int FLAG_VERIFY_CODE_FROM_CHANGE_MOBILE_CHECK = 0x03;
}
