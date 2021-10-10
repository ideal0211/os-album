package club.xyes.os.album.repository.mapper;

import club.xyes.os.album.commons.entity.Album;
import club.xyes.os.album.repository.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/21 20:21
 * AlbumMapper
 * 相册相关数据库访问组件
 *
 * @author 郭文梁
 * @data 2019/11/21 20:21
 */
@Repository
public interface AlbumMapper extends AbstractMapper<Album> {
    /**
     * update Album CategoryId By CategoryId
     *
     * @param oldCategory old category
     * @param newCategory new category
     * @return rows
     */
    int updateCategoryIdByCategoryId(@Param("oldCategory") Integer oldCategory,
                                     @Param("newCategory") int newCategory);

    /**
     * Select Albums order by create_time desc
     *
     * @param max max result size
     * @return Album List
     */
    List<Album> selectAllOrderByCreateTimeDesc(@Param("max") Integer max);

    /**
     * Max Update Time
     *
     * @return date
     */
    Date maxUpdateTime();

    /**
     * Select Home Albums
     *
     * @return Albums
     */
    List<Album> selectHomeAlbums(@Param("title") String title);

    /**
     * Select By Category
     *
     * @param categoryId category ID
     * @return Albums
     */
    List<Album> selectByCategory(@Param("categoryId") Integer categoryId);
}
