package com.lime.mypol.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lime.mypol.R;
import com.lime.mypol.manager.NetworkManager;
import com.lime.mypol.models.MemberInfo;
import com.lime.mypol.result.CheckMemberResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015-08-25.
 */
public class ExtraInfoDialogActivity extends Activity {

    private final String TAG = "ExtraInfojDialogActivity";
    final int GOOGLE_SEARCH_CODE = 1;

    TextView button;
    TextView tvAddress, tvBirthday;
    RadioGroup rd;
    MemberInfo kakaoMemberInfo;
    String mAddress, mBirthday, mGender;

    DatePickerDialog dataDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDialog();
    }

    private void showDialog() {

        Intent intent = getIntent();
        kakaoMemberInfo = (MemberInfo) intent.getSerializableExtra("kakaoMemberInfo");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_daiolog_user_extra_info);

        mGender = "남";
        dataDialog = new DatePickerDialog(this, datePickListener, 1990, 0, 1);
        tvAddress = (TextView) findViewById(R.id.text_address);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressSearchDialog();
            }
        });
        tvBirthday = (TextView) findViewById(R.id.text_birthday);
        tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataDialog.show();
            }
        });

        rd = (RadioGroup) findViewById(R.id.radiogroup_gender);
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        mGender = "남";
                        break;
                    case R.id.radio_female:
                        mGender = "여";
                        break;
                }
            }
        });

        button = (TextView) findViewById(R.id.text_complete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddress = tvAddress.getText().toString();
                mBirthday = tvBirthday.getText().toString();
                signUp();
            }
        });

        setSize();
    }

    private void showAddressSearchDialog() {
        Intent intent = new Intent(this, GoogleMapSearchDialogActivity.class);
        startActivityForResult(intent, GOOGLE_SEARCH_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GOOGLE_SEARCH_CODE){

            if (resultCode == RESULT_OK){
                tvAddress.setText(data.getStringExtra("address"));

            }else if(resultCode == RESULT_CANCELED){
                tvAddress.setText("");
            }

        }
    }

    private DatePickerDialog.OnDateSetListener datePickListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tvBirthday.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        }
    };

    private void setSize() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 80%
        int height = (int) (display.getHeight() * 0.8);  //Display 사이즈의 80%
        int dHeight = getWindow().getAttributes().height;
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = dHeight > height ? height : dHeight;
    }

    /**
     * 서버에 없는 데이터가 없는 신규 회원의 경우 추가 정보 받기
     */
    private void signUp() {
        if (mAddress != null)
            kakaoMemberInfo.setAddress(mAddress);

        if (mBirthday != null) {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = null;
            try {
                date = transFormat.parse(mBirthday);
                kakaoMemberInfo.setBirthDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (mGender != null)
            kakaoMemberInfo.setGender(mGender);

        NetworkManager.getInstance().saveMemberServer(kakaoMemberInfo, new NetworkManager.OnNetResultListener<CheckMemberResult>() {
            @Override
            public void onSuccess(CheckMemberResult result) {
                if (result.getResult() == 1) {
                    // 정상 신규 등록
                    Toast.makeText(getApplicationContext(), "등록완료", Toast.LENGTH_SHORT).show();
                    redirectKakaoActivity();
                } else {
                    // 신규 등록 실패
                    Toast.makeText(getApplicationContext(), "등록실패", Toast.LENGTH_SHORT).show();
                    redirectKakaoActivity();
                }

            }

            @Override
            public void onFail(int code) {
                Toast.makeText(ExtraInfoDialogActivity.this, "서버접속 실패:" + code, Toast.LENGTH_SHORT).show();
                redirectKakaoActivity();
            }
        });
    }

    private void redirectKakaoActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

}
