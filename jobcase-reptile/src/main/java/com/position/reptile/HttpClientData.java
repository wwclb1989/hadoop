package com.position.reptile;

import java.util.HashMap;
import java.util.Map;

public class HttpClientData {

    public static void main(String[] args) throws Exception {
        //设置请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", "JSESSIONID=ABAAABAAADEAAFIB83F3FFDBFB81E5DB89A6203AAC90D1E; WEBTJ-ID=20190807164335-16c6b3f6fe4465-0083689dbeda1e-c343162-2073600-16c6b3f6fe5822; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1565167415; _ga=GA1.2.979148561.1565167415; _gat=1; user_trace_token=20190807164336-71ff3cf4-b8ef-11e9-880c-525400f775ce; LGSID=20190807164336-71ff3e2f-b8ef-11e9-880c-525400f775ce; PRE_UTM=; PRE_HOST=; PRE_SITE=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; LGUID=20190807164336-71ff409c-b8ef-11e9-880c-525400f775ce; _gid=GA1.2.1862604946.1565167416; LG_LOGIN_USER_ID=313c2ede909fbe8a5612e69286a54d7025b0d69961f32718; LG_HAS_LOGIN=1; _putrc=7A54D31E687831AB; login=true; unick=%E5%BC%A0%E6%98%8E%E5%BC%BA; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=144; gate_login_token=552cd260fd9a48fbc4997864f3c1059a49d1def98fdfedcc; privacyPolicyPopup=false; index_location_city=%E5%8C%97%E4%BA%AC; TG-TRACK-CODE=index_search; X_HTTP_TOKEN=a0ff806945486fa53447615651b2f3e089cb75eb2e; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1565167443; LGRID=20190807164403-829fa8b1-b8ef-11e9-880c-525400f775ce; SEARCH_ID=34e3829847b44e31ba0d4a95365e9809");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en-GB;q=0.8,en;q=0.7");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)  AppleWebKit/537.36 (KHTML, like Gecko)  Chrome/75.0.3770.142 Safari/537.36");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Referer", "https://www.lagou.com/jobs/list_%E5%A4%A7%E6%95%B0%E6%8D%AE? px=default&city=%E5%85%A8%E5%9B%BD");
        headers.put("Origin", "https://www.lagou.com");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("X-Anit-Forge-Token", "None");
        headers.put("Cache-Control", "no-cache");
        headers.put("X-Anit-Forge-Code", "0");
        headers.put("Host", "www.lagou.com");
        //设置请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("kd", "大数据");
        params.put("city", "全国");

        for (int i = 1; i < 31; i++) {
            params.put("pn", String.valueOf(i));
            HttpClientResp result = HttpClientUtils.doPost("https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&first=true&px=default", headers, params);
            HttpClientHdfsUtils.createFileBySysTime("hdfs://hadoop01:9000", "page" + i, result.toString());
            Thread.sleep(1 * 500);
        }
    }
}
