package com.example.tr210demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.tr210demo.utils.Utils;

import java.io.File;

public class MainActivity extends Activity implements OnClickListener {

    private Button printer_test;
    private Button qr_code_test;
    private Button usb_camare_test;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

    }

    private void initView() {
        // TODO Auto-generated method stub
        printer_test = (Button) findViewById(R.id.printer_test);
        qr_code_test = (Button) findViewById(R.id.qr_code_test);
        usb_camare_test = (Button) findViewById(R.id.usb_camare_test);
    }

    private void setListener() {
        // TODO Auto-generated method stub
        printer_test.setOnClickListener(this);
        qr_code_test.setOnClickListener(this);
        usb_camare_test.setOnClickListener(this);
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
            case R.id.printer_test:
                printerTest();
                break;
            case R.id.qr_code_test:
                qrCodeTest();
                break;
            case R.id.usb_camare_test:
                usbCamareTest();
                break;

            default:
                break;
        }
    }

    private void printerTest() {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.example.tr210printtest","com.example.tr210printtest.PrintTestActivity");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.MAIN");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有该子APP，请下载安装",Toast.LENGTH_SHORT).show();
        }
    }

    private void qrCodeTest() {
        // TODO Auto-generated method stub

        if (isLicFileExist() && Utils.isNetworkConnected(MainActivity.this)){
            aKeyEntrance();
        }else{
            noKeyEntry();
        }
//        Intent intent = new Intent(MainActivity.this, QrcodeEntranceActivity.class);
//        startActivity(intent);
    }

    private void usbCamareTest(){
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.serenegiant","com.serenegiant.usbcamera.UvcCameraActivity");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.MAIN");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有该子APP，请下载安装",Toast.LENGTH_SHORT).show();
        }
    }

    private void aKeyEntrance() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivity(intent);
    }

    private void noKeyEntry() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MainActivity.this, ZxingQrcodeActivity.class);
        startActivity(intent);
    }

    public boolean isLicFileExist(){
        File file = new File("/mnt/sdcard");
        File[] listFile = file.listFiles();
        for(File sFile : listFile){
            if (sFile.isDirectory()){
                continue;
            }
            String fileName = sFile.getName();
            String[] str = fileName.split("\\.");
            if ("lic".equals(str[str.length-1])){
                return true;
            }
        }
        return false;
    }


}
