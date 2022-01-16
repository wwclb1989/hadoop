package com.position.reptile;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
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
}
