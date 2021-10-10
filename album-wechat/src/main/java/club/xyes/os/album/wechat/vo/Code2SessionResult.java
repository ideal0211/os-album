package club.xyes.os.album.wechat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Create by 郭文梁 2019/4/17 0017 15:20
 * Code2SessionResult
 * Code换Session API返回数据格式
 *
 * @author 郭文梁
 * @data 2019/4/17 0017
 */
@Data
public class Code2SessionResult {
    /**
     * openid
     */
    private String openid;
    /**
     * session_key
     */
    @JsonProperty("session_key")
    private String sessionKey;
    /**
     * unionid
     */
    private String unionid;
    /**
     * errcode
     */
    private Integer errcode;
    /**
     * errmsg
     */
    private String errmsg;
}
