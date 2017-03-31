package com.testretrofit.util;

/**
 * Created by hclpc on 12/1/2016.
 */

public class Constant {
    /***
     * COMMON
     */
    public static final String APP_NAME = "Retrofit 2";
    public static final String DB_NAME = "test_retrofit";
    private static final String APP_VERSION = "v1";
    public static boolean DEBUG = true;
    public static boolean DEBUG_SIGNIN = false;

    public static final String API_REQUEST = "http://www.host.com/";
    public static final String GOOGLE_MAP = "http://maps.google.com/maps";
    public static final String LOCALE_EN = "en";
    public static final String LOCALE_FR = "fr";
    public static final String DATE_FORMAT3 = "dd/MM/yyyy";
    public static final String DATE_FORMAT2 = "yyyy MMM";
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String TAG_TOKEN = "authorization-token";
    public static final String APP_TOKEN = "";
    public static final String SUCCESS = "success";
    public static final int SPLASH_SHOW_TIME = 1000;
    public static final long LOCATION_UPDATE_TIME = 120000;

    /**
     * INTENT EXTRAS
     */
    public static final String INTENT_ID = "id";
    public static final String INTENT_LATITUDE = "latitude";
    public static final String INTENT_LONGITUDE = "longitude";
    public static final String INTENT_CUSTOMER = "customer";
    public static final String INTENT_CUSTOMER_DETAIL = "customerDetail";
    public static final String INTENT_CREDIT = "credit";

    /***
     * API ENDPOINTS
     */
    public static final String API_GET_CUSTOMER = "customer/{customerid}";
    public static final String API_PUT_CUSTOMER = "customer/create";

    /***
     * DB
     */
    public static final String DB_CUSTOMER = "customer";
    public static final String DB_REQUESTS = "requests";
    public static final String DB_POI = "poi";

    /**
     * REQUEST ID'S
     */
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_POI = 2;
}
