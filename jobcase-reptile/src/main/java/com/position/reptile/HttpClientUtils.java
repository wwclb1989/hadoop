package com.position.reptile;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClientUtils {

    private static final String ENCODING = "UTF-8";
    private static final int CONNECT_TIMEOUT = 6000;
    private static final int SOCKET_TIMEOUT = 6000;

    public static void packageHeader(Map<String, String> params, final HttpRequestBase httpMethod) {
        if (params != null) {
            params.forEach(httpMethod::setHeader);
        }
    }

    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) throws UnsupportedEncodingException {
        if (params != null) {
            List<NameValuePair> nvps = params.entrySet().stream().map(item -> new BasicNameValuePair(item.getKey(), item.getValue())).collect(Collectors.toList());
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    public static HttpClientResp getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws IOException {
        httpResponse = httpClient.execute(httpMethod);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return new HttpClientResp(httpResponse.getStatusLine().getStatusCode(), content);
        }

        return new HttpClientResp(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 发送Post请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResp doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建http post对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)
         * 获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果
         * 访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        //封装请求配置项
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        //设置post请求配置项
        httpPost.setConfig(requestConfig);
        //通过创建的packageHeader方法设置请求头
        packageHeader(headers, httpPost);
        //通过创建的packageParam方法设置请求参数
        packageParam(params, httpPost);
        //创建httpResponse对象获取响应内容
        CloseableHttpResponse httpResponse = null;
        try {
            //执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }
}
