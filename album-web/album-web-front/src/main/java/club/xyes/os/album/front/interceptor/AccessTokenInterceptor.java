package club.xyes.os.album.front.interceptor;

import club.xyes.os.album.commons.context.ApplicationConstants;
import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.commons.util.TextUtils;
import club.xyes.os.album.commons.util.encrypt.AccessTokenEncoder;
import club.xyes.os.album.commons.vo.GeneralResult;
import club.xyes.os.album.front.token.UserToken;
import club.xyes.os.album.front.util.UserUtils;
import club.xyes.os.album.web.commons.holder.RequestExtendParamHolder;
import club.xyes.os.album.web.commons.token.AbstractToken;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Create by 郭文梁 2019/6/28 0028 12:58
 * AccessTokenInterceptor
 * 令牌拦截器
 *
 * @author 郭文梁
 * @data 2019/6/28 0028
 */
@Slf4j
public class AccessTokenInterceptor implements HandlerInterceptor {
    private final PathMatcher pathMatcher;
    private final AccessTokenEncoder accessTokenEncoder;

    public AccessTokenInterceptor(PathMatcher pathMatcher,
                                  AccessTokenEncoder accessTokenEncoder) {
        this.pathMatcher = pathMatcher;
        this.accessTokenEncoder = accessTokenEncoder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        obtainTokensFromRequest(request);
        final String path = request.getRequestURI();
        log.debug("Resolve request path [{}] ,method [{}]", path, request.getMethod());
        if (!(pathMatcher.match(ApplicationConstants.Security.FRONT_PATH, path)
                || pathMatcher.match(ApplicationConstants.Security.ADMIN_PATH, path))) {
            return true;
        }
        if (pathMatcher.match(ApplicationConstants.Security.FRONT_OPEN_PATH, path)
                || pathMatcher.match(ApplicationConstants.Security.ADMIN_OPEN_PATH, path)) {
            return true;
        } else {
            final Object userToken = RequestExtendParamHolder.get(ApplicationConstants.Security.USER_PARAM_NAME);
            if (userToken == null) {
                response.setContentType(ApplicationConstants.Http.CONTENT_TYPE_JSON_UTF8);
                response.getWriter().write(buildPermissionDeniedResult());
                return false;
            }
            if (pathMatcher.match(ApplicationConstants.Security.ADMIN_AUTH_PATH, path)) {
                return Objects.equals(UserUtils.requireRole(), User.ROLE_ADMIN);
            }
            return true;
        }
    }

    /**
     * 生成权限被拒绝的返回信息
     *
     * @return GR
     */
    private String buildPermissionDeniedResult() {
        return JSON.toJSONString(GeneralResult.permissionDenied("访问权限被拒绝！"));
    }

    /**
     * 获取全部token
     *
     * @param request 请求对象
     */
    private void obtainTokensFromRequest(HttpServletRequest request) {
        obtainTokenFromRequest(request,
                ApplicationConstants.Security.USER_HEADER_NAME,
                UserToken.class,
                UserToken.PREFIX,
                ApplicationConstants.Security.USER_PARAM_NAME);
    }

    /**
     * 从请求中获取一个token
     *
     * @param request         请求
     * @param headerName      header名称
     * @param klass           token类型
     * @param prefix          token前缀
     * @param extendParamName 扩展参数名
     * @param <T>             token类型
     */
    private <T extends AbstractToken> void obtainTokenFromRequest(HttpServletRequest request,
                                                                  String headerName,
                                                                  Class<T> klass,
                                                                  String prefix,
                                                                  String extendParamName) {
        final String userAccessToken = request.getHeader(headerName);
        final T token = decodeToken(userAccessToken, klass, prefix);
        RequestExtendParamHolder.set(extendParamName, token);
    }

    /**
     * 解码零帕
     *
     * @param token 令牌文本
     * @param klass 令牌类型
     * @param <T>   类型泛型
     * @return 令牌对象
     */
    private <T extends AbstractToken> T decodeToken(String token, Class<T> klass, String prefix) {
        if (TextUtils.isTrimedEmpty(token)) {
            return null;
        }
        return AbstractToken.decode(token, accessTokenEncoder, klass, prefix);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //清空保存的扩展参数
        RequestExtendParamHolder.clear();
    }
}
