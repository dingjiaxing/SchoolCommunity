package cn.edu.shu.scommunity.utils.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import android_hddl_framework.util.DialogUtil;
import android_hddl_framework.util.TLUtil;
import cn.edu.shu.scommunity.FApplication;
import cn.edu.shu.scommunity.utils.Constant;

/**
 * Created by admin on 2016/5/22.
 */
public class HttpRequest {
    public static final int SUCCESS = 1000000;
    public static final int ERROR = 1000001;
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) throws Exception{
        String result = "";
        BufferedReader in = null;
//        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//        } catch (Exception e) {
//            System.out.println("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//        }
        // 使用finally块来关闭输入流
//        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
//        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url2
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url2, String param) throws Exception{
        URL url;
        String result="";
        try {
            url=new URL(url2);
            try {
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(true);
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                DataOutputStream out=new DataOutputStream(conn.getOutputStream());
//                String param="uid="+ URLEncoder.encode(uid,"utf-8")
//                        +"&key="+ URLEncoder.encode(key,"utf-8")
//                        +"&text="+ URLEncoder.encode(text,"utf-8");
//                MagicInfo info=new MagicInfo();
////                uid="18818216390";
//                info.setUid(uid);
//                info.setToken(key);
//                info.setText(text);
//                info.setApi(MagicInfoApiEnum.SEND);
//                info.setDeviceId(deviceId);
//                info.setLocation(MapLocation.location);
//                Gson gs=new Gson();
////                String  param=gs.toJson(info);
////                String param=gs.toJson
//
//
//                GsonBuilder gb =new GsonBuilder();
////                gb.disableHtmlEscaping();
//                gb.disableInnerClassSerialization();
//                String param=gb.create().toJson(info);
//                param="json="+java.net.URLEncoder.encode(param,"utf-8");


//                param=java.net.URLEncoder.encode(param);

//                byte[] by= param.getBytes("UTF-8");
                Log.i("HttpHelp", "send param："+param);
//                param=new String(param.getBytes(), "utf-8");

//                String param="json={\"api\":\"SEND\",\"uid\":\"\",\"token\":\"SQe6100cdcee892111\",\"text\": \"1\"}";
                System.out.println(param);
                out.writeBytes(param);

                out.flush();
                out.close();
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    InputStreamReader in=new InputStreamReader(conn.getInputStream());
                    BufferedReader buffer=new BufferedReader(in);
                    String inputLine=null;
                    while((inputLine=buffer.readLine())!=null){
                        result+=inputLine+"\n";
                    }
                    in.close();
                }
                conn.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try{
            Log.i("HttpResult", result);
        }catch (Exception e){
            e.printStackTrace();
        }
//        return transfer(result);
        return result;
    }
}
