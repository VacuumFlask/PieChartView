package cn.vacuumflask.piechartview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PieChartView chartView = (PieChartView) findViewById(R.id.pieChartView);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        chartView.setColorList(colors);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(95);
        list.add(57);
        list.add(82);
        list.add(16);
        chartView.setDataList(list);
    }
}
