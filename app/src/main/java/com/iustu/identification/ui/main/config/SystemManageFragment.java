package com.iustu.identification.ui.main.config;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iustu.identification.BuildConfig;
import com.iustu.identification.R;
import com.iustu.identification.bean.ParameterConfig;
import com.iustu.identification.entity.Account;
import com.iustu.identification.ui.base.BaseFragment;
import com.iustu.identification.ui.widget.dialog.EditDialog;
import com.iustu.identification.util.DataCache;
import com.iustu.identification.util.QRCodeUtil;
import com.iustu.identification.util.SqliteUtil;
import com.iustu.identification.util.UUIDutil;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Liu Yuchuan on 2017/11/5.
 */

public class SystemManageFragment extends BaseFragment{
    private Account account;
    @BindView(R.id.tv_username)
    TextView usernameTv;
    @BindView(R.id.tv_version)
    TextView versionTv;
    @BindView(R.id.show_device_id)
    TextView deviceIdShow;
    @BindView(R.id.show_qrcode)
    ImageView showQrCode;
    @BindView(R.id.qr_code)
    ImageView qrCode;
    @OnClick(R.id.show_device_id)
    void setDeviceID(){
        new EditDialog.Builder()
                .content(config.getDeviceId())
                .title("更改设备ID")
                .hint("新ID")
                .positive("确定", (v, content, layout) -> {
//                    HttpUrl httpUrl = HttpUrl.parse(content);
//                    if(httpUrl == null) {
//                        layout.setError("地址不合法");
//                        return false;
//                    }
                    UUIDutil.setUUID(content);
                    config.setDeviceId(content);
                    config.save();
                    deviceIdShow.setText(content);
                    return true;
                })
                .negative("取消", null)
                .show(mActivity.getSupportFragmentManager());
    }

    ParameterConfig config;
    @Override
    protected int postContentView() {
        return R.layout.fragment_system_manage;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View view) {
        account = DataCache.getAccount();
        config = DataCache.getParameterConfig();
        ParameterConfig config=ParameterConfig.getFromSP();
        usernameTv.setText(("用户：" + account.name));
        versionTv.setText(("当前版本：" + BuildConfig.VERSION_NAME));
        deviceIdShow.setText(config.getDeviceId());
        showQrCode.setOnClickListener(v -> {
            qrCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(config.getDeviceId(),400,400));
            qrCode.setVisibility(View.VISIBLE);
        });
        qrCode.setOnClickListener(v -> {
            if(v.getVisibility()==View.VISIBLE){
                v.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onHide() {
        super.onHide();
        if(config!=null) {
            config.save();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(config!=null) {
            config.save();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(config!=null) {
            config.save();
        }
    }

    @OnClick(R.id.ip_alter_tv)
    public void alterIp(){
        new EditDialog.Builder()
                .content(config.getIpAddress())
                .title("更改域名地址")
                .hint("新域名")
                .positive("确定", (v, content, layout) -> {
                    if(!content.endsWith("/")){
                        content  += "/";
                    }
//                    HttpUrl httpUrl = HttpUrl.parse(content);
//                    if(httpUrl == null) {
//                        layout.setError("地址不合法");
//                        return false;
//                    }
                    config.setIpAddress(content);
                    return true;
                })
                .negative("取消", null)
                .show(mActivity.getSupportFragmentManager());
    }

    @OnClick(R.id.tv_modify_password)
    public void modifyPassword () {
        new EditDialog.Builder()
                .title("更改" + account.name + "的密码")
                .hint("新密码")
                .positive("确定", (v, content, layout) -> {
                    SqliteUtil.modifyAccountPassword(account.name, content);
                    return true;
                })
                .negative("取消", null)
                .show(mActivity.getSupportFragmentManager());
    }

}