package club.xyes.os.album.wechat.util;

import club.xyes.os.album.commons.context.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Create by 郭文梁 2019/4/19 0019 12:07
 * Md5SignUtil
 * MD5签名工具类
 *
 * @author 郭文梁
 * @data 2019/4/19 0019
 */
@Slf4j
public class Md5SignUtil {
    private static final String MICRO_PROGRAM_SIGN_TEXT = "pay_ver=%s&pay_type=%s&service_id=%s&merchant_no=%s&terminal_id=%s&terminal_trace=%s&terminal_time=%s&total_fee=%s&access_token=%s";
    private static final String TRADE_QUERY_SIGN_TEXT = "pay_ver=%s&pay_type=%s&service_id=%s&merchant_no=%s&terminal_id=%s&terminal_trace=%s&terminal_time=%s&out_trade_no=%s&access_token=%s";
    private static final String REFUND_SIGN_TEXT = "pay_ver=%s&pay_type=%s&service_id=%s&merchant_no=%s&terminal_id=%s&terminal_trace=%s&terminal_time=%s&refund_fee=%s&out_trade_no=%s&access_token=%s";

    /**
     * 微信小程序支付参数签名
     *
     * @param params      参数
     * @param accessToken 令牌
     * @return 签名
     */
    public static String signMicroProgramParam(Map<String, String> params, String accessToken) {
        String signText = String.format(MICRO_PROGRAM_SIGN_TEXT,
                params.get("pay_ver"),
                params.get("pay_type"),
                params.get("service_id"),
                params.get("merchant_no"),
                params.get("terminal_id"),
                params.get("terminal_trace"),
                params.get("terminal_time"),
                params.get("total_fee"),
                accessToken
        );
        try {
            return DigestUtils.md5Hex(signText.getBytes(ApplicationConstants.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签名算法
     *
     * @param source      参数
     * @param accessToken 令牌
     * @return 签名结果
     */
    public static String sign(Map<String, String> source, String accessToken) {
        List<String> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : source.entrySet()) {
            params.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        Collections.sort(params);
        StringBuilder sb = new StringBuilder();
        for (String p : params) {
            sb.append(p).append('&');
        }
        sb.append("access_token=").append(accessToken);
        String signText = sb.toString();
        try {
            return DigestUtils.md5Hex(signText.getBytes(ApplicationConstants.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对交易查询参数签名
     *
     * @param params      参数
     * @param accessToken 令牌
     */
    public static String signTradeQueryParam(Map<String, String> params, String accessToken) {
        String signText = String.format(TRADE_QUERY_SIGN_TEXT,
                params.get("pay_ver"),
                params.get("pay_type"),
                params.get("service_id"),
                params.get("merchant_no"),
                params.get("terminal_id"),
                params.get("terminal_trace"),
                params.get("terminal_time"),
                params.get("out_trade_no"),
                accessToken
        );
        try {
            return DigestUtils.md5Hex(signText.getBytes(ApplicationConstants.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对退款参数签名
     *
     * @param param       参数
     * @param accessToken 令牌
     * @return 签名
     */
    public static String signRefundParam(Map<String, String> param, String accessToken) {
        String signText = String.format(REFUND_SIGN_TEXT,
                param.get("pay_ver"),
                param.get("pay_type"),
                param.get("service_id"),
                param.get("merchant_no"),
                param.get("terminal_id"),
                param.get("terminal_trace"),
                param.get("terminal_time"),
                param.get("refund_fee"),
                param.get("out_trade_no"),
                accessToken
        );
        try {
            return DigestUtils.md5Hex(signText.getBytes(ApplicationConstants.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
