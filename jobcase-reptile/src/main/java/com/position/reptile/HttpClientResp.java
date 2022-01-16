package com.position.reptile;

public class HttpClientResp {
    private int code;

    private String content;

    public HttpClientResp() {
    }

    public HttpClientResp(int code) {
        this.code = code;
    }

    public HttpClientResp(String content) {
        this.content = content;
    }

    public HttpClientResp(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{code=" + code + ", content=" + content + '}';
    }
}
