package com.example.tr210demo;

import java.io.IOException;
import java.util.Vector;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tr210demo.camera.CameraManager;
import com.example.tr210demo.decoding.CaptureActivityHandler;
import com.example.tr210demo.decoding.InactivityTimer;
import com.example.tr210demo.utils.Utils;
import com.example.tr210demo.view.ViewFinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ZxingQrcodeActivity extends Activity implements Callback {
	private ZxingQrcodeActivity activity;
	private CaptureActivityHandler handler;
	private ViewFinderView viewfinderView;
	private TextView title_text;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private SurfaceView surfaceView;
	private AudioManager audioService;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_zxing);
		activity = this;
		CameraManager.init(activity, findViewById(R.id.preview_view));
		audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		initView();
		Utils.openLight();
	}

	private void initView() {
		// TODO Auto-generated method stub
		viewfinderView = (ViewFinderView) findViewById(R.id.viewfinder_view);
		title_text = (TextView) findViewById(R.id.title_text);
		title_text.setText(activity.getResources().getString(
				R.string.qr_code_test));
		ImageView back_imgae = (ImageView) findViewById(R.id.back_imgae);
		back_imgae.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZxingQrcodeActivity.this.finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	public void init() {
		surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;
		playBeep = true;
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		inactivityTimer.onActivity();
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		Utils.closeLight();
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		restartCamera();
	}

	/**
	 * ����ɨ����
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		closeCamera();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(activity, "result is null", 0).show();
		} else {
			Toast.makeText(activity, "result is "+resultString, 0).show();
		}
		restartCamera();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	public void closeCamera() {
		if (handler != null) {
			surfaceView.setActivated(false);
			handler.quitSynchronously();
			handler = null;
		}
		// �ر�����ͷ
		CameraManager.get().closeDriver();
	}

	public void restartCamera() {
		viewfinderView.setVisibility(View.VISIBLE);
		surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		initCamera(surfaceHolder);
		if (handler != null) {
			handler.restartPreviewAndDecode();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewFinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};


}