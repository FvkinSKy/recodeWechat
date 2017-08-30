package com.zr.wechatProj.entity;

/**
 * @author zourui On 17:04 2017/8/30
 * @version 1.0
 */
public class ClickButton extends ButtonEntity {
    /**
     * 菜单KEY值，用于消息接口推送
     */
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
