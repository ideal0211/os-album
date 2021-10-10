package club.xyes.os.album.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/11/21 18:03
 * AlbumCategory
 * 相册分类数据实体
 *
 * @author 郭文梁
 * @data 2019/11/21 18:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_album_category")
public class AlbumCategory extends AbstractEntity {
    /**
     * 默认分类ID
     */
    public static final int DEFAULT_CATEGORY_ID = 1;
    /**
     * Order Num
     */
    public static final int DEFAULT_ORDER_NUM = 1;
    /**
     * 分类名称
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 排序号
     */
    @Column(name = "order_num", length = 10, nullable = false)
    private Integer orderNum;
}
