package com.lime.mypol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lime.mypol.R;

/**
 * Created by seongsan on 2015-08-24.
 */
public class ChangeProfileDialogActivity extends Activity implements View.OnClickListener {

    private LinearLayout mLayout;
    private int pWidth;
    private int pHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_daiolog_change_profile);
        mLayout = (LinearLayout)findViewById(R.id.profile_dialog);
        TextView modText = (TextView)findViewById(R.id.text_modify);
        TextView canText = (TextView)findViewById(R.id.text_cancel);
        modText.setOnClickListener(this);
        canText.setOnClickListener(this);

//        Intent intent = getIntent();
//        pWidth = Integer.parseInt(intent.getStringExtra("width"));
//        pHeight = Integer.parseInt(intent.getStringExtra("height"));

        setSize();
    }

    private int mWidth;
    private int mHeight;

    private void setSize() {
//        ViewTreeObserver vto = mLayout.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                mWidth = mLayout.getMeasuredWidth();
//                mHeight = mLayout.getMeasuredHeight();
//
//                LayoutParams params = (LayoutParams) mLayout.getLayoutParams();
//                params.width = mWidth > (int)(pWidth*0.9) ? (int)(pWidth*0.9) : mWidth;
//                params.height =  mHeight > (int)(pHeight*0.9) ? (int)(pHeight*0.9) : mHeight;
//                mLayout.setLayoutParams(params);
//            }
//        });

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 80%
        int height = (int) (display.getHeight() * 0.8);  //Display 사이즈의 80%
//        int dWidth = getWindow().getAttributes().width;
        int dHeight = getWindow().getAttributes().height;
//        getWindow().getAttributes().width = dWidth > width ? width : dWidth;
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = dHeight > height ? height : dHeight;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_modify:
                finish();
                break;

            case R.id.text_cancel:
                finish();
                break;
        }
    }
}
