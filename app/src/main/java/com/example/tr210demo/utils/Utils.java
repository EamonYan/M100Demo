package com.example.tr210demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemProperties;


public class Utils {

	
	public static String getPayMsg() {
		String msg = "";
		msg = "          " + "支付签购单           \n\n";
		msg += "商户存根(MERCHANT COPY)\n";
		msg += "商户名称（MERCHANT NAME）：中智金云\n";
		msg += "商户编号（MERCHANT NO）：0123456789\n";
		msg += "终端编号（TERMINAL NO）：01\n";
		msg += "操作员号（OPERATOR）：01\n";

		// msg += "收款账户 : " + tradeDetail.pay_account + "\n";
		msg += "交易类型(TRANS TYPE) :银行卡交易\n";
		msg += "支付订单号：20161129154830\n";
		msg += "日期/时间（DATE/TIME）：2016-11-29 15:49:30\n";
		msg += "金额（AMOUNT）：\n     RMB 0.01\n";
		msg += "\n";
		msg += "（同意上述款项）    \n ";
		msg += "（持卡人签名）\n";
		msg += "\n\n";
		msg += "--------------------------";
		msg += "\n\n\n\n";
		return msg;
	}
	
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 对图片进行压缩(不去除透明度)
	 *
	 * @param bitmapOrg
	 */
	public static Bitmap compressBitmap(Bitmap bitmapOrg, int minification) {
		// 加载需要操作的图片，这里是一张图片
//	       Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),R.drawable.alipay);
		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		// 定义预转换成的图片的宽度和高度
		int newWidth = width / minification;
		int newHeight = height / minification;
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//	       BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
		return resizedBitmap;
	}

	public static void openLight(){
		SystemProperties.set("persist.sys.newlight", "1");
	}

	public static void closeLight(){
		SystemProperties.set("persist.sys.newlight", "0");

	}
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}


}
