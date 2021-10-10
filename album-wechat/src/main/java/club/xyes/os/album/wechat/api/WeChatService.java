package club.xyes.os.album.wechat.api;

import club.xyes.os.album.wechat.vo.Code2SessionResult;
import club.xyes.os.album.wechat.vo.WxAccessToken;

import java.io.IOException;

/**
 * Create by 郭文梁 2019/4/17 0017 14:51
 * WeChatService
 * 微信相关API服务
 *
 * @author 郭文梁
 * @data 2019/4/17 0017
 */
public interface WeChatService {
    /**
     * code 换openid
     *
     * @param appId  appId
     * @param secret secret
     * @param code   临时code
     * @return Code2SessionResult
     */
    Code2SessionResult code2Session(String appId, String secret, String code);

    /**
     * 获取AccessToken
     *
     * @param appId  AppId
     * @param secret Secret
     * @return AccessToken
     * @throws IOException IO异常
     */
    WxAccessToken getAccessToken(String appId, String secret) throws IOException;
}
