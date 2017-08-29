package com.zr.wechatProj.entity;

/**
 * @author zourui On 14:30 2017/8/29
 * @version 1.0
 * 回复文字消息实体类
 */
public class RevertMsgEntity extends BaseEntity {
    /**
     * 回复的消息内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
