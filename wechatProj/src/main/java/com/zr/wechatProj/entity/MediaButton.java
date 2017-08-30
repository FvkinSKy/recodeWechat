package com.zr.wechatProj.entity;

/**
 * @author zourui On 17:16 2017/8/30
 * @version 1.0
 */
public class MediaButton extends ButtonEntity {
    /**
     * 调用新增永久素材接口返回的合法media_id
     */
    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
