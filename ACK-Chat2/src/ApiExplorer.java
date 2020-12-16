import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ApiExplorer {
   
   private String servicekey = "=qf9QVIOBK3Xu0nAUyUoBTiPobY1AGMKYdixdrFt82GBAXr%2Fj%2F0hObYyrZ1slpOIyrM2ld1GysD62%2F50hHBIDdw%3D%3D"; // 서비스 키
   /* 날씨 데이터 */
   private String x = "62";
   private String y = "124";;// 성남시 수정구 복정동
   SimpleDateFormat form= new SimpleDateFormat("yyyyMMdd");
   Calendar time=Calendar.getInstance();
   private String format = form.format(time.getTime());
    //private String baseDate = format;
   	private String baseDate ="20201215"; // 임시..나중에 위에걸로 수정
    private String baseTime = "2300";// Base_time : 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 (1일 8회)
   

   /* 미세먼지 데이터 */
   private String year = "2020";// 발표 년도 지정
   private String dataNum = "10"; // 출력 데이터 개수 지정
   
   public String getWeather() throws IOException, ParseException {
      
      String result="";
   
   
      
      /* 동네 예보 조회 서비스 */

      //System.out.println("---성남시 수정구 복정동의 날씨---"); // x,y 좌표와 함께 수정 가능

      StringBuilder urlBuilder = new StringBuilder(
            "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /* URL */
      urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + servicekey); /* Service Key */
      urlBuilder
            .append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
      urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
            + URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
      urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
            + URLEncoder.encode("JSON", "UTF-8")); /* 요청자료형식(XML/JSON)Default: XML */
      urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
            + URLEncoder.encode(baseDate, "UTF-8")); /* 지정 날짜 발표 */
      urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
            + URLEncoder.encode(baseTime, "UTF-8")); /* 지정 시간 발표 */
      urlBuilder
            .append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(x, "UTF-8")); /* 예보지점 X 좌표값 */
      urlBuilder
            .append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(y, "UTF-8")); /* 예보지점의 Y 좌표값 */
      URL url = new URL(urlBuilder.toString());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "application/json");

      BufferedReader rd;
      if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else {
         rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }
      StringBuilder sb = new StringBuilder();
      String line;

      while ((line = rd.readLine()) != null) {
         sb.append(line);
      }
      rd.close();
      conn.disconnect();
      String input = sb.toString();
      
      
      /**Json 데이터 출력
      System.out.println("Response code: " + conn.getResponseCode());
       System.out.println(input);
       */
      
      /* 파싱  */
       ParseWeather pw = new ParseWeather(input);
      pw.parsing();
      
      
      result="성남시 수정구 복정동의 날씨\n"+pw.getResult();
      //System.out.println(result);
      /** Json 출력 테스트
      System.out.println("Response code: " + conn2.getResponseCode());
      System.out.println(sb2.toString());
      */
      
      return result;

   }
   

}