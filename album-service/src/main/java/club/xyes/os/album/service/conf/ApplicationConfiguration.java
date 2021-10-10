package club.xyes.os.album.service.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by 郭文梁 2019/11/22 17:53
 * ApplicationConfiguration
 * 应用相关配置
 *
 * @author 郭文梁
 * @data 2019/11/22 17:53
 */
@Component
@Data
@ConfigurationProperties(prefix = "app")
public class ApplicationConfiguration {
    /**
     * 应用工作目录
     */
    private String workspace;
    /**
     * CDN服务器前缀
     */
    private String cdnServer;
    /**
     * 相册封面
     */
    private String albumCoversPath = "/album/covers/";
    /**
     * 相册图片
     */
    private String albumImagesPath = "/album/images/";
    /**
     * 轮播图保存路径
     */
    private String carouselImagePath = "/carousel/image/";
}
