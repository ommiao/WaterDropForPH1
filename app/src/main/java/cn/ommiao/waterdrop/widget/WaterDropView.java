package cn.ommiao.waterdrop.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import cn.ommiao.waterdrop.R;

public class WaterDropView extends View {

    private int screenWidth, middleX, middleSpace = 57;

    private int ovalWidth = 380, ovalHeight = 250;

    private int ovalAngle = 78;

    private Bitmap camera;

    public WaterDropView(Context context) {
        this(context, null);
    }

    public WaterDropView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterDropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WaterDropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        screenWidth = getScreenWidth();
        middleX = screenWidth / 2;
        camera = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_camera);
    }

    Paint paint = new Paint();
    Path path  = new Path();
    RectF rectFL = new RectF();
    RectF rectFR = new RectF();
    RectF rectFC = new RectF();
    RectF rectFT = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        rectFL.left = middleX - middleSpace - ovalWidth;
        rectFL.top = 0;
        rectFL.right = middleX - middleSpace;
        rectFL.bottom = ovalHeight;
        rectFR.left = middleX + middleSpace;
        rectFR.top = 0;
        rectFR.right = middleX + middleSpace + ovalWidth;
        rectFR.bottom = ovalHeight;

        rectFT.left = rectFL.left + ovalWidth / 2;
        rectFT.top = 0;
        rectFT.right = rectFR.left + ovalWidth / 2;
        rectFT.bottom = 1;
        canvas.drawRect(rectFT, paint);

        path.arcTo(rectFL, 270 + ovalAngle, -ovalAngle);
        path.lineTo(middleX + ovalWidth / 2, 0);
        path.arcTo(rectFR, 270, -ovalAngle);
        path.close();

        canvas.drawPath(path, paint);
        int top = 25;
        int offset = 7;
        rectFC.left = middleX - middleSpace - offset;
        rectFC.top = top;
        rectFC.right = middleX + middleSpace + offset;
        rectFC.bottom = top + middleSpace * 2;
        canvas.drawArc(rectFC, 0, 180, true, paint);
        canvas.drawBitmap(camera, middleX - camera.getWidth() / 2, ovalHeight / 2 - camera.getHeight() - 3, null);
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
