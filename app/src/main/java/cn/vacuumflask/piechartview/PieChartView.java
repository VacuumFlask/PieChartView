package cn.vacuumflask.piechartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
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

    //设置颜色
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
        //赋最大值
        max = 0;
        for (Integer integer : this.dataList) {
            max += integer;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureValue(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = measureValue(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureValue(int minSize, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result;
        if (mode == MeasureSpec.EXACTLY) {//固定值或者match_parent
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {//wrap_content
            result = Math.min(minSize, size);
        } else {
            result = minSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleX = w / 2;
        circleY = h / 2;
        radius = circleX - 10;//半径赋值
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆
        canvas.drawCircle(circleX, circleY, radius, circlePaint);

        if (dataList == null || dataList.size() == 0) {
            Toast.makeText(getContext(), "数据不能空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (list == null || list.size() == 0) {
            Toast.makeText(getContext(), "颜色不能空", Toast.LENGTH_SHORT).show();
            return;
        }

        //画弧
        RectF rectF = new RectF(circleX - radius, circleY - radius, circleX + radius, circleY + radius);
        float startAngle = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int sweepAngle = dataList.get(i) * 360 / max;//扫过的弧度
            canvas.drawArc(rectF, startAngle, sweepAngle, true, list.get(i % list.size()));
            startAngle += sweepAngle;//开始角度
        }
    }
}
