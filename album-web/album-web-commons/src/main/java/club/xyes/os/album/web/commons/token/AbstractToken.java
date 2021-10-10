package club.xyes.os.album.web.commons.token;

import club.xyes.os.album.commons.util.encrypt.AccessTokenEncoder;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by 郭文梁 2019/6/28 0028 11:22
 * AbstractToken
 * 令牌抽象类
 *
 * @author 郭文梁
 * @data 2019/6/28 0028
 */
@Slf4j
public abstract class AbstractToken {
    /**
     * 解码令牌
     *
     * @param text    令牌文本
     * @param encoder 编解码器
     * @param klass   目标类型
     * @param <T>     类型泛型
     * @param prefix  前缀
     * @return token对象
     */
    public static <T extends AbstractToken> T decode(String text, AccessTokenEncoder encoder, Class<T> klass, String prefix) {
        if (text.length() <= prefix.length()) {
            return null;
        }
        text = text.substring(prefix.length());
        try {
            return encoder.decode(text, klass);
        } catch (Exception e) {
            log.debug("Could not unpack token [{}]", text);
            return null;
        }
    }

    /**
     * 获取令牌前缀 由子类实现
     *
     * @return 前缀
     */
    protected abstract String getPrefix();

    /**
     * 转换为字符串文本
     *
     * @param encoder 编码器
     * @return 令牌文本
     */
    public String asText(AccessTokenEncoder encoder) {
        return encoder.encode(JSON.toJSONString(this), getPrefix());
    }
}
