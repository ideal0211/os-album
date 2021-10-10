package club.xyes.os.album.front.controller.backstage;

import club.xyes.os.album.commons.entity.AlbumCategory;
import club.xyes.os.album.commons.exception.BadRequestException;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.service.general.AlbumCategoryService;
import club.xyes.os.album.service.general.AlbumService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static club.xyes.os.album.commons.util.ParamChecker.notEmpty;
import static club.xyes.os.album.commons.util.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/11/21 18:24
 * BackstageAlbumCategoryController
 * 后台相册分类相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/21 18:24
 */
@RestController
@RequestMapping("/b/api/category")
public class BackstageAlbumCategoryController extends AbstractEntityController<AlbumCategory> {
    private static final int DEFAULT_CATEGORY_ID = 1;
    private final AlbumService albumService;
    private final AlbumCategoryService albumCategoryService;

    /**
     * 构造时指定业务组件
     *
     * @param albumCategoryService 业务组件
     */
    public BackstageAlbumCategoryController(AlbumCategoryService albumCategoryService,
                                            AlbumService albumService) {
        super(albumCategoryService);
        this.albumCategoryService = albumCategoryService;
        this.albumService = albumService;
    }

    /**
     * 创建相册分类
     *
     * @param param params
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<AlbumCategory> create(@RequestBody AlbumCategory param) {
        val category = new AlbumCategory();
        checkAndCopyCreateParam(param, category);
        val saveRes = albumCategoryService.save(category);
        return GeneralResult.ok(saveRes);
    }

    /**
     * Check And Copy Create AlbumCategory
     *
     * @param param  param
     * @param target target
     */
    private void checkAndCopyCreateParam(AlbumCategory param, AlbumCategory target) {
        val ex = BadRequestException.class;
        notNull(param, ex, "No Params!");
        notEmpty(param.getName(), ex, "分类名称必填！");

        target.setName(param.getName());
        target.setOrderNum(param.getOrderNum() == null ? AlbumCategory.DEFAULT_ORDER_NUM : param.getOrderNum());
    }

    /**
     * 获取全部相册分类
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<AlbumCategory>> all() {
        val list = albumCategoryService.all();
        return GeneralResult.ok(list);
    }

    /**
     * 删除相册分类
     *
     * @param id ID
     * @return GR
     */
    @DeleteMapping("/{id}")
    public GeneralResult<AlbumCategory> delete(@PathVariable("id") Integer id) {
        if (id == DEFAULT_CATEGORY_ID) {
            throw new BadRequestException("默认分类不允许被删除！");
        }
        val category = albumCategoryService.require(id);
        albumCategoryService.removeById(id);
        albumService.moveCategory(id, AlbumCategory.DEFAULT_CATEGORY_ID);
        return GeneralResult.ok(category);
    }

    /**
     * 更新分类信息
     *
     * @param id    ID
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}")
    public GeneralResult<AlbumCategory> update(@PathVariable("id") Integer id,
                                               @RequestBody AlbumCategory param) {
        val category = albumCategoryService.require(id);
        checkUpdateParam(param, category);
        albumCategoryService.updateById(category);
        return GeneralResult.ok(category);
    }

    /**
     * Check And Copy Param for update
     *
     * @param param    param
     * @param category category
     */
    private void checkUpdateParam(AlbumCategory param, AlbumCategory category) {
        val ex = BadRequestException.class;
        notNull(param, ex, "No Request Body!");
        notEmpty(param.getName(), ex, "缺少名称！");

        category.setName(param.getName());
        if (param.getOrderNum() != null) {
            category.setOrderNum(param.getOrderNum());
        }
    }
}
