/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tr210demo.view;

import java.util.Collection;
import java.util.HashSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.tr210demo.camera.CameraManager;
import com.google.zxing.ResultPoint;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 */
@SuppressLint("DrawAllocation")
public final class ViewFinderView extends View {
	private static final String TAG = "log";

	private Context context;
	/**
	 * ˢ�½����ʱ��
	 */
	private static final long ANIMATION_DELAY = 10L;
	private static final int OPAQUE = 0xFF;

	/**
	 * �ĸ��߽Ƕ�Ӧ�ĳ���
	 */
	private int ScreenRate;

	/**
	 * �ĸ��߽Ƕ�Ӧ�Ŀ��
	 */
	private static final int CORNER_WIDTH = 10;
	/**
	 * ɨ����е��м��ߵĿ��
	 */
	private static final int MIDDLE_LINE_WIDTH = 6;

	/**
	 * ɨ����е��м��ߵ���ɨ������ҵļ�϶
	 */
	private static final int MIDDLE_LINE_PADDING = 5;

	/**
	 * �м�������ÿ��ˢ���ƶ��ľ���
	 */
	private static final int SPEEN_DISTANCE = 5;

	/**
	 * �ֻ�����Ļ�ܶ�
	 */
	private static float density;
	/**
	 * �����С
	 */
	private static final int TEXT_SIZE = 13;
	/**
	 * �������ɨ�������ľ���
	 */
	private static final int TEXT_PADDING_TOP = 30;

	/**
	 * ���ʶ��������
	 */
	private Paint paint;

	/**
	 * �м们���ߵ����λ��
	 */
	private int slideTop;

	/**
	 * �м们���ߵ���׶�λ��
	 */
	private int slideBottom;

	/**
	 * ��ɨ��Ķ�ά��������������û��������ܣ���ʱ������
	 */
	private Bitmap resultBitmap;

	private final int resultPointColor;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;
	private String bottomText = "����";
	/**
	 * ����Բ�ǵĿ��
	 */
	private static int OUTSIDE_RECT_CONER_WIDTH = 20;
	/**
	 * ����Բ�ǵĿ��
	 */
	private static int INSIDE_RECT_CONER_WIDTH = 10;
	/**
	 * ���߱߳�
	 */
	private static int OUTSIDE_LINE_WIDTH = 60;

	boolean isFirst;
	
	/**
	 * �м����Ƿ���ʾ
	 */
	private boolean isMiddleLineShow = true;
	
	

	public boolean isMiddleLineShow() {
		return isMiddleLineShow;
	}

	public void setMiddleLineShow(boolean isMiddleLineShow) {
		this.isMiddleLineShow = isMiddleLineShow;
	}

	public ViewFinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		density = context.getResources().getDisplayMetrics().density;
		// ������ת����dp
		ScreenRate = (int) (20 * density);

		paint = new Paint();
		Resources resources = context.getResources();
		resultPointColor = Color.parseColor("#FFFF00");
		possibleResultPoints = new HashSet<ResultPoint>(5);
	}

	public void setBottomText(String bottomText) {
		this.bottomText = bottomText;
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		// �м��ɨ�����Ҫ�޸�ɨ���Ĵ�С��ȥCameraManager�����޸�
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}
		// ��ʼ���м��߻��������ϱߺ����±�
		if (!isFirst) {
			isFirst = true;
			slideTop = frame.top;
			slideBottom = frame.bottom;
		}
		// ��ȡ��Ļ�Ŀ�͸�
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {
//			paint.setColor(context.getResources().getColor(R.color.main_bg));
//
//			// ����ɨ����������Ӱ���֣����ĸ����֣�ɨ�������浽��Ļ���棬ɨ�������浽��Ļ����
//			// ɨ��������浽��Ļ��ߣ�ɨ�����ұߵ���Ļ�ұ�
//			canvas.drawRect(0, 0, width, frame.top, paint);
//			canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
//			canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
//			canvas.drawRect(0, frame.bottom, width, height, paint);
//
//			// ��ɨ�����ϵıߣ��ܹ�8������
//			paint.setColor(Color.YELLOW);
//			canvas.drawRect(frame.left + OUTSIDE_RECT_CONER_WIDTH, frame.top,
//					frame.left + OUTSIDE_LINE_WIDTH, frame.top
//							+ INSIDE_RECT_CONER_WIDTH, paint);
//			canvas.drawRect(frame.left, frame.top + OUTSIDE_RECT_CONER_WIDTH,
//					frame.left + INSIDE_RECT_CONER_WIDTH, frame.top
//							+ OUTSIDE_LINE_WIDTH, paint);
//			canvas.drawRect(frame.right - OUTSIDE_LINE_WIDTH, frame.top,
//					frame.right - OUTSIDE_RECT_CONER_WIDTH, frame.top
//							+ INSIDE_RECT_CONER_WIDTH, paint);
//			canvas.drawRect(frame.right - INSIDE_RECT_CONER_WIDTH, frame.top
//					+ OUTSIDE_RECT_CONER_WIDTH, frame.right, frame.top
//					+ OUTSIDE_LINE_WIDTH, paint);
//			canvas.drawRect(frame.left + OUTSIDE_RECT_CONER_WIDTH, frame.bottom
//					- INSIDE_RECT_CONER_WIDTH, frame.left + OUTSIDE_LINE_WIDTH,
//					frame.bottom, paint);
//			canvas.drawRect(frame.left, frame.bottom - OUTSIDE_LINE_WIDTH,
//					frame.left + INSIDE_RECT_CONER_WIDTH, frame.bottom
//							- OUTSIDE_RECT_CONER_WIDTH, paint);
//			canvas.drawRect(frame.right - OUTSIDE_LINE_WIDTH, frame.bottom
//					- INSIDE_RECT_CONER_WIDTH, frame.right
//					- OUTSIDE_RECT_CONER_WIDTH, frame.bottom, paint);
//			canvas.drawRect(frame.right - INSIDE_RECT_CONER_WIDTH, frame.bottom
//					- OUTSIDE_LINE_WIDTH, frame.right, frame.bottom
//					- OUTSIDE_RECT_CONER_WIDTH, paint);
//
//			// ��ɨ�����ϵ�Բ�ǣ��ܹ�4������
//			paint.setStrokeWidth(INSIDE_RECT_CONER_WIDTH);
//			paint.setStyle(Paint.Style.STROKE);
//			
//			RectF rect_left_top = new RectF(frame.left + 5, frame.top
//					+ INSIDE_RECT_CONER_WIDTH / 2, frame.left
//					+ OUTSIDE_RECT_CONER_WIDTH * 2 - INSIDE_RECT_CONER_WIDTH
//					/ 2, frame.top + OUTSIDE_RECT_CONER_WIDTH * 2
//					- INSIDE_RECT_CONER_WIDTH / 2);
//			canvas.drawArc(rect_left_top, 180, 90, false, paint);
//
//			RectF rect_right_top = new RectF(frame.right
//					- OUTSIDE_RECT_CONER_WIDTH * 2 + INSIDE_RECT_CONER_WIDTH
//					/ 2, frame.top + INSIDE_RECT_CONER_WIDTH / 2, frame.right
//					- INSIDE_RECT_CONER_WIDTH / 2, frame.top
//					+ OUTSIDE_RECT_CONER_WIDTH * 2 - INSIDE_RECT_CONER_WIDTH
//					/ 2);
//			canvas.drawArc(rect_right_top, 270, 90, false, paint);
//
//			RectF rect_left_bottom = new RectF(frame.left
//					+ INSIDE_RECT_CONER_WIDTH / 2, frame.bottom
//					- OUTSIDE_RECT_CONER_WIDTH * 2 + INSIDE_RECT_CONER_WIDTH
//					/ 2, frame.left + OUTSIDE_RECT_CONER_WIDTH * 2
//					- INSIDE_RECT_CONER_WIDTH / 2, frame.bottom
//					- INSIDE_RECT_CONER_WIDTH / 2);
//			canvas.drawArc(rect_left_bottom, 90, 90, false, paint);
//
//			RectF rect_right_bottom = new RectF(frame.right
//					- OUTSIDE_RECT_CONER_WIDTH * 2 + INSIDE_RECT_CONER_WIDTH
//					/ 2, frame.bottom - OUTSIDE_RECT_CONER_WIDTH * 2
//					+ INSIDE_RECT_CONER_WIDTH / 2, frame.right
//					- INSIDE_RECT_CONER_WIDTH / 2, frame.bottom
//					- INSIDE_RECT_CONER_WIDTH / 2);
//			
//			canvas.drawArc(rect_right_bottom, 0, 90, false, paint);

			paint.setStyle(Paint.Style.FILL);

			// �����м����,ÿ��ˢ�½��棬�м���������ƶ�SPEEN_DISTANCE
			paint.setColor(Color.YELLOW);
			if (isMiddleLineShow) {
				slideTop += SPEEN_DISTANCE;
				if (slideTop >= frame.bottom) {
					slideTop = frame.top;
				}
//				canvas.drawRect(frame.left, slideTop
//						- MIDDLE_LINE_WIDTH / 2, frame.right,
//						slideTop + MIDDLE_LINE_WIDTH / 2, paint);
			}

			// ��ɨ����������
			paint.setColor(Color.WHITE);
			paint.setTextSize(TEXT_SIZE * density);
			paint.setAlpha(0x40);
			paint.setTypeface(Typeface.create("System", Typeface.BOLD));
			Rect rect = new Rect();
			paint.getTextBounds(bottomText, 0, bottomText.length(), rect);
            //�����ַ�����ռ���ؿ��
            int textWidths = rect.width();
//			canvas.drawText(
//					bottomText,
//					(frame.left + frame.right) / 2 - textWidths/2,
//					(float) (frame.bottom + (float) TEXT_PADDING_TOP * density),
//					paint);

			Collection<ResultPoint> currentPossible = possibleResultPoints;
			Collection<ResultPoint> currentLast = lastPossibleResultPoints;
			if (currentPossible.isEmpty()) {
				lastPossibleResultPoints = null;
			} else {
				possibleResultPoints = new HashSet<ResultPoint>(5);
				lastPossibleResultPoints = currentPossible;
				paint.setAlpha(OPAQUE);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 6.0f, paint);
				}
			}
			if (currentLast != null) {
				paint.setAlpha(OPAQUE / 2);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 3.0f, paint);
				}
			}
			// ֻˢ��ɨ�������ݣ������ط���ˢ��
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
					frame.right, frame.bottom);
		}
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}


}
