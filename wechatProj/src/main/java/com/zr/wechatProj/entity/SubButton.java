package com.zr.wechatProj.entity;

/**
 * @author zourui On 11:50 2017/8/31
 * @version 1.0
 *          二级菜单
 */
public class SubButton {
    /**
     * 二级菜单数组
     */
    private String sub_button;
    /**
     * 菜单标题
     */
    private String name;


    public String getSub_button() {
        return sub_button;
    }

    public void setSub_button(String sub_button) {
        this.sub_button = sub_button;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
