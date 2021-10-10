package club.xyes.os.album.front.controller.front.open;

import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.commons.exception.BadRequestException;
import club.xyes.os.album.commons.util.encrypt.AccessTokenEncoder;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.front.token.UserToken;
import club.xyes.os.album.front.vo.UserLoginParam;
import club.xyes.os.album.service.general.UserService;
import club.xyes.os.album.web.commons.controller.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static club.xyes.os.album.commons.util.ParamChecker.notEmpty;
import static club.xyes.os.album.commons.util.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/11/23 14:21
 * OpenUserController
 * 用户开放数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/23 14:21
 */
@RestController
@RequestMapping("/f/open/user")
public class OpenUserController extends AbstractEntityController<User> {
    private final UserService userService;
    private final AccessTokenEncoder accessTokenEncoder;

    /**
     * 构造时指定业务组件
     *
     * @param userService 业务组件
     */
    public OpenUserController(UserService userService, AccessTokenEncoder accessTokenEncoder) {
        super(userService);
        this.userService = userService;
        this.accessTokenEncoder = accessTokenEncoder;
    }

    /**
     * 登录
     *
     * @param param 参数
     * @return GR
     */
    @PostMapping("/login")
    public GeneralResult<Map<String, Object>> loginByCode(@RequestBody UserLoginParam param) {
        checkLoginParam(param);
        val user = userService.loginByCodeAndCompleteInfo(param.getCode(), param.getNickname(), param.getAvatar());
        val token = asToken(user);
        val resMap = new HashMap<String, Object>(16);
        resMap.put("user", user);
        resMap.put("token", token);
        return GeneralResult.ok(resMap);
    }

    /**
     * User To Token
     *
     * @param user user
     * @return token
     */
    private String asToken(User user) {
        return new UserToken(user).asText(accessTokenEncoder);
    }

    /**
     * 检查登录参数
     *
     * @param param 参数
     */
    private void checkLoginParam(UserLoginParam param) {
        final Class<BadRequestException> ex = BadRequestException.class;
        notNull(param, ex, "参数未传");
        notEmpty(param.getCode(), ex, "code必填");
    }

    /**
     * Mock Login
     *
     * @param id ID
     * @return GR
     */
    @GetMapping("/{id}/login")
    public GeneralResult<Map<String, Object>> mockLogin(@PathVariable("id") Integer id) {
        val user = userService.require(id);
        val token = asToken(user);
        val resMap = new HashMap<String, Object>(16);
        resMap.put("user", user);
        resMap.put("token", token);
        return GeneralResult.ok(resMap);
    }
}
