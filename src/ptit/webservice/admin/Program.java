/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.admin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author ngosi
 */
public class Program {
    // see http://tienns1-001-site1.ftempurl.com to call end point API
    // document library
    // GSON: https://sites.google.com/site/gson/gson-user-guide
    
    public static final String PrefixToken = "Bearer ";
    public static String Token = "";
    public static final String BASE_URL = "http://tienns1-001-site1.ftempurl.com/api/v1";

    public static void main(String[] args) throws UnsupportedEncodingException {
        Login.run();
    }
    
    public enum HttpHeader {
        Connection("Connection"),
        KeepAlive("Keep-Alive"),
        Trailer("Trailer"),
        TransferEncoding("Transfer-Encoding"),
        Authorization("Authorization"),
        Age("Age"),
        CacheControl("Cache-Control"),
        Accept("Accept"),
        AcceptEncoding("Accept-Encoding"),
        AcceptLanguage("Accept-Language"),
        Cookie("Cookie"),
        SetCookie("Set-Cookie"),
        AccessControlAllowOrigin("Access-Control-Allow-Origin"),
        AccessControlAllowCredentials("Access-Control-Allow-Credentials"),
        AccessControlAllowHeaders("Access-Control-Allow-Headers"),
        AccessControlAllowMethods("Access-Control-Allow-Methods"),
        AccessControlMaxAge("Access-Control-Max-Age"),
        Origin("Origin"),
        ContentDisposition("Content-Disposition"),
        ContentLength("Content-Length"),
        ContentType("Content-Type"),
        ContentEncoding("Content-Encoding"),
        ContentLanguage("Content-Language"),
        Forwarded("Forwarded"),
        From("From"),
        Host("Host"),
        Referer("Referer"),
        ReferrerPolicy("Referrer-Policy"),
        UserAgent("User-Agent");

        private final String header;

        HttpHeader(String header) {
            this.header = header;
        }

        public String getHeader() {
            return this.header;
        }
    }

    public enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        PATCH("PATCH"),
        DELETE("DELETE");

        private final String method;

        HttpMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }
    }

    // only using method GET
    public static String SendHttpGet(String url, Map<String, String> params, Map<String, String> headers) throws MalformedURLException, ProtocolException, IOException {
        StringBuilder sb = new StringBuilder();
        StringBuilder strParams = new StringBuilder();
        if (params != null) {
            strParams.append('?');
            params.forEach((k, v) -> {
                strParams.append(k).append('=').append(v).append('&');
            });
            // loại bỏ dấu '&' ở cuối 
            strParams.deleteCharAt(strParams.length() - 1);
            // nối thêm vào chuỗi
            url += strParams.toString();
        }
        URL _url = new URL(Program.BASE_URL + url); //your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        conn.setRequestMethod(HttpMethod.GET.method);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        if (headers != null) {
            headers.forEach((k, v) -> {
                conn.setRequestProperty(k, v);
            });
        }

        if (conn.getResponseCode() != 200) {
            InputStream inputStream = conn.getErrorStream();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            StringBuilder sb1 = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb1.append(output);
            }
             br.close();
             in.close();
             conn.disconnect();
            throw new RuntimeException(sb1.toString() + "; HTTP Error code : "
                    + conn.getResponseCode());
        }
        
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);

        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        br.close();
        in.close();
        conn.disconnect();
        return sb.toString();
    }

    // used to send POST, PUT, PATCH, DELETE
    public static String SendHttp(String url, HttpMethod method, byte[] data, Map<String, String> headers) throws MalformedURLException, ProtocolException, IOException {
        URL _url = new URL(Program.BASE_URL + url); //your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod(method.method);

        if (headers != null) {
            headers.forEach((k, v) -> {
                conn.setRequestProperty(k, v);
            });
        }
        conn.setUseCaches(false);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(data);
        wr.close();

        if (conn.getResponseCode() != 200) {
            InputStream inputStream = conn.getErrorStream();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
            sb.append(output);
            }
            throw new RuntimeException(sb.toString() + "; HTTP Error code : "
                    + conn.getResponseCode());
        }

        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        br.close();
        in.close();
        conn.disconnect();
        return sb.toString();
    }
}
