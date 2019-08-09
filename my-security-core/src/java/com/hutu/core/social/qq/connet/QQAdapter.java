package com.hutu.core.social.qq.connet;

import com.hutu.core.social.qq.api.QQ;
import com.hutu.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 当前的api是否可用
     * @param qq
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        try {
            QQUserInfo userInfo = qq.getQQUserInfo();

            connectionValues.setDisplayName(userInfo.getNickname());
            connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
            connectionValues.setProfileUrl(null);
            connectionValues.setProviderUserId(userInfo.getOpenId());
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {
        //do nothing
    }
}
