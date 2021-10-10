package club.xyes.os.album.front.controller.front;

import club.xyes.os.album.commons.context.ApplicationConstants;
import club.xyes.os.album.commons.entity.Album;
import club.xyes.os.album.commons.util.DateTimeUtils;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.service.general.AlbumService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import com.github.pagehelper.PageInfo;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by 郭文梁 2019/11/22 18:03
 * AlbumController
 * 相册相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/22 18:03
 */
@RestController
@RequestMapping("/f/api/album")
public class AlbumController extends AbstractEntityController<Album> {
    private final AlbumService albumService;

    /**
     * 构造时指定业务组件
     *
     * @param albumService 业务组件
     */
    public AlbumController(AlbumService albumService) {
        super(albumService);
        this.albumService = albumService;
    }

    /**
     * 查找分类下的相册
     *
     * @param categoryId 分类ID
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<Album>> findByCategory(@RequestParam("category") Integer categoryId) {
        val albums = albumService.findByCategory(categoryId);
        albumService.resolvePath(albums);
        return GeneralResult.ok(albums);
    }

    /**
     * 最新
     *
     * @param page page
     * @param rows rows
     * @return GR
     */
    @GetMapping("/_newest")
    public GeneralResult<Map<String, Object>> newestAlbums(Integer rows, Integer page) {
        page = defaultPage(page);
        rows = defaultRows(rows);
        val albums = albumService.list(page, rows);
        albumService.resolvePath(albums);
        val lastUpdate = albumService.maxUpdateTime();
        val resMap = new HashMap<String, Object>(16);
        resMap.put("albums", albums);
        resMap.put("lastUpdate", DateTimeUtils.format(lastUpdate, ApplicationConstants.DateTime.DATE_TIME_FORMAT));
        return GeneralResult.ok(resMap);
    }

    /**
     * 相册详情
     *
     * @param id ID
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<Album> detail(@PathVariable("id") Integer id) {
        val album = albumService.require(id);
        albumService.resolvePath(album);
        return GeneralResult.ok(album);
    }

    /**
     * 主页相册列表
     *
     * @param page page
     * @param rows rows
     * @param title title
     * @return GE
     */
    @GetMapping("/_home")
        public GeneralResult<PageInfo<Album>> homeAlbums(Integer page, Integer rows, String title) {
        page = defaultPage(page);
        rows = defaultRows(rows);
        val albums = albumService.homeAlbums(page, rows,title);
        albumService.resolvePath(albums);
        return GeneralResult.ok(albums);
    }
}
