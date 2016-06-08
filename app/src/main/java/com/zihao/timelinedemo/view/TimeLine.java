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

    private int circleType = CIRCLE_TYPE_SOILD;
    private int lineType = LINE_TYPE_SOILD;
    private int circleColor = Color.BLUE;
    private int lineColor = Color.BLACK;

    private int width, height;
    private Paint circlePaint, linePaint;

    // circle params
    private int centerX, centerY;// X/Y中心点
    private int radius = 20;// 圆点半径
    private int pointCount = 5;// 绘制点的总数
    private int verticalAvgSpace;// 圆点间的竖直间隔距离

    // line params
    private int lineWidth = 4;// 线宽

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
            verticalAvgSpace = height / pointCount;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawCircle(canvas);
    }

    /**
     * 绘制线条
     * @param canvas
     *  // 画布
     */
    private void drawLine(Canvas canvas) {
        // 绘制一条与View相等高度的线即可--后续用圆点覆盖
        canvas.drawLine(centerX - lineWidth / 2, 0, centerX + lineWidth / 2, height, linePaint);
    }

    /**
     * 绘制圆点
     * @param canvas
     *  // 画布
     */
    private void drawCircle(Canvas canvas) {
        int startY = verticalAvgSpace / 2;

        for(int i = 0;i < pointCount;i++){
            canvas.drawCircle(centerX, startY, radius, circlePaint);
            startY += verticalAvgSpace;
        }
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
        this.invalidate();
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