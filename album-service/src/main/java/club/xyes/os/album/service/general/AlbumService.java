package club.xyes.os.album.service.general;

import club.xyes.os.album.commons.entity.Album;
import club.xyes.os.album.service.basic.AbstractService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/22 17:49
 * AlbumService
 * 相册相关业务行为定义
 *
 * @author 郭文梁
 * @data 2019/11/22 17:49
 */
public interface AlbumService extends AbstractService<Album> {
    /**
     * Set Covers
     *
     * @param album album
     * @param file  covers file
     */
    void setCovers(Album album, MultipartFile file);

    /**
     * Append Image
     *
     * @param album album
     * @param file  image file
     */
    void appendImage(Album album, MultipartFile file);

    /**
     * Delete Image
     *
     * @param album album
     * @param index image index
     */
    void deleteImage(Album album, Integer index);

    /**
     * 移动相册到分类
     *
     * @param oldCategory 旧分类
     * @param newCategory 新分类
     */
    void moveCategory(Integer oldCategory, int newCategory);

    /**
     * Find Album By Category
     *
     * @param categoryId category ID
     * @return Album List
     */
    List<Album> findByCategory(Integer categoryId);

    /**
     * Find Albums order by create desc
     *
     * @param max max result size
     * @return Album list
     */
    List<Album> newest(Integer max);

    /**
     * 最后一次更新时间
     *
     * @return date object
     */
    Date maxUpdateTime();

    /**
     * Home Albums
     *
     * @param page page
     * @param rows rows
     * @return GR
     */
    PageInfo<Album> homeAlbums(Integer page, Integer rows, String title);
}
