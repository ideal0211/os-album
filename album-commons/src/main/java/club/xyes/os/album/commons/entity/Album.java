package club.xyes.os.album.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/21 20:00
 * Album
 * Album
 * 相册实体对象
 *
 * @author 郭文梁
 * @data 2019/11/21 20:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_album")
public class Album extends AbstractEntity {
    public static final int DEFAULT_ORDER_NUM = 1;
    /**
     * 类别ID
     */
    @Column(name = "category_id", length = 10, nullable = false)
    private Integer categoryId;
    /**
     * 分类对象
     */
    private AlbumCategory category;
    /**
     * 标题
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 副标题
     */
    @Column(name = "sub_title")
    private String subTitle;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 相册封面
     */
    @Column(name = "covers")
    private String covers;
    /**
     * 相册子图片
     */
    @Column(name = "image_json", length = 1000)
    private String imageJson;
    /**
     * 图片列表
     */
    private List<String> images;
    /**
     * 排序号
     */
    @Column(name = "order_num", length = 10, nullable = false)
    private Integer orderNum;
    /**
     * 是否在主页显示
     */
    @Column(name = "show_on_home", nullable = false)
    private Boolean showOnHome;
}
