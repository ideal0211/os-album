package club.xyes.os.album.service.general;

import club.xyes.os.album.commons.entity.Carousel;
import club.xyes.os.album.service.basic.AbstractService;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create by 郭文梁 2019/11/22 17:46
 * CarouselService
 * 轮播图相关业务行为定义
 *
 * @author 郭文梁
 * @data 2019/11/22 17:46
 */
public interface CarouselService extends AbstractService<Carousel> {
    /**
     * Set Image
     *
     * @param carousel carousel
     * @param file     file
     */
    void setImage(Carousel carousel, MultipartFile file);
}
