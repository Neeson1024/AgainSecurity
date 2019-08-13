package com.hutu.core.social.qq.api;

import java.io.UnsupportedEncodingException;

public class QQUserInfo {
    //返回码
    private String ret;
    //如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
    private String msg;
    //用户在QQ空间的昵称
    private String nickname;
    //	大小为30×30像素的QQ空间头像URL。
    private String figureurl;
    //大小为50×50像素的QQ空间头像URL
    private String figureurl_1;
    //大小为100×100像素的QQ空间头像URL
    private String figureurl_2;
    //大小为40×40像素的QQ头像URL。
    private String figureurl_qq_1;
    //大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
    private String figureurl_qq_2;
    //性别。 如果获取不到则默认返回"男"
    private String gender;
    private String figureurl_qq;
    //是否是黄钻
    private String is_yellow_vip;
    private String province;
    private String city;
    private String year;
    private String constellation;
    private String figureurl_type;
    private String vip;
    private String yellow_vip_level;
    private String level;
    private String is_yellow_year_vip;
    private String is_lost;
    private String openId;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getFigureurl_type() {
        return figureurl_type;
    }

    public void setFigureurl_type(String figureurl_type) {
        this.figureurl_type = figureurl_type;
    }

    public String getFigureurl_qq() {
        return figureurl_qq;
    }

    public void setFigureurl_qq(String figureurl_qq) {
        this.figureurl_qq = figureurl_qq;
    }

    public String getIs_lost() {
        return is_lost;
    }

    public void setIs_lost(String is_lost) {
        this.is_lost = is_lost;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return "aaaa";
    }

    public void setNickname(String nickname) {
        try {
            this.nickname = new String(nickname.getBytes("GBK"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIs_yellow_vip() {
        return is_yellow_vip;
    }

    public void setIs_yellow_vip(String is_yellow_vip) {
        this.is_yellow_vip = is_yellow_vip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellow_vip_level() {
        return yellow_vip_level;
    }

    public void setYellow_vip_level(String yellow_vip_level) {
        this.yellow_vip_level = yellow_vip_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIs_yellow_year_vip() {
        return is_yellow_year_vip;
    }

    public void setIs_yellow_year_vip(String is_yellow_year_vip) {
        this.is_yellow_year_vip = is_yellow_year_vip;
    }
}
