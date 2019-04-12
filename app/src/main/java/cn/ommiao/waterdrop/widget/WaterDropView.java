package cn.ommiao.waterdrop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class WaterDropView extends View {

    private int screenWidth, middleX, middleSpace = 57;

    private int ovalWidth = 400, ovalHeight = 250;

    private int ovalAngle = 80;

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
    }

    Paint paint = new Paint();
    Path path  = new Path();
    RectF rectFL = new RectF();
    RectF rectFR = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);


        rectFL.left = middleX - middleSpace - ovalWidth;
        rectFL.top = 0;
        rectFL.right = middleX - middleSpace;
        rectFL.bottom = ovalHeight;
        rectFR.left = middleX + middleSpace;
        rectFR.top = 0;
        rectFR.right = middleX + middleSpace + ovalWidth;
        rectFR.bottom = ovalHeight;

        path.arcTo(rectFL, 270 + ovalAngle, -ovalAngle);
        path.lineTo(middleX + ovalWidth / 2, 0);
        path.arcTo(rectFR, 270, -ovalAngle);
        path.close();

        canvas.drawPath(path, paint);
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
