package com.lime.mypol.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lime.mypol.R;
import com.lime.mypol.manager.NetworkManager;
import com.lime.mypol.models.MemberInfo;
import com.lime.mypol.result.AddressResult;
import com.lime.mypol.result.CheckMemberResult;
import com.lime.mypol.result.SearchGoogleMapResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015-08-25.
 */
public class GoogleMapSearchDialogActivity extends Activity {

    private final String TAG = "GoogleMapSearch";

    ImageView button;
    EditText input;
    ListView listView;
    List<String> listAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDialog();
    }

    private void showDialog() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_daiolog_google_map_search);

        listAddress = new ArrayList<>();
        input = (EditText) findViewById(R.id.edit_google_search);
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    button.performClick();
                    return true;
                }
                return false;
            }
        });

        // 키보드 보여주기
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);
        imm.showSoftInputFromInputMethod(input.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED);

        listView = (ListView) findViewById(R.id.list_google_address);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_address, R.id.text_result_address, listAddress);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                Intent i = new Intent();
                i.putExtra("address", itemValue);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        button = (ImageView) findViewById(R.id.btn_google_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String keyword = input.getText().toString();

                if (!TextUtils.isEmpty(keyword)) {
                    //google search
                    NetworkManager.getInstance().searchAddress(keyword, new NetworkManager.OnNetResultListener<List<String>>() {
                        @Override
                        public void onSuccess(List<String> result) {
                            listView.setVisibility(View.VISIBLE);
                            Log.d(TAG, "google result count : " + result.size());
                            listAddress.clear();
                            listAddress.addAll(result);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFail(int code) {

                        }
                    });


                }
            }
        });

        setSize();
    }

    private void setSize() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.7); //Display 사이즈의 80%
        int height = (int) (display.getHeight() * 0.8);  //Display 사이즈의 80%
        int dHeight = getWindow().getAttributes().height;
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = dHeight > height ? height : dHeight;
    }
}
