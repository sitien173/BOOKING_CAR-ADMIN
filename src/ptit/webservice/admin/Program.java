/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.admin;

import ptit.webservice.UI.Login;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import ptit.webservice.model.AppUser;

/**
 *
 * @author ngosi
 */
public class Program {
    // see http://tienns1-001-site1.ftempurl.com to call end point API
    // document library
    // GSON: https://sites.google.com/site/gson/gson-user-guide

    public static final String PrefixToken = "Bearer ";
    public static String Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYWRtIiwicm9sZSI6IkFETUlOIiwibmJmIjoxNjUyODg4NTYxLCJleHAiOjE2NTU1NjY5NjEsImlhdCI6MTY1Mjg4ODU2MX0.GikFIm18ut5WtPM0pu-6uP99uHa0uH7i4c0Qm_ZEVzQ";
    public static final String BASE_URL = "http://localhost:5000/api/v1";
    public static AppUser identities;
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        new Login().run();
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
    public static String SendHttpGet(String url, Map<String, String> queryParams, Map<HttpHeader, String> headers) throws MalformedURLException, ProtocolException, IOException {
        StringBuilder sb = new StringBuilder();
        StringBuilder strParams = new StringBuilder();
        if (queryParams != null) {
            strParams.append('?');
            queryParams.forEach((k, v) -> {
                strParams.append(k).append('=').append(v).append('&');
            });
            // loại bỏ dấu '&' ở cuối 
            strParams.deleteCharAt(strParams.length() - 1);
            // nối thêm vào chuỗi
            url += strParams.toString();
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(Program.BASE_URL + url);
            if (headers != null) {
                headers.forEach((k, v) -> {
                    request.addHeader(k.header, v);
                });
            }
            
             try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity responseEntity = response.getEntity();
                InputStreamReader in = new InputStreamReader(responseEntity.getContent());
                BufferedReader br = new BufferedReader(in);

                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // used to send POST, PUT, PATCH, DELETE
    public static String SendHttp(String url, HttpMethod method, byte[] data, Map<String, String> queryParams, Map<HttpHeader, String> headers) throws MalformedURLException, ProtocolException, IOException {
        StringBuilder strParams = new StringBuilder();
        if (queryParams != null) {
            strParams.append('?');
            queryParams.forEach((k, v) -> {
                strParams.append(k).append('=').append(v).append('&');
            });
            // loại bỏ dấu '&' ở cuối 
            strParams.deleteCharAt(strParams.length() - 1);
            // nối thêm vào chuỗi
            url += strParams.toString();
        }

        URL _url = new URL(Program.BASE_URL + url); //your url i.e fetch data from .
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod(method.method);

        if (headers != null) {
            headers.forEach((k, v) -> {
                conn.setRequestProperty(k.header, v);
            });
        }
        conn.setUseCaches(false);
        if(data != null) {
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(data);
            wr.close();
        }

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

    // used to send POST, PUT, PATCH with formData
    public static String SendHttpFormData(String url, HttpMethod method, MultipartEntityBuilder builder, Map<String, String> queryParams, Map<HttpHeader, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpEntityEnclosingRequestBase request = null;
            StringBuilder strParams = new StringBuilder();
            if (queryParams != null) {
                strParams.append('?');
                queryParams.forEach((k, v) -> {
                    strParams.append(k).append('=').append(v).append('&');
                });
                // loại bỏ dấu '&' ở cuối 
                strParams.deleteCharAt(strParams.length() - 1);
                // nối thêm vào chuỗi
                url += strParams.toString();
            }
            switch (method) {
                case POST: {
                    request = new HttpPost(BASE_URL + url);
                    break;
                }
                case PUT: {
                    request = new HttpPut(BASE_URL + url);
                    break;
                }
                case PATCH: {
                    request = new HttpPatch(BASE_URL + url);
                    break;
                }
                default: {
                    throw new RuntimeException("Method support POST, PUT, PATCH");
                }
            }

            if (headers != null) {
                List<BasicHeader> listHeaders = new ArrayList<>();
                headers.forEach((k,v) -> {
                    listHeaders.add(new BasicHeader(k.header, v));
                });
                
                for(Header header : listHeaders) {
                    request.addHeader(header);
                }
            }
            HttpEntity entity = builder.build();
            request.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity responseEntity = response.getEntity();
                InputStreamReader in = new InputStreamReader(responseEntity.getContent());
                BufferedReader br = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] getFile(String requestUrl)
    {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(requestUrl);
             try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity responseEntity = response.getEntity();
                return IOUtils.toByteArray(responseEntity.getContent());
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
