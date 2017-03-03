package com.example.tr210demo;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map.Entry;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tr210demo.utils.LogUtils;
import com.example.tr210demo.utils.Utils;
import com.codecorp.CortexDecoderLibrary;
import com.codecorp.CortexDecoderLibrary.CortexDecoderLibraryCallback;
import com.codecorp.CortexDecoderLibrary.CortexDecoderLicenseCallback;
import com.codecorp.CortexDecoderLibrary.LicenseStatusCode;
import com.codecorp.CortexDecoderLibrary.SymbologyType;
import com.codecorp.internal.Debug;
import static com.codecorp.internal.Debug.*;

/**
 * Initial the com.cnziz.m100demo.camera
 * 
 * @author Ryan.Tang
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CaptureActivity extends Activity implements
CortexDecoderLibraryCallback, CortexDecoderLicenseCallback  {
	
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE, };

	static final String TAG = CaptureActivity.class.getSimpleName();
	static final String LTAG = "CAPTUREACTIVITY";
	private SharedPreferences prefs;
	private CortexDecoderLibrary mCortexDecoderLibrary;
	private MyApplication mApplication;
	
	private CaptureActivity activity;
	//private ScanView scanView;
	private TextView title_text;
	//private boolean hasSurface;
	private String characterSet;
	//private SurfaceView surfaceView;
	private Handler mMainHandler;
	private View mCameraPreview;
	private FrameLayout frameLayout;

	private TextView text;

	private int count;
	private long last_time = 0L;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		if (!prefs.contains("focus.Back-Facing")) {
//			setPreference(prefs, "focus.Back-Facing", "Normal");
//		}
//		if (!prefs.contains("illumination.Back-Facing")) {
//			setPreference(prefs, "illumination.Back-Facing", Boolean.valueOf(false));
//		}
//		if (!prefs.contains("focus.Front-Facing")) {
//			setPreference(prefs, "focus.Front-Facing", "Far Fixed");
//		}
//		if (!prefs.contains("illumination.Front-Facing")) {
//			setPreference(prefs, "illumination.Front-Facing", Boolean.valueOf(false));
//		}
//
//		if (!prefs.contains("aztec")) {
//			setPreference(prefs, "aztec", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("bc412")) {
//			setPreference(prefs, "bc412", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("code11")) {
//			setPreference(prefs, "code11", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("code128")) {
//			setPreference(prefs, "code128", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("code32")) {
//			setPreference(prefs, "code32", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("code39")) {
//			setPreference(prefs, "code39", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("code49")) {
//			setPreference(prefs, "code49", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("code93")) {
//			setPreference(prefs, "code93", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("codabar")) {
//			setPreference(prefs, "codabar", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("codablockf")) {
//			setPreference(prefs, "codablockf", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("data_matrix")) {
//			setPreference(prefs, "data_matrix", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("ean13")) {
//			setPreference(prefs, "ean13", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("ean8")) {
//			setPreference(prefs, "ean8", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("grid_matrix")) {
//			setPreference(prefs, "grid_matrix", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("hanxin_code")) {
//			setPreference(prefs, "hanxin_code", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("hong_kong_2_of_5")) {
//			setPreference(prefs, "hong_kong_2_of_5", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("iata_2_of_5")) {
//			setPreference(prefs, "iata_2_of_5", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("interleaved_2_of_5")) {
//			setPreference(prefs, "interleaved_2_of_5", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("maxi_code")) {
//			setPreference(prefs, "maxi_code", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("matrix_2_of_5")) {
//			setPreference(prefs, "matrix_2_of_5", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("micropdf417")) {
//			setPreference(prefs, "micropdf417", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("microqr")) {
//			setPreference(prefs, "microqr", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("msi_plessey")) {
//			setPreference(prefs, "msi_plessey", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("nec_2_of_5")) {
//			setPreference(prefs, "nec_2_of_5", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("pdf417")) {
//			setPreference(prefs, "pdf417", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("pharmacode")) {
//			setPreference(prefs, "pharmacode", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("plessey")) {
//			setPreference(prefs, "plessey", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("qr_code")) {
//			setPreference(prefs, "qr_code", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("straight_2_of_5")) {
//			setPreference(prefs, "straight_2_of_5", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("telepen")) {
//			setPreference(prefs, "telepen", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("trioptic")) {
//			setPreference(prefs, "trioptic", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("upc-a")) {
//			setPreference(prefs, "upc-a", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("upc-e")) {
//			setPreference(prefs, "upc-e", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("australia_post")) {
//			setPreference(prefs, "australia_post", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("canada_post")) {
//			setPreference(prefs, "canada_post", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("dutch_post")) {
//			setPreference(prefs, "dutch_post", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("japan_post")) {
//			setPreference(prefs, "japan_post", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("korea_post")) {
//			setPreference(prefs, "korea_post", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("korea_post")) {
//			setPreference(prefs, "korea_post", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("royal_mail")) {
//			setPreference(prefs, "royal_mail", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("usps_intelligent_mail")) {
//			setPreference(prefs, "usps_intelligent_mail", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("usps_planet")) {
//			setPreference(prefs, "usps_planet", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("usps_postnet")) {
//			setPreference(prefs, "usps_postnet", Boolean.valueOf(true));
//		}
//		if (!prefs.contains("upu")) {
//			setPreference(prefs, "upu", Boolean.valueOf(true));
//		}
//
//


		setPreference(prefs, "camera_type", "Front-Facing");
		mApplication = (MyApplication) getApplication();
		mApplication.pushPreference(prefs, "debug_level");
		// Uncomment a line below to override the debug level
		// setDebugLevel(Log.INFO);
		// setDebugLevel(Log.DEBUG);
		// setDebugLevel(Log.VERBOSE);

		debug(TAG, "onCreate()");;
		verbose(TAG, "MANUFACTURER: " + android.os.Build.MANUFACTURER);
		verbose(TAG, "MODEL: " + android.os.Build.MODEL);
		verbose(TAG, "BRAND: " + android.os.Build.BRAND);
		verbose(TAG, "DEVICE: " + android.os.Build.DEVICE);
		verbose(TAG, "HARDWARE: " + android.os.Build.HARDWARE);
		verbose(TAG, "FINGERPRINT: " + android.os.Build.FINGERPRINT);
		verbose(TAG, "PRODUCT: " + android.os.Build.PRODUCT);
		verbose(TAG, "ID: " + android.os.Build.ID);
		verbose(TAG, "HOST: " + android.os.Build.HOST);

		mCortexDecoderLibrary = CortexDecoderLibrary.sharedObject(mApplication);
		// Save mCortexDecoderLibrary in case another Activity needs it
		mApplication.mCortexDecoderLibrary = mCortexDecoderLibrary;
		mCortexDecoderLibrary.setCallback(this);

		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_capture);
		activity = this;
		initView();
		//CameraManager.init(getApplication(), findViewById(R.id.preview_view));
		//hasSurface = false;

		if (verifyStoragePermission()) {
			mCortexDecoderLibrary.initLicensing("/mnt/sdcard", this);
		}else {
			Toast.makeText(this, "permission fail", Toast.LENGTH_SHORT).show();
		}
		// Send all preferences to decoder
		pushPreferences();;
		mMainHandler = new Handler(Looper.getMainLooper());
	}

	private void initView() {
		// TODO Auto-generated method stub
		frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText(activity.getResources().getString(
				R.string.qr_code_test));

		mCameraPreview = mCortexDecoderLibrary.getCameraPreview();
		frameLayout.addView(mCameraPreview);

		ImageView back_imgae = (ImageView) findViewById(R.id.back_imgae);
		back_imgae.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CaptureActivity.this.finish();
			}
		});
		text = (TextView) findViewById(R.id.text);
	}

	private void setPreference(SharedPreferences sharedPreferences, String key,
			Object value) {
		debug(TAG, "setPreference(" + key + ", " + value + ")");
		SharedPreferences.Editor e = sharedPreferences.edit();
		if (value instanceof String)
			e.putString(key, (String) value);
		else if (value instanceof Boolean)
			e.putBoolean(key, (Boolean) value);
		else
			throw new RuntimeException(
					"Programming error!  Unknown value type.");
		e.apply();
	}

	private void setDebugLevel(int level) {
		Log.d(TAG, "setDebugLevel(" + level + ")");
		Debug.debugLevel = level;
		Editor e = prefs.edit();
		e.putString("debug_level", Integer.toString(level));
		e.apply();
	}

	private void pushPreferences() {
		for (Entry<String, ?> e : prefs.getAll().entrySet()) {
			try {
				LogUtils.getInstanse(this).saveMassage("pushPreferences",e.getKey().toString());
				mApplication.pushPreference(prefs, e.getKey());
			} catch (Exception ex) {
				Log.e(TAG, "Error pushing preference " + e.getKey() + ":", ex);
			}
		}
	}

	OnClickListener tapListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			debug(TAG, "onClick()");
			startScanning();
		}
	};

	void startScanning() {
		debug(TAG, "startScanning()");
		mMainHandler.post(new Runnable() {
			@Override
			public void run() {
				mCortexDecoderLibrary.startCameraPreview();
				mCortexDecoderLibrary.startDecoding();
				Utils.openLight();
			}
		});
	}

	void stopScanning() {
		debug(TAG, "stopScanning()");
		Utils.closeLight();
		mCortexDecoderLibrary.stopDecoding();
		mCortexDecoderLibrary.stopCameraPreview();
	}

	public void stopCamera() {
		debug(TAG, "stopCamera()");
		mCortexDecoderLibrary.stopDecoding();
		mCortexDecoderLibrary.stopCameraPreview();
	}

	public void closeCamera() {
		debug(TAG, "closeCamera()");
		mCortexDecoderLibrary.stopDecoding();
		mCortexDecoderLibrary.stopCameraPreview();
		mCortexDecoderLibrary.closeCamera();
	}
	
	public boolean verifyStoragePermission() {
		PackageManager packageManager = getPackageManager();
		int permission = packageManager.checkPermission(
				"android.permission.CAMERA", "com.example.tr210demo");
		if (PackageManager.PERMISSION_GRANTED == permission) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onStart() {
		debug(TAG, "onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
		debug(TAG, "onResume()");
		super.onResume();
		pushPreferences();
		mCortexDecoderLibrary.setLicenseCallback(this);
		startScanning();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		debug(TAG, "onActivityResult()");
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		debug(TAG, "onPause()");
		super.onPause();
		mCortexDecoderLibrary.setLicenseCallback(null);
		mCortexDecoderLibrary.setCallback(null);
		stopCamera();
		closeCamera();
	}

	@Override
	public void onStop() {
		debug(TAG, "onStop()");
		super.onStop();
	}



	@Override
	protected void onDestroy() {
		debug(TAG, "onDestroy()");
		Utils.closeLight();
		mCortexDecoderLibrary.closeSharedObject();
		super.onDestroy();
	}

	@Override
	public void receivedDecodedData(final String data, final SymbologyType type) {
		Date dt = new Date();
		long time= dt.getTime();
		if(last_time != 0L){
			final String symString = CortexDecoderLibrary
					.stringFromSymbologyType(type);
			count++;
			String msg = "symString：" + symString + "\t\tresult：" + data  +"\tcount="+count+"\t time= "+(time-last_time)+" 毫秒";
			Message message = mHandler.obtainMessage();
			message.obj = msg;
			mHandler.sendMessage(message);
		}
		last_time = time;
		mCortexDecoderLibrary.startDecoding();
		mCortexDecoderLibrary.captureCurrentImageInBuffer();
		//startScanning();
	}

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			text.setText((String)msg.obj);

		}
	};

	@Override
	public void licenseStatusCallback(LicenseStatusCode statusCode) {
		final boolean deviceNotLicensed = (statusCode != LicenseStatusCode.LicenseStatus_LicenseValid);
		switch (statusCode) {
		case LicenseStatus_LicenseNotFound:
			// Log.i(LTAG, "License file does not exist on device");
			Toast.makeText(CaptureActivity.this, "License not found",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseDeviceMismatch:
			// Log.i(LTAG,
			// "License file does not match DeviceID or Wifi Mac Address of current device");
			Toast.makeText(CaptureActivity.this, "License device mismatch",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseNetworkError:
			// Log.i(LTAG,
			// "Encountered network error while trying to acquire license");
			Toast.makeText(CaptureActivity.this, "License network error",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseInvalid:
			// Log.i(LTAG, "License does not exist on back-end server");
			Toast.makeText(CaptureActivity.this, "License invalid",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseNotActivated:
			// Log.i(LTAG, "License has not been activated");
			Toast.makeText(CaptureActivity.this, "License not activated",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseExpired:
			// Log.i(LTAG, "License has expired");
			Toast.makeText(CaptureActivity.this, "License expired",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseBlocked:
			// Log.i(LTAG, "License has been blocked");
			Toast.makeText(CaptureActivity.this, "License blocked",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseInternetBlocked:
			// Log.i(LTAG,
			// "License has been blocked due to license not being validated");
			Toast.makeText(CaptureActivity.this,
					"License blocked due to no Internet", Toast.LENGTH_LONG)
					.show();
			break;
		case LicenseStatus_LicenseBlockedNoCheckin:
			// Log.i(LTAG,
			// "License has been blocked no check-in withing sync period");
			Toast.makeText(CaptureActivity.this, "License blocked no check-in",
					Toast.LENGTH_LONG).show();
			break;
		case LicenseStatus_LicenseValid:
			// Log.i(LTAG, "License is valid to use");
			Toast.makeText(CaptureActivity.this, "License valid for use",
					Toast.LENGTH_LONG).show();
			break;
		default:
			// Log.i(LTAG, "Received unknown license status code");
			Toast.makeText(CaptureActivity.this,
					"Received unknown license status code", Toast.LENGTH_LONG)
					.show();
			break;
		}
	}
}