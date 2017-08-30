package com.zr.wechatProj.service;

import com.zr.wechatProj.entity.RevertMsgEntity;
import com.zr.wechatProj.util.WeChatApiTool;
import com.zr.wechatProj.util.WeChatParseTool;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zourui On 10:44 2017/8/29
 * @version 1.0
 */
@Service("weChatIoService")
public class WeChatIoServiceImpl implements WeChatIoService {

    //文本消息
    private static final String TEXT_TYPE = "text";
    //图片消息
    private static final String IMAGE_TYPE = "image";

    /**
     * 消息处理
     *
     * @param map
     * @return
     * @throws
     */
    @Override
    public String msgHandler(Map<String, String> map) throws Exception {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");
        String createTime = String.valueOf(new Date().getTime());//创建时间
        String xml = "";
        if (TEXT_TYPE.equals(MsgType)) {
            RevertMsgEntity entity = new RevertMsgEntity();
            //调用robot api
            entity.setContent(WeChatApiTool.callRobotApi(Content));
            entity.setCreateTime(createTime);
            entity.setFromUserName(toUserName);
            entity.setMsgType(MsgType);
            entity.setToUserName(fromUserName);
            //entity转xml
            xml = WeChatParseTool.parseMsgEntityToXml(entity);
        } else if (IMAGE_TYPE.equals(MsgType)) {

        }
        return xml;
    }

    @Override
    public String eventHandler(Map<String, String> map) throws Exception {
        return null;
    }
}
