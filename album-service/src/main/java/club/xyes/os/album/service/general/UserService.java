package club.xyes.os.album.service.general;

import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.service.basic.AbstractService;

/**
 * Create by 郭文梁 2019/11/20 19:52
 * UserService
 * User Service
 * 用户业务组件 行为定义
 *
 * @author 郭文梁
 * @data 2019/11/20 19:52
 */
public interface UserService extends AbstractService<User> {
    /**
     * 通过微信code登录 并且完善用户信息
     *
     * @param code     微信code
     * @param nickname 微信昵称
     * @param avatar   头像地址
     * @return 用户信息
     */
    User loginByCodeAndCompleteInfo(String code, String nickname, String avatar);
}
