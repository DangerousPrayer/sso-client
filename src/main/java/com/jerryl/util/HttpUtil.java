package com.jerryl.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuruijie on 2016/11/29.
 */
public class HttpUtil {
    protected static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public interface ResponseListener {
        void afterResponse(String webResult);
    }

    public static void sendRequest(final RequestConfig config, final ResponseListener responseListener) {
        sendRequest(config, responseListener, false);
    }

    //发送get请求
    public static void sendRequest(final RequestConfig config, final ResponseListener responseListener, boolean async) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;


                StringBuilder sb = new StringBuilder();
                if(config.getContentType().equals(RequestConfig.CONTENT_TYPE_FORM_DATA)){
                    Map<String, Object> params = config.getParams();

                    for(String key: params.keySet()){
                        sb.append(key).append("=").append(params.get(key)).append("&");
                    }
                    if (sb.length()>0)sb.deleteCharAt(sb.length()-1);
                    config.setBody(sb.toString());
                }

                try {
                    String urlStr =config.getUrl().startsWith("http")?config.getUrl():"http://"+config.getUrl();
                    if(config.getType().equals("GET")||config.getType().equals("DELETE")){
                        if(sb.length()>0)urlStr += "?"+sb.toString();
                    }
                    URL url = new URL(urlStr);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(config.getType());
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    if(!(config.getType().equals("GET")||config.getType().equals("DELETE"))){
                        OutputStreamWriter writer = new OutputStreamWriter(
                                connection.getOutputStream());
                        writer.write(config.getBody());
                        writer.flush();
                    }

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (responseListener != null) {
                        responseListener.afterResponse(response.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

        };

        if(async){
            executorService.submit(runnable);
        }else{
            runnable.run();
        }
    }


    public static class RequestConfig {
        public static final String CONTENT_TYPE_FORM_DATA = "application/x-www-form-urlencoded";
        public static final String CONTENT_TYPE_JSON = "application/json";

        private String url;
        private String type;
        private String contentType;
        private Map<String, Object> params = new HashMap<>();
        private String body;

        public void addParam(String name, Object value){
            params.put(name, value);
        }

        public RequestConfig(String url, String type) {
            this.url = url;
            this.type = type;
            contentType = CONTENT_TYPE_FORM_DATA;
            body = "";
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
