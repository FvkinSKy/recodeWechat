package com.zr.wechatProj.entity;

/**
 * @author zourui On 17:13 2017/8/30
 * @version 1.0
 */
public class ViewButton extends ButtonEntity {
    /**
     * 网页链接，用户点击菜单可打开链接
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
