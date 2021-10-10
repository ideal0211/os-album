package club.xyes.os.album.front.token;

import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.web.commons.token.AbstractToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create by 郭文梁 2019/11/23 14:14
 * UserToken
 * User Token
 *
 * @author 郭文梁
 * @data 2019/11/23 14:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToken extends AbstractToken {
    public static final String PREFIX = "X-User/";
    private Integer id;
    private Integer role;

    public UserToken() {
    }

    public UserToken(User user) {
        this.id = user.getId();
        this.role = user.getRole();
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }
}
