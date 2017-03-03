package com.example.tr210demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class QrcodeEntranceActivity extends Activity implements View.OnClickListener {

    private Button a_key_entrance;
    private Button no_key_entry;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_qrcode_entrance);
        initView();
        setListener();

    }

    private void initView() {
        a_key_entrance = (Button) findViewById(R.id.a_key_entrance);
        no_key_entry = (Button) findViewById(R.id.no_key_entry);
    }

    private void setListener() {
        // TODO Auto-generated method stub
        a_key_entrance.setOnClickListener(this);
        no_key_entry.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.a_key_entrance:
                aKeyEntrance();
                break;
            case R.id.no_key_entry:
                noKeyEntry();
                break;

            default:
                break;
        }
    }

    private void aKeyEntrance() {
        Intent intent = new Intent(QrcodeEntranceActivity.this, CaptureActivity.class);
        startActivity(intent);
    }

    private void noKeyEntry() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(QrcodeEntranceActivity.this, ZxingQrcodeActivity.class);
        startActivity(intent);
    }
}

