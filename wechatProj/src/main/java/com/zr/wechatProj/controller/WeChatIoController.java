package com.zr.wechatProj.controller;

import com.zr.wechatProj.dto.WeChatDataDto;
import com.zr.wechatProj.util.WeChatCheckTool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zourui On 14:37 2017/8/28
 * @version 1.0
 */
@Controller
@RequestMapping("/weChat")
public class WeChatIoController {

    private static Logger log = Logger.getLogger(WeChatIoController.class);

    private static final String ACCESS_TOKEN = "zrwechat001";

    /**
     * 测试log4j配置
     */
    @RequestMapping("/getLog")
    public void testLog(HttpServletResponse res) throws IOException {
        res.getWriter().print("123");
        log.info("66666");
    }

    /**
     * 验证微信服务器发送的数据
     * 原样返回echostr完成验证
     *
     * @param dto 微信加密签名验证参数
     */
    @RequestMapping(value = "/io", method = RequestMethod.GET)
    public void check(WeChatDataDto dto, HttpServletRequest req, HttpServletResponse res) {
        try {
            req.setCharacterEncoding("UTF-8");
            res.setCharacterEncoding("UTF-8");

            boolean isCheck = WeChatCheckTool.checkData(dto, ACCESS_TOKEN);
            if (isCheck) {
                OutputStream os = res.getOutputStream();
                os.write(dto.getEchostr().getBytes("UTF-8"));
                os.flush();
                log.info("successful access");
            }
        } catch (Exception e) {
            log.error("微信服务器验证异常，错误信息为:" + e);
            e.printStackTrace();
        }
    }


    /**
     * 接收微信服务器转发的消息并处理
     */
    @RequestMapping(value = "/io", method = RequestMethod.POST)
    public void messageHandler(HttpServletRequest req, HttpServletResponse res) {

    }

}
