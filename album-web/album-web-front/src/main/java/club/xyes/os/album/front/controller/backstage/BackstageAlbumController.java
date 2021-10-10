package club.xyes.os.album.front.controller.backstage;

import club.xyes.os.album.commons.entity.Album;
import club.xyes.os.album.commons.exception.BadRequestException;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.service.general.AlbumCategoryService;
import club.xyes.os.album.service.general.AlbumService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static club.xyes.os.album.commons.util.ParamChecker.notEmpty;
import static club.xyes.os.album.commons.util.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/11/23 15:07
 * BackstageAlbumController
 * 相册管理相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/23 15:07
 */
@RestController
@RequestMapping("/b/api/album")
public class BackstageAlbumController extends AbstractEntityController<Album> {
    private final AlbumService albumService;
    private final AlbumCategoryService albumCategoryService;

    /**
     * 构造时指定业务组件
     *
     * @param albumService 业务组件
     */
    public BackstageAlbumController(AlbumService albumService,
                                    AlbumCategoryService albumCategoryService) {
        super(albumService);
        this.albumService = albumService;
        this.albumCategoryService = albumCategoryService;
    }

    /**
     * 创建相册
     *
     * @param param 参数
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<Album> create(@RequestBody Album param) {
        val album = new Album();
        checkCreateParam(param, album);
        val category = albumCategoryService.require(album.getCategoryId());
        album.setCategory(category);
        album.setShowOnHome(false);
        val savedAlbum = albumService.save(album);
        return GeneralResult.ok(savedAlbum);
    }

    /**
     * Check And Copy Create Album Param
     *
     * @param param  param
     * @param target copy target
     */
    private void checkCreateParam(Album param, Album target) {
        val ex = BadRequestException.class;
        notNull(param, ex, "No Param!");
        notEmpty(param.getTitle(), ex, "缺少标题！");
        notNull(param.getCategoryId(), ex, "未指定分类！");

        target.setCategoryId(param.getCategoryId());
        target.setTitle(param.getTitle());
        target.setSubTitle(param.getSubTitle());
        target.setDescription(param.getDescription());
        target.setOrderNum(param.getOrderNum() == null ? Album.DEFAULT_ORDER_NUM : param.getOrderNum());
    }

    /**
     * 设置封面
     *
     * @param id   ID
     * @param file 图片
     * @return GR
     */
    @PostMapping("/{id}/covers")
    public GeneralResult<Album> setCover(@PathVariable("id") Integer id,
                                         MultipartFile file) {
        notEmpty(file, BadRequestException.class, "缺少图片！");
        val album = albumService.require(id);
        albumService.setCovers(album, file);
        albumService.resolvePath(album);
        return GeneralResult.ok(album);
    }

    /**
     * 添加子图片
     *
     * @param id   ID
     * @param file 图片文件
     * @return GR
     */
    @PostMapping("/{id}/images")
    public GeneralResult<Album> appendImage(@PathVariable("id") Integer id,
                                            MultipartFile file) {
        notEmpty(file, BadRequestException.class, "缺少图片！");
        val album = albumService.require(id);
        albumService.appendImage(album, file);
        albumService.resolvePath(album);
        return GeneralResult.ok(album);
    }

    /**
     * 删除图片
     *
     * @param id    ID
     * @param index 图片索引
     * @return GR
     */
    @DeleteMapping("/{id}/images")
    public GeneralResult<Album> deleteImage(@PathVariable("id") Integer id,
                                            @RequestParam("index") Integer index) {
        val album = albumService.require(id);
        albumService.deleteImage(album, index);
        albumService.resolvePath(album);
        return GeneralResult.ok(album);
    }

    /**
     * 更新相册
     *
     * @param id    ID
     * @param param 参数
     * @return GR
     */
    @PostMapping("/{id}")
    public GeneralResult<Album> update(@PathVariable("id") Integer id,
                                       @RequestBody Album param) {
        val album = albumService.require(id);
        checkUpdateParam(param, album);
        val updateRes = albumService.updateById(album);
        return GeneralResult.ok(updateRes);
    }

    /**
     * Check And Copy Update Album Param
     *
     * @param param  param
     * @param target copy target
     */
    private void checkUpdateParam(Album param, Album target) {
        val ex = BadRequestException.class;
        notNull(param, ex, "No Params!");
        notEmpty(param.getTitle(), ex, "标题必填！");

        target.setTitle(param.getTitle());
        target.setSubTitle(param.getSubTitle());
        target.setDescription(param.getDescription());
        target.setOrderNum(param.getOrderNum() == null ? Album.DEFAULT_ORDER_NUM : param.getOrderNum());
    }

    /**
     * 删除相册
     *
     * @param id ID
     * @return GR
     */
    @DeleteMapping("/{id}")
    public GeneralResult<Void> delete(@PathVariable("id") Integer id) {
        val album = albumService.require(id);
        albumService.removeById(album.getId());
        return GeneralResult.ok();
    }

    /**
     * Find By Category
     *
     * @param categoryId category Id
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<Album>> findByCategory(@RequestParam("category") Integer categoryId) {
        val albums = albumService.findByCategory(categoryId);
        albumService.resolvePath(albums);
        return GeneralResult.ok(albums);
    }

    /**
     * 设置主页是否显示
     *
     * @param id    ID
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/showOnHome")
    public GeneralResult<Album> setShowOnHome(@PathVariable("id") Integer id,
                                              @RequestBody Album param) {
        notNull(param, BadRequestException.class, "No Request Body!");
        notNull(param.getShowOnHome(), BadRequestException.class, "请指定是否显示在主页！");
        val album = albumService.require(id);
        if (!Objects.equals(param.getShowOnHome(), album.getShowOnHome())) {
            album.setShowOnHome(param.getShowOnHome());
            albumService.updateById(album);
        }
        albumService.resolvePath(album);
        return GeneralResult.ok(album);
    }

    /**
     * 设置相册分类
     *
     * @param id    ID
     * @param param params
     * @return GR
     */
    @PostMapping("/{id}/category")
    public GeneralResult<Album> setCategory(@PathVariable("id") Integer id,
                                            @RequestBody Album param) {
        notNull(param, BadRequestException.class, "No Param!");
        notNull(param.getCategoryId(), BadRequestException.class, "请指定分类！");
        val category = albumCategoryService.require(param.getCategoryId());
        val album = albumService.require(id);
        album.setCategoryId(category.getId());
        album.setCategory(category);
        albumService.updateById(album);
        return GeneralResult.ok(album);
    }
}
