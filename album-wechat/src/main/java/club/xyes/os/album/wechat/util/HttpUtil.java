package club.xyes.os.album.wechat.util;

import club.xyes.os.album.commons.context.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Create by 郭文梁 2019/4/19 0019 14:03
 * HttpUtil
 * Http相关工具类
 *
 * @author 郭文梁
 * @data 2019/4/19 0019
 */
@Slf4j
public class HttpUtil {
    private static final String DEFAULT_JSON_CHARSET = "utf-8";

    /**
     * 获取客户端
     *
     * @return 客户端对象
     */
    private static CloseableHttpClient getClient() {
        return HttpClients.createDefault();
    }

    /**
     * 发送Post请求 请求体为JSON
     *
     * @param url  地址
     * @param body 请求体
     * @return 响应结果
     */
    public static String postJson(String url, String body) throws IOException {
        log.debug("Http Api POST :[{}], body:[{}]", url, body);
        try (CloseableHttpClient client = getClient()) {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(body, DEFAULT_JSON_CHARSET);
            entity.setContentType(ApplicationConstants.Http.CONTENT_TYPE_JSON_UTF8);
            post.setEntity(entity);
            try (CloseableHttpResponse response = client.execute(post)) {
                return response2String(response);
            }
        }
    }

    /**
     * Http Post
     *
     * @param url url
     * @return response body
     * @throws IOException IOE
     */
    public static String post(String url) throws IOException {
        log.debug("Http Api POST :[{}]", url);
        try (CloseableHttpClient client = getClient()) {
            HttpPost post = new HttpPost(url);
            try (CloseableHttpResponse response = client.execute(post)) {
                return response2String(response);
            }
        }
    }

    /**
     * 发起Get请求
     *
     * @param url 请求地址
     * @return 字符串响应内容
     * @throws IOException IO异常
     */
    public static String get(String url) throws IOException {
        try (CloseableHttpClient client = getClient()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                return response2String(response);
            }
        }
    }

    /**
     * 将Http响应转换为字符串
     *
     * @param response 响应对象
     * @return 字符创
     * @throws IOException IO异常
     */
    private static String response2String(CloseableHttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else {
            throw new IOException("Response status code is " + response.getStatusLine().getStatusCode());
        }
    }
}

