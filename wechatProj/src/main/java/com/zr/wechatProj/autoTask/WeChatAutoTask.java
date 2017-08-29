package com.zr.wechatProj.autoTask;

import com.zr.wechatProj.util.WeChatApiTool;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author zourui On 16:33 2017/8/29
 * @version 1.0
 */
public class WeChatAutoTask extends QuartzJobBean {

    private static Logger log = Logger.getLogger(WeChatAutoTask.class);

    private static boolean run_flag = true;

    /**
     * 定时获取access_token
     */
    private void work() {
        log.info("开始获取access_token:" + new Date());
        String accessToken = WeChatApiTool.getAccessToken();
        //存入redis
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (run_flag) {
                run_flag = false;
                this.work();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自动任务运行异常，原因为：" + e);
        } finally {
            run_flag = true;
        }

    }
}
