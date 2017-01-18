package cn.vacuumflask.piechartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/17 0017.
 * 饼状图
 */
public class PieChartView extends View {

    private List<Paint> list;//画笔的集合
    private int circleX;//圆心x轴
    private int circleY;//圆心y轴
    private float radius;//半径
    private List<Integer> dataList;//数据集合
    private int max;//最大值
    protected Paint circlePaint;

    public PieChartView(Context context) {
        super(context);
        initPaint();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        list = new ArrayList<>();
        dataList = new ArrayList<>();
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(1.8f);

    }

    private Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1.8f);
        return paint;
    }

    public void setColorList(List<Integer> colorList) {
        if (colorList == null || colorList.size() == 0) {
            return;
        }
        for (Integer integer : colorList) {
            Paint paint = createPaint(integer);
            list.add(paint);
        }
    }


    public void setDataList(List<Integer> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        this.dataList = dataList;
        max = 0;
        for (Integer integer : this.dataList) {
            max += integer;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(MainActivity.TAG, "widthMeasureSpec: " + widthMeasureSpec);
        Log.i(MainActivity.TAG, "heightMeasureSpec: " + heightMeasureSpec);
        int width = measureValue(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = measureValue(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureValue(int minSize, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (mode == MeasureSpec.EXACTLY) {//固定值或者match_parent
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.min(minSize, size);
        } else {
            result = minSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(MainActivity.TAG, "w: " + w);
        Log.i(MainActivity.TAG, "h: " + h);
        Log.i(MainActivity.TAG, "oldw: " + oldw);
        Log.i(MainActivity.TAG, "oldh: " + oldh);
        circleX = w / 2;
        circleY = h / 2;
        radius = circleX - 10;//半径赋值
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(MainActivity.TAG, "onLayout: ------------------------------------------------------");
        Log.i(MainActivity.TAG, "left: " + left);
        Log.i(MainActivity.TAG, "top: " + top);
        Log.i(MainActivity.TAG, "right: " + right);
        Log.i(MainActivity.TAG, "bottom: " + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆
        canvas.drawCircle(circleX, circleY, radius, circlePaint);
        //画弧
        RectF rectF = new RectF(circleX - radius, circleY - radius, circleX + radius, circleY + radius);

        if (dataList == null || dataList.size() == 0) {
            Toast.makeText(getContext(), "数据不能空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (list == null || list.size() == 0) {
            Toast.makeText(getContext(), "颜色不能空", Toast.LENGTH_SHORT).show();
            return;
        }

        float startAngle = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int sweepAngle = dataList.get(i) * 360 / max;
            canvas.drawArc(rectF, startAngle, sweepAngle, true, list.get(i % list.size()));
            startAngle += sweepAngle;
        }
    }
}
