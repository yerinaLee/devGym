package com.gym.alarm;


import com.gym.utils.KoreaHolidayList;

public class NworksController {

    public static String[] empName = {"최고다", "정말로", "이래서", "박자가", "예리나"};
    public static String[] empIds = {
            "8e74b2e1-4b8d-4d1e-bed4-09b3d6fd6c0f",
            "ffecf357-41cf-4741-ba37-93a7cdcfdb32",
            "c63b3c80-49f5-4815-93db-5d45b77fc77a",
            "f369e016-9eb0-48d8-b093-62a750e68c8e",
            "9dd4ee35-789c-4979-b477-978f8712d22b",
    };
    public static String devMode = System.getProperty("java.mode") + "";
    public static String apiURL = "https://naddic.ncpworkplace.com/user/commute-status/list?_=1752566055001";

    public static String bashHost = "auth.worksmobile.com";
    public static String baseDomain = "https://auth.worksmobile.com";
    public static String beforeIdexPage = "https://auth.worksmobile.com/login/checkParam?loginParam=yeri%40naddic.com&countryCode=%2B82"; // {"returnMessage":"","returnCode":200,"loginAuditMessage":""}
    public static String beforeNextIndexPage = "https://auth.worksmobile.com/login/login?accessUrl=http%3A%2F%2Fcalendar.worksmobile.com%2Fmain&loginParam=yeri%40naddic.com&language=ko_KR&countryCode=82&serviceCode=login_web&isIdRememberChecked=true";
    public static String beforeLoginPage = "https://auth.worksmobile.com/login/loginProcessV2";
    public static String beforeIndexPage = "https://auth.worksmobile.com/login/login?accessUrl=http%3A%2F%2Fcalendar.worksmobile.com%2Fmain&loginParam=yeri%40naddic.com&language=ko_KR&countryCode=82&serviceCode=login_web&isIdRememberChecked=true"; // next랑 before이랑 같은건데,,?
    public static String landingPageURL = "https://auth.worksmobile.com/login/login?accessUrl=http%3A%2F%2Fnaddic.ncpworkplace.com%2Fv%2Fhome%2F";

    // for test
    public static KoreaHolidayList koreaHolidayList;




}
