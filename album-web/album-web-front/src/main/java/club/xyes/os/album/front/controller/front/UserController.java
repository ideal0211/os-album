package club.xyes.os.album.front.controller.front;

import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.commons.util.TextUtils;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.front.util.UserUtils;
import club.xyes.os.album.service.general.UserService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/11/20 18:26
 * UserController
 * User Controller
 * 用户数据
 *
 * @author 郭文梁
 * @data 2019/11/20 18:26
 */
@Slf4j
@RestController
@RequestMapping("/f/api/user")
public class UserController extends AbstractEntityController<User> {
    private final UserService userService;

    /**
     * 构造时指定业务组件
     *
     * @param userService 业务组件
     */
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    /**
     * 更新用户基本信息
     *
     * @param param param
     * @return GR
     */
    @PostMapping("/_update")
    public GeneralResult<User> update(@RequestBody User param) {
        val user = UserUtils.requireUser(userService);
        boolean needUpdate = false;
        if (TextUtils.isTrimedNotEmpty(param.getNickname())) {
            user.setNickname(param.getNickname());
            needUpdate = true;
        }
        if (TextUtils.isTrimedNotEmpty(param.getAvatar())) {
            user.setAvatar(param.getAvatar());
            needUpdate = true;
        }
        if (needUpdate) {
            userService.updateById(user);
        }
        return GeneralResult.ok(user);
    }
}
