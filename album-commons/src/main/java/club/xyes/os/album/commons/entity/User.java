package club.xyes.os.album.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/11/20 19:37
 * User
 * User Entity
 *
 * @author 郭文梁
 * @data 2019/11/20 19:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_user")
public class User extends AbstractEntity {
    /**
     * 角色 用户
     */
    public static final int ROLE_USER = 0x00;
    /**
     * 角色 管理员
     */
    public static final int ROLE_ADMIN = 0x01;
    /**
     * 微信昵称
     */
    @Column(name = "nickname")
    private String nickname;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
    /**
     * 微信 Open Id
     */
    @Column(name = "open_id")
    private String openId;
    /**
     * 角色
     */
    @Column(name = "role")
    private Integer role;
}
