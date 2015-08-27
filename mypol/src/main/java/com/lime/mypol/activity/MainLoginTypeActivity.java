package com.lime.mypol.activity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;
import com.lime.mypol.R;
import com.lime.mypol.manager.DatabaseManager;
import com.lime.mypol.manager.NetworkManager;
import com.lime.mypol.models.MemberInfo;
import com.lime.mypol.result.CheckMemberResult;
import com.lime.mypol.utils.WindowUtil;
import com.lime.mypol.view.kbv.KenBurnsView;


public class MainLoginTypeActivity extends AppCompatActivity {

    private static String TAG = "MainLoginTypeActivity";


    private final int WA_SIGNUP_CODE = 1100;

    private MemberInfo kakaoMemberInfo = new MemberInfo();
    private UserProfile userProfile;

    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        super.onCreate(savedInstanceState);
        checkLoginInfo();
        setSize();
    }

    final float BUTTON_HEIGHT_SCALE = 0.07f;
    final float BUTTON_WIDTH_SCALE = 0.5f;
    final float BUTTON_TEXT_SCALE = 0.05f;

    private void setSize() {
        WindowUtil.getDisplay(this);
        int width, height, size;
        width = (int) (WindowUtil.width * BUTTON_WIDTH_SCALE);
        height = (int) (WindowUtil.height * BUTTON_HEIGHT_SCALE);
        size = (int) (WindowUtil.height * BUTTON_TEXT_SCALE);
        loginButton.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        btnDemo.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        btnDemo.setTextSize(size);
    }

    private void checkLoginInfo() {
        Log.d(TAG, "checkLoginInfo start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        userProfile = UserProfile.loadFromCache();
//        UserManagement.requestMe(new MeResponseCallback() {
//            @Override
//            protected void onSuccess(UserProfile up) {
//                userProfile = up;
//            }
//
//            @Override
//            protected void onNotSignedUp() {
//                userProfile = null;
//            }
//
//            @Override
//            protected void onSessionClosedFailure(APIErrorResult apiErrorResult) {
//                userProfile = null;
//            }
//
//            @Override
//            protected void onFailure(APIErrorResult apiErrorResult) {
//                userProfile = null;
//            }
//        });
//        Log.d(TAG, "kakaoMemberInfo:" + kakaoMemberInfo);
//        Log.d(TAG, "userProfile:" + userProfile);

        if (!kakaoMemberInfo.isLogOn() && userProfile != null) {
            long id = userProfile.getId();
            String nickname = userProfile.getNickname();

            if (id > 0) {
                Log.d(TAG, "로그인정보:" + id + "/" + nickname);
                kakaoMemberInfo = DatabaseManager.getInstance(this).selectMemberInfo("" + id, 1);

                if (kakaoMemberInfo == null) {
                    kakaoMemberInfo = new MemberInfo("" + id, 1, nickname);
//                kakaoMemberInfo.setUrlThumbnail(userProfile.getThumbnailImagePath());
                    kakaoMemberInfo.setUrlThumbnail(userProfile.getProfileImagePath());
//                    Log.d("kakako_img_url:", userProfile.getProfileImagePath());
//                    Log.d("kakako_thum_url:", userProfile.getThumbnailImagePath());

                    // web server 회원인지 체크
                    checkMemberInServer();
                } else {
                    kakaoMemberInfo.setLogOn(true);
                }
            } else {
                initView();
            }
        } else {
            initView();
        }
    }


    private LoginButton loginButton;
    private Button btnDemo;
    private LinearLayout linearLayout;

    private void initView() {
        setContentView(R.layout.activity_main_login_type);
        linearLayout = (LinearLayout) findViewById(R.id.layout_button);
        btnDemo = (Button) findViewById(R.id.demo_btn);
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDemo();
            }
        });

        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainLoginTypeActivity.this.finish();
//            }
//        });

        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);

        setAnimation();
    }

    private KenBurnsView mKenBurns;

    private void setAnimation() {
        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mKenBurns.setImageResource(R.drawable.main_background);
        animation2();
    }


    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(loginButton, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(loginButton, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(loginButton, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();
    }

    private void animation2() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_button);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        linearLayout.startAnimation(anim);
    }

    private void animation3() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(btnDemo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        super.onDestroy();
        if (session != null) session.removeCallback(mySessionCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (session != null) {
            if (session.isClosed()) {
                loginButton.setVisibility(View.VISIBLE);
                btnDemo.setVisibility(View.VISIBLE);
            } else {
                loginButton.setVisibility(View.GONE);
                btnDemo.setVisibility(View.GONE);
                session.implicitOpen();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MySessionStatusCallback implements SessionCallback {
        /**
         * 세션이 오픈되었으면 가입페이지로 이동 한다.
         */
        @Override
        public void onSessionOpened() {
            //뺑글이 종료
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈후 보일 페이지로 이동
            MainLoginTypeActivity.this.onSessionOpened();
        }

        /**
         * 세션이 삭제되었으니 로그인 화면이 보여야 한다.
         *
         * @param exception 에러가 발생하여 close가 된 경우 해당 exception
         */
        @Override
        public void onSessionClosed(final KakaoException exception) {
            //뺑글이 종료
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈을 못했으니 다시 로그인 버튼 노출.
            loginButton.setVisibility(View.VISIBLE);
            btnDemo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSessionOpening() {
            //뺑글이 시작
        }

    }

    protected void onSessionOpened() {
        // 세션이 오픈되어 있음. 즉, Access Token을 얻어 낼 수 있는 위치임.
        final Intent intent = new Intent(MainLoginTypeActivity.this, SampleSignupActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkMemberInServer() {
        NetworkManager.getInstance().checkMemberInServer(kakaoMemberInfo, new NetworkManager.OnNetResultListener<CheckMemberResult>() {
            @Override
            public void onSuccess(CheckMemberResult result) {
                if (result.getResult() == 0) {
                    // 신규회원
                    redirectWASignupActivity();
                } else {
                    // 기존회원
//                    showMainMenuActivity();
                    showMainActivity();
                }
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(MainLoginTypeActivity.this, "서버접속 실패:" + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectWASignupActivity() {

        Intent intent = new Intent(this, ExtraInfoDialogActivity.class);
        intent.putExtra("kakaoMemberInfo", kakaoMemberInfo);
        startActivity(intent);
//        startActivityForResult(intent, WA_SIGNUP_CODE);
//        finish();
    }

    private void showMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("memberInfo", kakaoMemberInfo);
        startActivity(intent);
        finish();
    }

    private void showDemo() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("memberInfo", new MemberInfo());
        startActivity(intent);
        finish();
    }
}
