package com.missionse.securityhelper.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

import com.missionse.securityhelper.R;

public class MapView extends View {
	private Paint paint;
	private Bitmap mapImage;

	public MapView(final Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(15);
		paint.setStyle(Style.STROKE);

		mapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.mselogo);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mapImage, 0, 0, paint);
		canvas.drawCircle(15, 15, 50, paint);
	}
}
