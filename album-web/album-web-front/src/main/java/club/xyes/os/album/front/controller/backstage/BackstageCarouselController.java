package club.xyes.os.album.front.controller.backstage;

import club.xyes.os.album.commons.entity.Carousel;
import club.xyes.os.album.commons.exception.BadRequestException;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.service.general.AlbumService;
import club.xyes.os.album.service.general.CarouselService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static club.xyes.os.album.commons.util.ParamChecker.notEmpty;
import static club.xyes.os.album.commons.util.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/11/23 17:45
 * BackstageCarouselController
 * 轮播图管理相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/23 17:45
 */
@RestController
@RequestMapping("/b/api/carousel")
public class BackstageCarouselController extends AbstractEntityController<Carousel> {
    private final CarouselService carouselService;
    private final AlbumService albumService;

    public BackstageCarouselController(CarouselService carouselService,
                                       AlbumService albumService) {
        super(carouselService);
        this.carouselService = carouselService;
        this.albumService = albumService;
    }

    /**
     * 创建轮播图
     *
     * @param param 参数
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<Carousel> create(@RequestBody Carousel param) {
        val carousel = new Carousel();
        checkCreateParam(param, carousel);
        val album = albumService.require(carousel.getAlbumId());
        carousel.setAlbum(album);
        val savedCarousel = carouselService.save(carousel);
        return GeneralResult.ok(savedCarousel);
    }

    /**
     * Check And Copy Create Param
     *
     * @param param    param
     * @param carousel carousel
     */
    private void checkCreateParam(Carousel param, Carousel carousel) {
        val ex = BadRequestException.class;
        notNull(param, ex, "No Params！");
        notEmpty(param.getTitle(), ex, "缺少标题！");
        notNull(param.getAlbumId(), ex, "相册ID必填！");

        carousel.setTitle(param.getTitle());
        carousel.setAlbumId(param.getAlbumId());
    }

    /**
     * 设置轮播图图片
     *
     * @param id   ID
     * @param file image file
     * @return GR
     */
    @PostMapping("/{id}/image")
    public GeneralResult<Carousel> setImage(@PathVariable("id") Integer id,
                                            MultipartFile file) {
        notEmpty(file, BadRequestException.class, "缺少图片！");
        val carousel = carouselService.require(id);
        carouselService.setImage(carousel, file);
        carouselService.resolvePath(carousel);
        return GeneralResult.ok(carousel);
    }

    /**
     * 删除轮播图
     *
     * @param id ID
     * @return GR
     */
    @DeleteMapping("/{id}")
    public GeneralResult<Carousel> delete(@PathVariable("id") Integer id) {
        val carousel = carouselService.require(id);
        carouselService.removeById(id);
        return GeneralResult.ok(carousel);
    }

    /**
     * Find All Carousel(s)
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<Carousel>> findAll() {
        val carousels = carouselService.all();
        carouselService.resolvePath(carousels);
        return GeneralResult.ok(carousels);
    }
}
