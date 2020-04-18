package org.feather.common.constants;


public class Constants {
    /**用户token**/
    public static final String REQUEST_TOKEN_HEADER = "x-auth-token";
    /**用户session***/
    public static final String REQUEST_USER_SESSION = "current-user";
    /**客户端版本**/
    public static final String REQUEST_VERSION_KEY = "version";
    /**客户端平台 android/ios**/
    public static final String REQUEST_PLATFORM_KEY = "";

    public static final String REQUEST_TYPE_KEY = "";
    /**自定义状态码 start**/
    public static final int RESP_STATUS_OK = 200;
    public static final int RESP_STATUS_RIDE_END = 201;

    public static final int RESP_STATUS_NOAUTH = 401;

    public static final int RESP_STATUS_INTERNAL_ERROR = 500;

    public static final int RESP_STATUS_BADREQUEST = 400;
    /**自定义状态码 end**/

    /***七牛keys start****/
    public static final String QINIU_ACCESS_KEY="QpjYpGjjb_6H7QuNGWDGsnpEmc4er_mCpWAAvcOZ";

    public static final String QINIU_SECRET_KEY="acFn-SnHLRuXKl0KR2iBYQSE4-TmSFs_NZTnbd03";

    public static final String QINIU_HEAD_IMG_BUCKET_NAME="";

    public static final String QINIU_HEAD_IMG_BUCKET_URL="";

    /***七牛keys end****/

    public static final String feather_TS_VIDEO_KEY = "featheronline123456";


    /**阿里云SMS start**/
    public static final String ALSMS_ACCESS_KEY = "";

    public static final String ALSMS_ACCESS_SECRET = "";

    public static final String ALSMS_DOMAIN = "dysmsapi.aliyuncs.com";
    public static final String ALSMS_MS_PRODUCT = "Dysmsapi";

    public static final String ALSMS_VERCODE_TPLID = "";

    public static final String ALSMS_SIGNNAME = "";

    /**阿里云SMS end**/


    /**百度云推送 start**/
    public static final String BAIDU_YUN_PUSH_API_KEY="";

    public static final String BAIDU_YUN_PUSH_SECRET_KEY="";

    public static final String CHANNEL_REST_URL = "";
    /**百度云推送end**/


}
