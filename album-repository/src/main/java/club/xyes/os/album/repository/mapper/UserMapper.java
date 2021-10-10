package club.xyes.os.album.repository.mapper;

import club.xyes.os.album.commons.entity.User;
import club.xyes.os.album.repository.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create by 郭文梁 2019/11/20 19:33
 * UserMapper
 * User Mapper
 * 用户相关数据库访问组件
 *
 * @author 郭文梁
 * @data 2019/11/20 19:33
 */
@Repository
public interface UserMapper extends AbstractMapper<User> {
    /**
     * Select User By OpenId
     *
     * @param openId openId
     * @return User
     */
    User selectByOpenId(@Param("openId") String openId);
}
