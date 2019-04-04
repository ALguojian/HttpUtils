package com.shuwtech.commonsdk.mediator.event;

public interface TagEvent {
    String EVENT_AUTH_FAIL = "event_auth_fail";
    String EVENT_REFRESH_TEAM_LIST = "event_refresh_team_list";
    String EVENT_REFRESH_ACCOUNT = "event_refresh_account";
    String EVENT_LOGOUT = "event_logout";
    String EVENT_PUSH = "event_push";
    String EVENT_UPDATE_ORDER_LOCATION = "update_order_location";
    String EVENT_ADD_STYLIST = "event_add_stylist";
    String EVENT_CHECK_TEAM_TAB = "event_check_team_tab";

    String EVENT_ORDER_PAY_SUCCESS = "event_order_pay_success";
    String EVENT_PAY_VIP_SUCCESS = "event_pay_vip_success";

    String EVENT_REFRESH_CUSTOMER_HOME_LIST = "event_refresh_customer_home_list";
    String EVENT_REFRESH_MARKETING_HOME_LIST = "event_refresh_marketing_home_list";
}
