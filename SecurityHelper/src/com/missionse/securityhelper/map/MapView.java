package com.missionse.securityhelper.map;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.SecurityHelper;

public class MapView extends View implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {
	private Paint paint;
	private Bitmap mapImage, cameraImage;
	private Rect cameraRect;
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleGestureDetector;

	private Matrix transformationMatrix;
	private Rect transformedLocation;
	private boolean scaling;
	private float scale;
	private float xTranslation, yTranslation;

	private HashMap<String, Rect> locations;

	private SecurityHelper securityHelper;

	public MapView(final Activity context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.argb(200, 0, 125, 125));
		paint.setStrokeWidth(15);
		paint.setStyle(Style.FILL);

		mapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor1);
		cameraImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.camera_icon);
		cameraRect = new Rect(0, 0, cameraImage.getWidth(), cameraImage.getHeight());

		transformationMatrix = new Matrix();
		xTranslation = 0.0f;
		yTranslation = 0.0f;
		scale = 2.0f;

		transformedLocation = new Rect();
		locations = new HashMap<String, Rect>();
		locations.put("CompassRoom", new Rect(10, 105, 135, 190));
		locations.put("C4Door", new Rect(10, 815, 120, 870));
		locations.put("Camera", new Rect(275, 95, 325, 160));

		gestureDetector = new GestureDetector(context, this);
		scaleGestureDetector = new ScaleGestureDetector(context, this);

		securityHelper = (SecurityHelper) context;
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		transformationMatrix.reset();
		transformationMatrix.postScale(scale, scale);
		transformationMatrix.postTranslate(xTranslation, yTranslation);
		canvas.drawBitmap(mapImage, transformationMatrix, paint);

		for (Rect location : locations.values()) {
			transformLocation(location, transformedLocation);
			canvas.drawRect(transformedLocation, paint);
		}

		transformLocation(locations.get("Camera"), transformedLocation);
		canvas.drawBitmap(cameraImage, cameraRect, transformedLocation, paint);
	}

	private void transformLocation(final Rect location, final Rect newLocation) {
		newLocation.set(
				(int) (location.left * scale + xTranslation),
				(int) (location.top * scale + yTranslation),
				(int) (location.right * scale + xTranslation),
				(int) (location.bottom * scale + yTranslation));
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		scaleGestureDetector.onTouchEvent(event);
		gestureDetector.onTouchEvent(event);

		invalidate();

		return true;
	}

	@Override
	public boolean onScale(final ScaleGestureDetector detector) {
		scale *= detector.getScaleFactor();
		return true;
	}

	@Override
	public boolean onScaleBegin(final ScaleGestureDetector detector) {
		scaling = true;
		return true;
	}

	@Override
	public void onScaleEnd(final ScaleGestureDetector detector) {
		scaling = false;
	}

	@Override
	public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
		if (!scaling) {
			xTranslation -= distanceX;
			yTranslation -= distanceY;
		}

		return true;
	}

	@Override
	public boolean onSingleTapUp(final MotionEvent e) {
		Rect correctedLocation = new Rect();
		int xTouch = (int) e.getX();
		int yTouch = (int) e.getY();

		for (String location : locations.keySet()) {
			transformLocation(locations.get(location), correctedLocation);
			if (correctedLocation.contains(xTouch, yTouch)) {
				if (location.equals("CompassRoom")) {
					securityHelper.showLocation(location);
				} else if (location.equals("C4Door")) {
					securityHelper.showExitDetail(location);
				} else if (location.equals("Camera")) {
					securityHelper.showSecurityVideo(location);
				}
			}
		}

		return true;
	}

	@Override
	public boolean onDown(final MotionEvent e) {
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
}
