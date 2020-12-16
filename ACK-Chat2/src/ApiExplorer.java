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
   
   private String servicekey = "=qf9QVIOBK3Xu0nAUyUoBTiPobY1AGMKYdixdrFt82GBAXr%2Fj%2F0hObYyrZ1slpOIyrM2ld1GysD62%2F50hHBIDdw%3D%3D"; // ���� Ű
   /* ���� ������ */
   private String x = "62";
   private String y = "124";;// ������ ������ ������
   SimpleDateFormat form= new SimpleDateFormat("yyyyMMdd");
   Calendar time=Calendar.getInstance();
   private String format = form.format(time.getTime());
    //private String baseDate = format;
   	private String baseDate ="20201215"; // �ӽ�..���߿� �����ɷ� ����
    private String baseTime = "2300";// Base_time : 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 (1�� 8ȸ)
   

   /* �̼����� ������ */
   private String year = "2020";// ��ǥ �⵵ ����
   private String dataNum = "10"; // ��� ������ ���� ����
   
   public String getWeather() throws IOException, ParseException {
      
      String result="";
   
   
      
      /* ���� ���� ��ȸ ���� */

      //System.out.println("---������ ������ �������� ����---"); // x,y ��ǥ�� �Բ� ���� ����

      StringBuilder urlBuilder = new StringBuilder(
            "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /* URL */
      urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + servicekey); /* Service Key */
      urlBuilder
            .append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* ��������ȣ */
      urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
            + URLEncoder.encode("10", "UTF-8")); /* �� ������ ��� �� */
      urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
            + URLEncoder.encode("JSON", "UTF-8")); /* ��û�ڷ�����(XML/JSON)Default: XML */
      urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
            + URLEncoder.encode(baseDate, "UTF-8")); /* ���� ��¥ ��ǥ */
      urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
            + URLEncoder.encode(baseTime, "UTF-8")); /* ���� �ð� ��ǥ */
      urlBuilder
            .append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(x, "UTF-8")); /* �������� X ��ǥ�� */
      urlBuilder
            .append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(y, "UTF-8")); /* ���������� Y ��ǥ�� */
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
      
      
      /**Json ������ ���
      System.out.println("Response code: " + conn.getResponseCode());
       System.out.println(input);
       */
      
      /* �Ľ�  */
       ParseWeather pw = new ParseWeather(input);
      pw.parsing();
      
      
      result="������ ������ �������� ����\n"+pw.getResult();
      //System.out.println(result);
      /** Json ��� �׽�Ʈ
      System.out.println("Response code: " + conn2.getResponseCode());
      System.out.println(sb2.toString());
      */
      
      return result;

   }
   

}