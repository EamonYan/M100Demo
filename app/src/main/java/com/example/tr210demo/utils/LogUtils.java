package com.example.tr210demo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class LogUtils {
	public static boolean debug = true;
	private static final String log_path = Environment.getExternalStorageDirectory().getPath() + "/log";
	private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final int MAX_SIZE = 100;  //��λMB
	private static LogUtils logUtils;
	private static Context mContext;

	public static LogUtils getInstanse(Context context) {
		mContext = context;
		if (null == logUtils) {
			logUtils = new LogUtils();
			File file = new File(log_path);
			if (!file.exists()) {
				file.mkdirs();
			}
			if (FileUtil.getFileOrFilesSize(log_path, FileUtil.SIZETYPE_MB) >= MAX_SIZE) {
				FileUtil.deleteFolderFile(log_path, false);
			}
		}
		return logUtils;
	}

	public void d(String tag, String content) {
		//saveMassage(tag, content);
		if (!debug) {
			return;
		}
		Log.d(tag, content);
	}

	public void e(String tag, String content) {
		saveMassage(tag, content);
		if (!debug) {
			return;
		}
		Log.e(tag, content);
	}

	public void i(String tag, String content) {
		//saveMassage(tag, content);
		if (!debug) {
			return;
		}
		Log.i(tag, content);
	}

	public void v(String tag, String content) {
		//saveMassage(tag, content);
		if (!debug) {
			return;
		}
		Log.v(tag, content);
	}

	public void w(String tag, String content) {
		//saveMassage(tag, content);
		if (!debug) {
			return;
		}
		Log.w(tag, content);
	}

	public void saveMassage(String tag, String content) {
		File file = new File(log_path, dayFormat.format(new Date())+".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			String msg = tag+"  "+timeFormat.format(new Date())+"  "+content;
			msg = msg + "\n";
			fileWriter.write(msg , 0, msg.length());
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
