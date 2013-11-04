package com.missionse.securityhelper.map;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.missionse.securityhelper.R;

public class MapView extends View implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {
	private Paint paint;
	private Bitmap mapImage;
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleGestureDetector;

	private Matrix transformationMatrix;
	private Rect transformedLocation;
	private boolean scaling;
	private float scale;
	private float xTranslation, yTranslation;

	private ArrayList<Rect> locations;

	public MapView(final Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.argb(200, 0, 125, 125));
		paint.setStrokeWidth(15);
		paint.setStyle(Style.FILL);

		mapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor1);

		transformationMatrix = new Matrix();
		xTranslation = 0.0f;
		yTranslation = 0.0f;
		scale = 2.0f;

		transformedLocation = new Rect();
		locations = new ArrayList<Rect>();
		locations.add(new Rect(10, 105, 135, 190));

		gestureDetector = new GestureDetector(context, this);
		scaleGestureDetector = new ScaleGestureDetector(context, this);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		transformationMatrix.reset();
		transformationMatrix.postScale(scale, scale);
		transformationMatrix.postTranslate(xTranslation, yTranslation);
		canvas.drawBitmap(mapImage, transformationMatrix, paint);

		for (Rect location : locations) {
			transformedLocation.set(
					(int) (location.left * scale + xTranslation),
					(int) (location.top * scale + yTranslation),
					(int) (location.right * scale + xTranslation),
					(int) (location.bottom * scale + yTranslation));
			canvas.drawRect(transformedLocation, paint);
		}
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		scaleGestureDetector.onTouchEvent(event);
		gestureDetector.onTouchEvent(event);

		return true;
	}

	@Override
	public boolean onScale(final ScaleGestureDetector detector) {
		Log.e("MapView", "onScale");
		scale *= detector.getScaleFactor();
		invalidate();
		return true;
	}

	@Override
	public boolean onScaleBegin(final ScaleGestureDetector detector) {
		Log.e("MapView", "onScaleBegin");
		scaling = true;
		return true;
	}

	@Override
	public void onScaleEnd(final ScaleGestureDetector detector) {
		Log.e("MapView", "onScaleEnd");
		scaling = false;
	}

	@Override
	public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
		if (!scaling) {
			Log.e("MapView", "onScroll");
			xTranslation -= distanceX;
			yTranslation -= distanceY;
			invalidate();
		}

		return true;
	}

	@Override
	public boolean onDown(final MotionEvent e) {
		Log.e("MapView", "onDown");
		return true;
	}

	@Override
	public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
		return true;
	}

	@Override
	public void onLongPress(final MotionEvent e) {
	}

	@Override
	public void onShowPress(final MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(final MotionEvent e) {
		return false;
	}
}
