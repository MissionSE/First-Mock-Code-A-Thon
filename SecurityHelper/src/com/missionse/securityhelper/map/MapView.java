package com.missionse.securityhelper.map;

import java.util.ArrayList;
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
import android.widget.Toast;

import com.missionse.securityhelper.R;
import com.missionse.securityhelper.SecurityHelper;

public class MapView extends View implements GestureDetector.OnGestureListener,
		ScaleGestureDetector.OnScaleGestureListener {
	private Paint paint;
	private Bitmap mapImage, cameraImage, personImage;
	private Rect cameraRect, personRect;
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleGestureDetector;

	private Matrix transformationMatrix;
	private Rect transformedLocation;
	private boolean scaling;
	private float scale;
	private float xTranslation, yTranslation;

	private HashMap<String, Rect> locations;
	private HashMap<String, Integer> colors;
	private Integer locationColor, emergencyColor;
	private int serverUp, serverDown;

	private boolean showEmergencyRoute;
	private ArrayList<Rect> emergencyRoutes;

	private boolean showPerson;

	private SecurityHelper securityHelper;

	public MapView(final Activity context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.argb(200, 0, 125, 125));
		paint.setStrokeWidth(15);
		paint.setStyle(Style.FILL);

		locationColor = Color.argb(200, 0, 125, 125);
		emergencyColor = Color.argb(200, 255, 0, 0);
		serverUp = Color.argb(255, 0, 255, 0);
		serverDown = Color.argb(255, 255, 0, 0);

		mapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor1);
		cameraImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.camera_icon);
		personImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.person_icon);
		cameraRect = new Rect(0, 0, cameraImage.getWidth(), cameraImage.getHeight());
		personRect = new Rect(0, 0, personImage.getWidth(), personImage.getHeight());

		showPerson = false;

		transformationMatrix = new Matrix();
		xTranslation = 0.0f;
		yTranslation = 0.0f;
		scale = 2.0f;
		transformationMatrix.setScale(scale, scale);

		transformedLocation = new Rect();
		locations = new HashMap<String, Rect>();
		locations.put("CompassRoom", new Rect(10, 105, 135, 190));
		locations.put("C4Door", new Rect(10, 815, 120, 870));
		locations.put("Camera", new Rect(275, 95, 325, 145));
		locations.put("Person", new Rect(325, 180, 375, 230));
		locations.put("Emergency", new Rect(550, 675, 680, 730));
		locations.put("Server1", new Rect(554, 810, 568, 830));
		locations.put("Server2", new Rect(568, 810, 582, 830));
		locations.put("Server3", new Rect(582, 810, 615, 830));
		locations.put("Server4", new Rect(554, 850, 615, 870));

		colors = new HashMap<String, Integer>();
		colors.put("CompassRoom", locationColor);
		colors.put("C4Door", locationColor);
		colors.put("Camera", locationColor);
		colors.put("Person", locationColor);
		colors.put("Emergency", emergencyColor);
		colors.put("Server1", serverUp);
		colors.put("Server2", serverDown);
		colors.put("Server3", serverUp);
		colors.put("Server4", serverUp);

		showEmergencyRoute = false;
		emergencyRoutes = new ArrayList<Rect>();
		emergencyRoutes.add(new Rect(210, 730, 575, 770));
		emergencyRoutes.add(new Rect(210, 730, 240, 895));
		emergencyRoutes.add(new Rect(100, 870, 240, 895));
		emergencyRoutes.add(new Rect(10, 845, 120, 870));

		gestureDetector = new GestureDetector(context, this);
		scaleGestureDetector = new ScaleGestureDetector(context, this);

		securityHelper = (SecurityHelper) context;
	}

	public void showPerson(final boolean show) {
		showPerson = show;
		invalidate();
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		transformationMatrix.reset();
		transformationMatrix.postScale(scale, scale);
		transformationMatrix.postTranslate(xTranslation, yTranslation);
		canvas.drawBitmap(mapImage, transformationMatrix, paint);

		for (String location : locations.keySet()) {
			if (!location.equals("Camera") && !location.equals("Person")) {
				transformLocation(locations.get(location), transformedLocation);
				paint.setColor(colors.get(location));
				canvas.drawRect(transformedLocation, paint);
			}
		}

		if (showEmergencyRoute) {
			for (Rect route : emergencyRoutes) {
				transformLocation(route, transformedLocation);
				paint.setColor(emergencyColor);
				canvas.drawRect(transformedLocation, paint);
			}
		}

		transformLocation(locations.get("Camera"), transformedLocation);
		canvas.drawBitmap(cameraImage, cameraRect, transformedLocation, paint);

		if (showPerson) {
			transformLocation(locations.get("Person"), transformedLocation);
			canvas.drawBitmap(personImage, personRect, transformedLocation, paint);
		}
	}

	private void transformLocation(final Rect location, final Rect newLocation) {
		newLocation.set((int) (location.left * scale + xTranslation), (int) (location.top * scale + yTranslation),
				(int) (location.right * scale + xTranslation), (int) (location.bottom * scale + yTranslation));
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
					securityHelper.showLocation();
				} else if (location.equals("C4Door")) {
					securityHelper.showExitDetail();
				} else if (location.equals("Camera")) {
					securityHelper.showSecurityVideo();
				} else if (location.equals("Emergency")) {
					Toast.makeText(getContext(), "Fire! (Error Code: 06)", Toast.LENGTH_SHORT).show();
					showEmergencyRoute = !showEmergencyRoute;
				} else if (location.equals("Server2")) {
					Toast.makeText(getContext(), "Server overheated! (Error Code: 041)", Toast.LENGTH_SHORT).show();
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
