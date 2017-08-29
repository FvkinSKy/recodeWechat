package com.zr.wechatProj.service;

import java.util.Map;

/**
 * @author zourui On 10:42 2017/8/29
 * @version 1.0
 */
public interface WeChatIoService {
    String msgHandler(Map<String, String> map) throws Exception;

    String eventHandler(Map<String, String> map) throws Exception;
}
