package club.xyes.os.album.service.general.impl;

import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.commons.util.TextUtils;
import club.xyes.os.album.repository.mapper.UserMapper;
import club.xyes.os.album.service.basic.impl.AbstractServiceImpl;
import club.xyes.os.album.service.conf.WechatConfiguration;
import club.xyes.os.album.service.general.UserService;
import club.xyes.os.album.wechat.api.WeChatService;
import club.xyes.os.album.wechat.vo.Code2SessionResult;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * Create by 郭文梁 2019/11/20 19:53
 * UserServiceImpl
 * UserService
 * 用户业务组件实现
 *
 * @author 郭文梁
 * @data 2019/11/20 19:53
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
    private final UserMapper userMapper;
    private final WechatConfiguration wechatConfiguration;
    private final WeChatService weChatService;

    public UserServiceImpl(UserMapper userMapper,
                           WechatConfiguration wechatConfiguration,
                           WeChatService weChatService) {
        super(userMapper);
        this.userMapper = userMapper;
        this.wechatConfiguration = wechatConfiguration;
        this.weChatService = weChatService;
    }

    @Override
    public User loginByCodeAndCompleteInfo(String code, String nickname, String avatar) {
        @NotNull final User user = loginByCode(code);
        if (!TextUtils.isTrimedEmpty(nickname) || !TextUtils.isTrimedEmpty(avatar)) {
            completeUserInfo(user, nickname, avatar);
        }
        return user;
    }

    private User loginByCode(String code) {
        final String appId = wechatConfiguration.getAppId();
        final String secret = wechatConfiguration.getSecret();
        final Code2SessionResult code2SessionResult = weChatService.code2Session(appId, secret, code);
        final String openid = code2SessionResult.getOpenid();
        User user = findByOpenId(openid);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setRole(User.ROLE_USER);
            user = save(user);
        } else {
            user = updateById(user);
        }
        return user;
    }

    /**
     * Update User Info
     *
     * @param user     user
     * @param nickName nickname
     * @param avatar   avatar
     */
    private void completeUserInfo(User user, String nickName, String avatar) {
        user.setNickname(nickName);
        user.setAvatar(avatar);
        updateById(user);
    }

    /**
     * Find User By OpenId
     *
     * @param openId openId
     * @return User
     */
    private User findByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }
}
