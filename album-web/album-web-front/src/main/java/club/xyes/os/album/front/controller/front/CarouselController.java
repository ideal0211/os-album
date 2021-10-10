package club.xyes.os.album.front.controller.front;

import club.xyes.os.album.commons.entity.Carousel;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.service.general.CarouselService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by 郭文梁 2019/11/23 17:42
 * CarouselController
 * 轮播图相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/23 17:42
 */
@RestController
@RequestMapping("/f/api/carousel")
public class CarouselController extends AbstractEntityController<Carousel> {
    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        super(carouselService);
        this.carouselService = carouselService;
    }

    /**
     * 全部轮播图
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<Carousel>> all() {
        val list = carouselService.all();
        carouselService.resolvePath(list);
        return GeneralResult.ok(list);
    }
}
