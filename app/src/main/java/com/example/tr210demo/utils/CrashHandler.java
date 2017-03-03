package com.example.tr210demo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;


import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {

	/** CrashHandler实例 */

	private static CrashHandler instance;
	// 系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	// 程序的Context对象
	private Context mContext;

	/** 获取CrashHandler实例 ,单例模式 */

	public static CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		String logdir;
		logdir = "/sdcard/log";
		File file = new File(logdir);
		boolean mkSuccess;
		if (!file.isDirectory()) {
			mkSuccess = file.mkdirs();
			if (!mkSuccess) {
				mkSuccess = file.mkdirs();
			}
		}
		try {
			FileWriter fw = new FileWriter(logdir + File.separator
					+ "error.txt", true);
			StackTraceElement[] stackTrace = arg1.getStackTrace();
			String msg = "";
			msg = msg + new Date() + "\n";

			msg = msg + arg1.getMessage() + "\n";
			for (int i = 0; i < stackTrace.length; i++) {
				msg = msg + "file:" + stackTrace[i].getFileName() + " class:"
						+ stackTrace[i].getClassName() + " method:"
						+ stackTrace[i].getMethodName() + " line:"
						+ stackTrace[i].getLineNumber() + "\n";
			}
			msg = msg+"\n";
			fw.write(msg , 0, msg.length());
			fw.close();
		} catch (IOException e) {
			Log.e("crash handler", "load file failed...", e.getCause());
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

}
