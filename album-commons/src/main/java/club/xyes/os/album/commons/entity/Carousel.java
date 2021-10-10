package club.xyes.os.album.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/11/22 17:40
 * Carousel
 * 轮播图数据实体
 *
 * @author 郭文梁
 * @data 2019/11/22 17:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_carousel")
public class Carousel extends AbstractEntity {
    /**
     * 标题
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 图片
     */
    @Column(name = "image")
    private String image;
    /**
     * 相册ID
     */
    @Column(name = "album_id", length = 10)
    private Integer albumId;
    /**
     * 相册
     */
    private Album album;
}
