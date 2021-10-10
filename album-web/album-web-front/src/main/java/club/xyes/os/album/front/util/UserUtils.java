package club.xyes.os.album.front.util;

import club.xyes.os.album.commons.context.ApplicationConstants;
import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.commons.exception.PermissionDeniedException;
import club.xyes.os.album.front.token.UserToken;
import club.xyes.os.album.service.general.UserService;
import club.xyes.os.album.web.commons.holder.RequestExtendParamHolder;
import lombok.val;

/**
 * Create by 郭文梁 2019/11/23 14:54
 * UserUtils
 * 用户相关工具类
 *
 * @author 郭文梁
 * @data 2019/11/23 14:54
 */
public class UserUtils {
    /**
     * 获取当前令牌
     *
     * @return Token
     */
    public static UserToken getCurrentToken() {
        val param = RequestExtendParamHolder.get(ApplicationConstants.Security.USER_PARAM_NAME);
        if (!(param instanceof UserToken)) {
            return null;
        }
        return (UserToken) param;
    }

    /**
     * Require Token
     *
     * @return token
     */
    public static UserToken requireCurrentToken() {
        val token = getCurrentToken();
        if (token == null) {
            throw new PermissionDeniedException("无有效访问令牌！");
        }
        return token;
    }

    /**
     * Require UserId
     *
     * @return UserId
     */
    public static Integer requireId() {
        return requireCurrentToken().getId();
    }

    /**
     * Require User Role
     *
     * @return role
     */
    public static Integer requireRole() {
        return requireCurrentToken().getRole();
    }

    /**
     * Request User
     *
     * @param userService user service
     * @return user
     */
    public static User requireUser(UserService userService) {
        return userService.require(requireId());
    }
}
