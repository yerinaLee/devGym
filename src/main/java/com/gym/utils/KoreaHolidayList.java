package com.gym.utils;

import java.io.IOException;
import java.io.StringReader;
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
import com.gym.utils.HttpSocket;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class KoreaHolidayList {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Date date = new Date();
        getLunarDate(getDateByString(date));
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
    public static String getLunarDate(String dateStr) throws IOException, ParserConfigurationException, SAXException {
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

//        if(Integer.parseInt(month) < 10) month = "0"+ month;
//        if(Integer.parseInt(day) < 10) day = "0"+ day;

        url += "?solYear="+year+"&solMonth="+month+"&solDay="+day+"&ServiceKey="+serviceKey;

        // URL : 주소 관리하는 객체(연결 등의 작업 진행)
        String out = HttpSocket.getApiRequest(url, null);
//        JSONObject jsonObject = new JSONObject(out); // 데이터는 JSON이 아닌 XML 형태로 넘어옴
        
        // XML 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(out)));

        System.out.println("doc : " + doc.toString());

        NodeList list = doc.getElementsByTagName("item");
        String result = list.item(0).getTextContent();

        System.out.println("result : " + result); // result : 03병신(丙申)평0829을사(乙巳)을유(乙酉)2025242460943평09수2025

        return null;
    }
}
