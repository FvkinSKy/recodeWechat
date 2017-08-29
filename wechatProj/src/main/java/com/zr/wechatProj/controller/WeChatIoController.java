package com.zr.wechatProj.controller;

import com.zr.wechatProj.dto.WeChatDataDto;
import com.zr.wechatProj.service.WeChatIoService;
import com.zr.wechatProj.util.WeChatCheckTool;
import com.zr.wechatProj.util.WeChatParseTool;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author zourui On 14:37 2017/8/28
 * @version 1.0
 */
@Controller
@RequestMapping("/weChat")
public class WeChatIoController {

    private static Logger log = Logger.getLogger(WeChatIoController.class);

    private static final String MY_TOKEN = "zrwechat001";

    @Resource(name = "weChatIoService")
    private WeChatIoService service;

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

            boolean isCheck = WeChatCheckTool.checkData(dto, MY_TOKEN);
            if (isCheck) {
                OutputStream os = res.getOutputStream();
                os.write(dto.getEchostr().getBytes("UTF-8"));
                os.flush();
                log.info("successful access");
            }
        } catch (Exception e) {
            log.error("微信服务器验证异常，错误信息为：" + e);
            e.printStackTrace();
        }
    }


    /**
     * 接收微信服务器转发的消息并处理
     */
    @RequestMapping(value = "/io", method = RequestMethod.POST)
    public void messageHandler(HttpServletRequest req, HttpServletResponse res) {
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        OutputStream os = null;
        try {
            //设置编码格式
            req.setCharacterEncoding("UTF-8");
            res.setCharacterEncoding("UTF-8");
            res.setContentType("text/html;charset=utf-8");
            //读取xml
            is = req.getInputStream();
            reader = new InputStreamReader(is);
            br = new BufferedReader(reader);
            String message = "";
            StringBuffer sb = new StringBuffer();
            while ((message = br.readLine()) != null) {
                sb.append(message);
            }
            String content = sb.toString();
            //解析xml为map
            Map<String, String> map = WeChatParseTool.parseXMLtoMap(content);
            String msgType = map.get("MsgType");
            //事件和消息分开处理
            String result = "";
            if (msgType != null && "event".equals(msgType)) {
                result = service.eventHandler(map);
            } else {
                result = service.msgHandler(map);
            }
            //返回xml到微信服务器
            if (!StringUtils.isEmpty(result)) {
                os = res.getOutputStream();
                os.write(result.getBytes("UTF-8"));
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("消息或事件处理失败，原因为：" + e);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("流关闭失败，原因为：" + e);
            }

        }
    }

}
