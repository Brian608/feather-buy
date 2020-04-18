package org.feather.common.constants;


public class Constants {
    /**自定义状态码 start**/
    public static final int RESP_STATUS_OK = 200;

    public static final int RESP_STATUS_NOAUTH = 401;

    public static final int RESP_STATUS_INTERNAL_ERROR = 500;

    public static final int RESP_STATUS_BADREQUEST = 400;
    /**自定义状态码 end**/

    /**用户token**/
    public static final String REQUEST_TOKEN_HEADER = "x-auth-token";
    /**用户session***/
    public static final String REQUEST_USER_SESSION = "current-user";
    /**客户端版本**/
    public static final String REQUEST_VERSION_KEY = "version";

    public static final String CACHE_PRODUCT_STOCK = "product:stock";
    public static final String CACHE_PRODUCT_STOCK_LOCK = "product:stock:lock";
    public static final String ORDER_RETRY_LOCK = "trade:retry:lock";

}
