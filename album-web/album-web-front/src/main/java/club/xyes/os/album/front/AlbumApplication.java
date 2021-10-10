package club.xyes.os.album.front;

import club.xyes.os.album.commons.context.ContextConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create by 郭文梁 2019/11/20 18:18
 * AlbumApplication
 * Album Application Entry
 *
 * @author 郭文梁
 * @data 2019/11/20 18:18
 */
@MapperScan(ContextConstants.MAPPER_PACKAGE)
@SpringBootApplication(scanBasePackages = ContextConstants.BASE_PACKAGE)
public class AlbumApplication {
    /**
     * Album Application
     *
     * @param args command args
     */
    public static void main(String[] args) {
        SpringApplication.run(AlbumApplication.class, args);
    }
}
