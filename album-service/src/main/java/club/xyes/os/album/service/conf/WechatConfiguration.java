package club.xyes.os.album.service.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by 郭文梁 2019/11/23 11:39
 * WechatConfiguration
 * 微信相关配置
 *
 * @author 郭文梁
 * @data 2019/11/23 11:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfiguration {
    /**
     * APPID
     */
    private String appId;
    /**
     * Secret
     */
    private String secret;
}
