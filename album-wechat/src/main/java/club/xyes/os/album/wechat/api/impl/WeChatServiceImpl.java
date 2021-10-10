package club.xyes.os.album.wechat.api.impl;

import club.xyes.os.album.commons.exception.InternalServerErrorException;
import club.xyes.os.album.wechat.api.WeChatService;
import club.xyes.os.album.wechat.util.HttpUtil;
import club.xyes.os.album.wechat.vo.Code2SessionResult;
import club.xyes.os.album.wechat.vo.WxAccessToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by 郭文梁 2019/4/17 0017 15:24
 * WeChatServiceImpl
 * 微信相关API实现
 *
 * @author 郭文梁
 * @data 2019/4/17 0017
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {
    private static final String ACCESS_TOKEN_ERR_CODE_KEY = "errcode";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String CODE_2_SESSION_API_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    private Map<String, WxAccessToken> localAccessTokenCache = new ConcurrentHashMap<>();


    @Override
    public Code2SessionResult code2Session(String appid, String secret, String code) {
        String apiUrl = String.format(CODE_2_SESSION_API_URL, appid, secret, code);
        Code2SessionResult result;
        String resp;
        try {
            resp = HttpUtil.get(apiUrl);
            result = JSON.parseObject(resp, Code2SessionResult.class);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error(IOException) On Request API(GET): [" + apiUrl + "]!", e);
        }
        if (result == null || result.getOpenid() == null) {
            throw new InternalServerErrorException("Failure to parse wechat server returned data:" + resp);
        }
        return result;
    }


    @Override
    public WxAccessToken getAccessToken(String appId, String secret) throws IOException {
        WxAccessToken lastToken = localAccessTokenCache.get(appId);
        if (lastToken != null && !lastToken.expired()) {
            return lastToken;
        }
        String tokenUrl = String.format(ACCESS_TOKEN_URL, appId, secret);
        String response = HttpUtil.get(tokenUrl);
        JSONObject jsonRes = JSON.parseObject(response);
        if (jsonRes.get(ACCESS_TOKEN_ERR_CODE_KEY) == null) {
            lastToken = jsonRes.toJavaObject(WxAccessToken.class);
            lastToken.setRefreshTime(System.currentTimeMillis() / 1000);
            localAccessTokenCache.put(appId, lastToken);
        } else {
            throw new InterruptedIOException("Unknown response json:" + response);
        }
        return lastToken;
    }
}
