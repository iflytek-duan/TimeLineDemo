package com.zihao.timelinedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO<自定义时光轴></>
 * Created by zihao on 2016/5/31 16:25.
 */
public class TimeLine extends View {

    // circle type
    private static final int CIRCLE_TYPE_SOILD = 0;
    private static final int CIRLCE_TYPE_HOLLOW = 1;
    // line type
    private static final int LINE_TYPE_SOILD = 0;
    private static final int LINE_TYPE_DOTTED = 1;

    private int width, height;
    private int centerX, centerY;
    private int radius;
    private int lineWidth, lineHeight;
    private Paint circlePaint, linePaint;
    private int circleType = CIRCLE_TYPE_SOILD;
    private int lineType = LINE_TYPE_SOILD;
    private int circleColor = Color.BLUE;
    private int lineColor = Color.GREEN;

    public TimeLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // init circlePaint
        circlePaint = new Paint();
        switch (circleType) {
            case CIRCLE_TYPE_SOILD:
                circlePaint.setStyle(Paint.Style.FILL);
                break;
            case CIRLCE_TYPE_HOLLOW:
                circlePaint.setStyle(Paint.Style.STROKE);
                break;
        }
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);

        // init linePaint
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        if (lineType == LINE_TYPE_DOTTED) {
            Path path = new Path();
            path.moveTo(0, 10);
            path.lineTo(480, 10);
            PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
            linePaint.setPathEffect(effects);
        }
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // get view width and height
        if (width == 0 || height == 0) {
            int minimumWidth = getSuggestedMinimumWidth();
            int minimumHeight = getSuggestedMinimumHeight();
            width = resolveMeasured(widthMeasureSpec, minimumWidth);
            height = resolveMeasured(heightMeasureSpec, minimumHeight);
            centerX = width / 2;
            centerY = height / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawLine(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
    }

    private void drawLine(Canvas canvas) {
        int startY = centerY + radius;
        int stopY = startY + lineHeight;
        canvas.drawLine(centerX, startY, centerX, stopY, linePaint);
    }

    /**
     * TODO<测绘的处置>
     *
     * @return int
     */
    private int resolveMeasured(int measureSpec, int desired) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }
}