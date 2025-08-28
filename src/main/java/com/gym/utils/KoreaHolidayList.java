package com.gym.utils;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KoreaHolidayList {
    public static void main(String[] args) {
        Date date = new Date();
        getLunarDate(getDateByString(date, "-"));
    }

    public static Map<String, String> solarHolidayMap = new HashMap<>();
    public static Map<String, String> lunarHoidayMap = new HashMap<>();

    public static String getDateByString(Date date){
        return getDateByString(date, "-");
    }

    public static String getDateByString(Date date, String separator){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
        return sdf.format(date);
    }

    // 한국천문연구원 양음력 계산 api 호출을 통해 음력일 계산 / 일일트래픽 10000
    // lunYear	연(음력)
    // lunMonth	월(음력)
    // lunDay	일(음력)
    // http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService/getLunCalInfo?solYear=2025&solMonth=08&solDay=28&ServiceKey=zICFxRCxeKNDk0eZH240WS8zMz0oqxcxx5MzyqcDCG62vDweuJMmeVOa8tsFAYQSARpg2uBfAaJo%2BxauvnvhDw%3D%3D
    /*<response>
        <header>
        <resultCode>00</resultCode>
            <resultMsg>NORMAL SERVICE.</resultMsg>
        </header>
        <body>
            <items>
                <item>
                    <lunDay>06</lunDay>
                    <lunMonth>07</lunMonth>
                    <lunNday>30</lunNday>
                    <lunYear>2025</lunYear>
                    <solDay>28</solDay>
                    <solMonth>08</solMonth>
                    <solYear>2025</solYear>
                </item>
            </items>
        </body>
    </response>*/
    public static String getLunarDate(String dateStr) throws UnsupportedEncodingException, MalformedURLException {
        final String serviceKey = "zICFxRCxeKNDk0eZH240WS8zMz0oqxcxx5MzyqcDCG62vDweuJMmeVOa8tsFAYQSARpg2uBfAaJo%2BxauvnvhDw%3D%3D";
        String url = "http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService/getLunCalInfo";
        
        // 양력 날짜 데이터 세팅
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        String day = Integer.toString(calendar.get(Calendar.DATE));
//        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
//        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String[] arr = dateStr.split("-");
        String year = arr[0];
        String month = arr[1];
        String day = arr[2];

        if(Integer.parseInt(month) < 10) month = "0"+ month;
        if(Integer.parseInt(day) < 10) day = "0"+ day;

        url += "?solYear="+year+"&solMonth="+month+"&solDay="+day+"&ServiceKey="+serviceKey;

//        StringBuilder urlBuilder = new StringBuilder(url);
//        urlBuilder.append("?" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder)

        // URL : 주소 관리하는 객체(연결 등의 작업 진행)
        URL responseUrl = new URL(url);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();











        return null;
    }








}
