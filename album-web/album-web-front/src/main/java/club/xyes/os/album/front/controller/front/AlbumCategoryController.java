package club.xyes.os.album.front.controller.front;

import club.xyes.os.album.commons.entity.AlbumCategory;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.service.general.AlbumCategoryService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by 郭文梁 2019/11/21 18:22
 * AlbumCategoryController
 * 相册分类相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/21 18:22
 */
@RestController
@RequestMapping("/f/api/category")
public class AlbumCategoryController extends AbstractEntityController<AlbumCategory> {
    private final AlbumCategoryService albumCategoryService;

    /**
     * 构造时指定业务组件
     *
     * @param albumCategoryService 业务组件
     */
    public AlbumCategoryController(AlbumCategoryService albumCategoryService) {
        super(albumCategoryService);
        this.albumCategoryService = albumCategoryService;
    }

    /**
     * 全部分类
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<AlbumCategory>> all() {
        val categoryList = albumCategoryService.all();
        return GeneralResult.ok(categoryList);
    }
}
