package club.xyes.os.album.front.interceptor.config;

import club.xyes.os.album.commons.util.encrypt.AccessTokenEncoder;
import club.xyes.os.album.front.interceptor.AccessTokenInterceptor;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create by 郭文梁 2019/5/20 0020 19:03
 * InterceptorConfig
 * 拦截器配置
 *
 * @author 郭文梁
 * @data 2019/5/20 0020
 */
@ConfigurationProperties(prefix = "interceptor")
@Configuration
@Component
public class InterceptorConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Setter
    private boolean requireTokenInterceptor = true;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (requireTokenInterceptor) {
            AccessTokenEncoder tokenEncoder = applicationContext.getBean(AccessTokenEncoder.class);
            PathMatcher pathMatcher = applicationContext.getBean(PathMatcher.class);
            registry.addInterceptor(new AccessTokenInterceptor(pathMatcher, tokenEncoder));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
