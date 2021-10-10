package club.xyes.os.album.service.general.impl;

import club.xyes.os.album.commons.entity.Carousel;
import club.xyes.os.album.commons.exception.InternalServerErrorException;
import club.xyes.os.album.commons.util.MultipartFileUtil;
import club.xyes.os.album.commons.util.TextUtils;
import club.xyes.os.album.repository.mapper.CarouselMapper;
import club.xyes.os.album.service.basic.impl.AbstractServiceImpl;
import club.xyes.os.album.service.conf.ApplicationConfiguration;
import club.xyes.os.album.service.general.CarouselService;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/22 17:47
 * CarouselServiceImpl
 * 轮播图相关业务行为实现
 *
 * @author 郭文梁
 * @data 2019/11/22 17:47
 */
@Service
public class CarouselServiceImpl extends AbstractServiceImpl<Carousel> implements CarouselService {
    private final CarouselMapper carouselMapper;
    private final ApplicationConfiguration applicationConfiguration;

    public CarouselServiceImpl(CarouselMapper carouselMapper,
                               ApplicationConfiguration applicationConfiguration) {
        super(carouselMapper);
        this.carouselMapper = carouselMapper;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public <T extends Carousel> void resolvePath(T e) {
        val root = applicationConfiguration.getCdnServer();
        if (TextUtils.isTrimedNotEmpty(e.getImage())) {
            e.setImage(root + applicationConfiguration.getCarouselImagePath() + e.getImage());
        }
    }

    @Override
    public List<Carousel> all() {
        val query = new Carousel();
        query.setDeleted(false);
        return findByQuery(query);
    }

    @Override
    public void setImage(Carousel carousel, MultipartFile file) {
        val path = applicationConfiguration.getWorkspace() + applicationConfiguration.getCarouselImagePath();
        try {
            val filename = MultipartFileUtil.save(file, path);
            carousel.setImage(filename);
            updateById(carousel);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error On Save Carousel image!", e);
        }
    }
}
