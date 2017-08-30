package com.zr.wechatProj.util;

import com.thoughtworks.xstream.XStream;
import com.zr.wechatProj.entity.RevertMsgEntity;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zourui On 10:04 2017/8/29
 * @version 1.0
 */
public class WeChatParseTool {

    private static Logger log = Logger.getLogger(WeChatParseTool.class);

    /**
     * XML转Map
     *
     * @param xml
     * @return map
     */
    public static Map<String, String> parseXMLtoMap(String xml) {
        Map<String, String> map = new HashMap<>();
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element element = doc.getRootElement();
            for (Iterator<?> it = element.elementIterator(); it.hasNext(); ) {
                Element e = (Element) it.next();
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("xml转map失败，原因为：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 实体类转xml
     *
     * @param entity
     * @return xml
     */
    public static String parseMsgEntityToXml(RevertMsgEntity entity) {
        XStream xStream = new XStream();
        String xml = "";
        try {
            xStream.alias("xml", entity.getClass());
            xml = xStream.toXML(entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("实体类转xml失败，原因为：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return xml;
    }
}
