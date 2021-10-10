package club.xyes.os.album.commons.context;

import java.time.ZoneId;
import java.util.Locale;

/**
 * Create by 郭文梁 2019/11/20 18:59
 * ApplicationConstants
 * 系统常量表
 *
 * @author 郭文梁
 * @data 2019/11/20 18:59
 */
public class ApplicationConstants {
    /**
     * 系统默认编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 数据库相关常量
     */
    public static class Database {
        /**
         * JDBC主键生成器名称
         */
        public static final String GENERATOR_JDBC = "JDBC";
        /**
         * 降序排序
         */
        public static final String ORDER_DESC = "desc";
        /**
         * 升序排序
         */
        public static final String ORDER_ASC = "asc";
    }

    /**
     * 时间日期相关
     */
    public static class DateTime {
        /**
         * 默认时间如期格式
         */
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        /**
         * 时间格式
         */
        public static final String TIME_FORMAT = "HH:mm:ss";
        /**
         * 时间格式 不带秒
         */
        public static final String TIME_FORMAT_WITHOUT_SECOND = "HH:mm";
        /**
         * 日期格式
         */
        public static final String DATE_FORMAT = "yyyy-MM-dd";
        /**
         * 日期格式化 不带年
         */
        public static final String DATE_FORMAT_WITHOUT_YEAR = "MM-dd";
        /**
         * 默认时区
         */
        public static final ZoneId DEFAULT_TIMEZONE = ZoneId.systemDefault();
        /**
         * 默认地区
         */
        public static final Locale DEFAULT_LOCALE = Locale.CHINA;
    }

    /**
     * Http相关常量
     */
    public static class Http {
        /**
         * 内容类型 JSON 编码 UTF-8
         */
        public static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=utf-8";
        /**
         * 内容类型 xml 编码 UTF-8
         */
        public static final String CONTENT_TYPE_XML_URT8 = "text/xml;charset=utf-8";
    }

    /**
     * 安全相关配置
     */
    public static class Security {
        /**
         * 前台路径
         */
        public static final String FRONT_PATH = "/f/**";
        /**
         * 后台路径
         */
        public static final String ADMIN_PATH = "/b/**";
        /**
         * 前台需授权路径
         */
        public static final String FRONT_AUTH_PATH = "/f/api/**";
        /**
         * 前台开放访问路径
         */
        public static final String FRONT_OPEN_PATH = "/f/open/**";
        /**
         * 后台需授权路径
         */
        public static final String ADMIN_AUTH_PATH = "/b/api/**";
        /**
         * 后台开放访问路径
         */
        public static final String ADMIN_OPEN_PATH = "/b/open/**";
        /**
         * 用户参数名称
         */
        public static final String USER_PARAM_NAME = "album.user";
        /**
         * 用户Token header名称
         */
        public static final String USER_HEADER_NAME = "X-Token";
    }
}
