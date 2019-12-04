package com.sdk.ltgame.ltguest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sdk.ltgame.core.common.LTGameOptions;
import com.sdk.ltgame.core.common.LTGameSdk;
import com.sdk.ltgame.core.common.Target;
import com.sdk.ltgame.core.impl.OnLoginStateListener;
import com.sdk.ltgame.core.impl.OnRechargeListener;
import com.sdk.ltgame.core.model.LoginObject;
import com.sdk.ltgame.core.model.RechargeObject;
import com.sdk.ltgame.core.platform.AbsPlatform;
import com.sdk.ltgame.core.platform.IPlatform;
import com.sdk.ltgame.core.platform.PlatformFactory;
import com.sdk.ltgame.core.uikit.BaseActionActivity;
import com.sdk.ltgame.core.util.LTGameUtil;
import com.sdk.ltgame.ltguest.uikit.GuestActivity;


public class GuestPlatform extends AbsPlatform {

    private GuestHelper mGuestHelper;


    public GuestPlatform(Context context, String appId, String appName, String appKey, int target) {
        super(context, appId, appName, appKey, target);
    }

    /**
     * 工厂
     */
    public static class Factory implements PlatformFactory {


        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameSdk.options();
            if (!LTGameUtil.isAnyEmpty(options.getAppName(), options.getLtAppKey())) {
                platform = new GuestPlatform(context, options.getLtAppId(),
                        options.getAppName(), options.getLtAppKey(), target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_GUEST;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_GUEST;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }

    @Override
    public Class getUIKitClazz() {
        return GuestActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        mGuestHelper.onGoogleResult(requestCode, data);
        mGuestHelper.onFBResult(requestCode, resultCode, data);
    }


    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mGuestHelper = new GuestHelper(activity, object.getmGoogleClient(), object.getmAdID(),
                object.getSelfRequestCode(), listener);
        mGuestHelper.guestLogin(object.getGuestType(), object.getFacebookAppID());
    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }
}
