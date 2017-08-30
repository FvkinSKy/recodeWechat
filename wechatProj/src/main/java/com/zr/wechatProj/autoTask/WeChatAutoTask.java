package com.zr.wechatProj.autoTask;

import com.alibaba.fastjson.JSONObject;
import com.zr.wechatProj.util.WeChatApiTool;
import com.zr.wechatProj.util.WeChatRedisTool;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * @author zourui On 16:33 2017/8/29
 * @version 1.0
 */
public class WeChatAutoTask extends QuartzJobBean {

    private static Logger log = Logger.getLogger(WeChatAutoTask.class);

    private static boolean run_flag = false;

    private static int count = 0;

    /**
     * 定时获取access_token
     */
    private boolean work() {
        count++;
        log.info("开始获取access_token:" + new Date());
        String accessToken = WeChatApiTool.getAccessToken();
        JSONObject object = JSONObject.parseObject(accessToken);
        if (object.containsKey("access_token")) {
            //存入redis
            Jedis jedis = WeChatRedisTool.getConn();
            jedis.set("access_token", object.getString("access_token"));
            jedis.close();
            count = 0;
            return true;
        } else {
            //再次获取access_token
            if (count >= 3) {
                log.error("无法获取access_token");
                return false;
            }
            this.work();
        }
        return false;
    }

    /**
     * 调用接口创建菜单
     *
     * @return
     */
    private boolean buildMenu() {
        try {
            Jedis jedis = WeChatRedisTool.getConn();
            String access_token = jedis.get("access_token");
            boolean isBuild = WeChatApiTool.buildMenu("", access_token);
            if (isBuild) {
                log.info("菜单创建成功" + new Date());
            } else {
                log.info("菜单创建失败" + new Date());
            }
        } catch (Exception e) {
            log.error("菜单创建异常，原因为：" + e.getMessage());
        }
        return false;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (run_flag) {
                return;
            }
            run_flag = true;
            if (this.work()) {
                this.buildMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自动任务运行异常，原因为：" + e.getMessage());
        } finally {
            run_flag = false;
        }

    }
}
