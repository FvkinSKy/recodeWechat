package com.zr.wechatProj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author zourui On 14:53 2017/8/29
 * @version 1.0
 *          API工具类
 */
public class WeChatApiTool {

    private static final String GRANT_TYPE = "client_credential";
    /**
     * 第三方用户唯一凭证
     */
    private static final String APPID = "wx440013db0de931d1";
    /**
     * 第三方用户唯一凭证密钥
     */
    private static final String SECRET = "ce57f62f00f58c1d9ad0f6610d168f14";

    /**
     * 获取access_token url
     */
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?";

    /**
     * 图灵机器人API url
     */
    private static final String ROBOT_URL = "http://www.tuling123.com/openapi/api?key=a4500591896d4848a709cd5ab85dacf2&info=";

    /**
     * 微信菜单API url
     */
    private static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";

    private static Logger log = Logger.getLogger(WeChatApiTool.class);

    /**
     * 调用图灵机器人API
     *
     * @param content 用户发来的文字消息
     * @return 调用API返回的结果
     */
    public static String callRobotApi(String content) {
        String revert = "";
        try {
            HttpGet httpGet = new HttpGet(ROBOT_URL + content.trim());
            CloseableHttpResponse response = null;
            response = HttpClients.createDefault().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject object = JSON.parseObject(result);
                revert = String.valueOf(object.get("text"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return revert;
    }

    /**
     * 调用API获取access_token
     *
     * @return access_token
     */
    public static String getAccessToken() {
        String access_token = "";
        StringBuilder url = new StringBuilder();
        try {
            CloseableHttpResponse response = null;
            url.append(TOKEN_URL).append("grant_type=").append(GRANT_TYPE).append("&appid=").append(APPID)
                    .append("&secret=").append(SECRET).append("");
            //发送HTTPGET请求
            HttpGet httpGet = new HttpGet(url.toString());
            response = HttpClients.createDefault().execute(httpGet);
            //获取返回值
            access_token = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取access_token失败" + e);
        }
        return access_token;
    }

    /**
     * 调用接口创建菜单
     *
     * @param access_token
     * @return
     */
    public static boolean buildMenu(String menu, String access_token) {
        StringBuilder url = new StringBuilder();
        try {
            if (!StringUtils.isEmpty(access_token)) {
                url.append(MENU_URL).append("?access_token=").append(access_token);
                HttpPost httpPost = new HttpPost(url.toString());
                //参数
                httpPost.setEntity(new StringEntity(menu, "UTF-8"));
                //获取响应
                CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost);
                HttpEntity httpEntityentity = response.getEntity();
                String result = EntityUtils.toString(httpEntityentity);
                JSONObject jsonObject = JSON.parseObject(result);
                return (String.valueOf(jsonObject.get("errcode")).equals("0"));
            } else {
                log.error("菜单创建失败,access_token为空");
                throw new RuntimeException("菜单创建失败,access_token为空");
            }
        } catch (ClientProtocolException e) {
            log.error("菜单创建失败,协议错误");
            throw new RuntimeException("菜单创建失败,协议错误");
        } catch (IOException e) {
            log.error("菜单创建失败,IO异常");
            throw new RuntimeException("菜单创建失败,IO异常");
        }
    }
}