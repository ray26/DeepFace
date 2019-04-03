package com.iustu.identification.ui.login.view;

import android.hardware.Camera;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.iustu.identification.R;
import com.iustu.identification.api.Api;
import com.iustu.identification.api.message.Message;
import com.iustu.identification.bean.User;
import com.iustu.identification.ui.base.BaseFragment;
import com.iustu.identification.ui.login.prenster.LoginPrenster;
import com.iustu.identification.ui.widget.CameraDecor;
import com.iustu.identification.ui.widget.camera.CameraPreview;
import com.iustu.identification.util.ExceptionUtil;
import com.iustu.identification.util.LibManager;
import com.iustu.identification.util.ToastUtil;
import com.iustu.identification.util.UserCache;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Liu Yuchuan on 2017/11/15.
 */

public class FaceLoginFragment extends BaseFragment{
    LoginPrenster loginPrenster=new LoginPrenster();
    @BindView(R.id.login_face_btn)
    TextView loginTv;
    @BindView(R.id.login_camera_layout)
    FrameLayout cameraLayout;
    @BindView(R.id.camera_decor)
    CameraDecor cameraDecor;
    private CameraPreview cameraPreview;


    private boolean isInLogin;
    private String[] buttonText = {"刷脸登录", "停止刷脸"};

    @Override
    protected int postContentView() {
        return R.layout.fragment_face_login;
    }

    @OnClick(R.id.set_server_tv)
    public void setServer(){
        cameraDecor.stopScan();
        dispose();
        loginPrenster.setServer();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginTv.setText(buttonText[0]);
        isInLogin = false;
        cameraPreview = new CameraPreview(mActivity, Camera.CameraInfo.CAMERA_FACING_FRONT);
        cameraLayout.addView(cameraPreview);
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraDecor.stopScan();
        cameraLayout.removeView(cameraPreview);
        dispose();
        cameraPreview = null;
    }

    @OnClick(R.id.login_normal_switch)
    public void switchToNormal(){
        dispose();
        isInLogin = false;
        loginTv.setText(buttonText[0]);
        ((LoginActivity)mActivity).switchFragment(0);
    }

    @OnClick(R.id.login_face_btn)
    public void login(){
        if(!isInLogin){
            ToastUtil.show("正在刷脸登录...请勿切换或退出页面");
            loginTv.setText(buttonText[1]);
            isInLogin = true;
            cameraDecor.startScan();
            cameraPreview.imgDataObservable()
                    .doOnSubscribe(mActivity::addDisposable)
                    .subscribe(this::faceLogin, t->{
                                ExceptionUtil.getThrowableMessage("faceLogin", t);
                                ToastUtil.show("出现错误，请重试");
                                loginTv.setText(buttonText[0]);
                                isInLogin = false;
                                cameraDecor.stopScan();
                                dispose();
                            });
        }else {
            loginTv.setText(buttonText[0]);
            isInLogin = false;
            cameraDecor.stopScan();
            dispose();
        }
    }

    public void faceLogin(byte[] bytes){
        Api.faceLogin(bytes)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(mActivity::addDisposable)
                .subscribe(stringMessage -> {
                    if(stringMessage.getCode() == Message.CODE_SUCCESS){
                        dispose();
                        User user = new User();
                        user.setName(stringMessage.getBody().getName());
                        user.setUsername(stringMessage.getBody().getUserName());
                        user.setSession(stringMessage.getBody().getSession());
                        user.setId(stringMessage.getId());
                        UserCache.setUser(user);
                        LibManager.loadData();
                        ((LoginActivity)mActivity).startLogin();
                    }
                }, t-> ExceptionUtil.getThrowableMessage(getClass().getSimpleName(), t));
    }
}