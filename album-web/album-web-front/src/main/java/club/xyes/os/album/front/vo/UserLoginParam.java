package club.xyes.os.album.front.vo;

import lombok.Data;

/**
 * Create by 郭文梁 2019/11/23 11:07
 * UserLoginParam
 * 用户登录参数
 *
 * @author 郭文梁
 * @data 2019/11/23 11:07
 */
@Data
public class UserLoginParam {
    /**
     * 微信登录授权Code
     */
    private String code;
    /**
     * 微信头像地址
     */
    private String avatar;
    /**
     * 微信昵称
     */
    private String nickname;
}
