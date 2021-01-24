package com.example.chencj.myapplication.mpcharts;

/**
 * Created by Administrator on 2021/1/23.
 */


import android.app.Activity;


import com.example.chencj.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChartsDemo1Activity extends Activity {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts1_view);

        mChart = (LineChart) findViewById(R.id.chart);

        mChart.setDescription("Zhang Phil @ http://blog.csdn.net/zhangphil");
        mChart.setNoDataTextDescription("暂时尚无数据");

        mChart.setTouchEnabled(true);

        // 可拖曳
        mChart.setDragEnabled(true);

        // 可缩放
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        mChart.setPinchZoom(true);

        // 设置图表的背景颜色
        mChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();

        // 数据显示的颜色
        data.setValueTextColor(Color.WHITE);

        // 先增加一个空的数据，随后往里面动态添加
        mChart.setData(data);

        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mChart.getLegend();

        // 可以修改图表注解部分的位置
        // l.setPosition(LegendPosition.LEFT_OF_CHART);

        // 线性，也可是圆
        l.setForm(LegendForm.LINE);

        // 颜色
        l.setTextColor(Color.WHITE);

        // x坐标轴
        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);

        // 几个x坐标轴之间才绘制？
        xl.setSpaceBetweenLabels(5);

        // 如果false，那么x坐标轴将不可见
        xl.setEnabled(true);

        // 将X坐标轴放置在底部，默认是在顶部。
        xl.setPosition(XAxisPosition.BOTTOM);

        // 图表左边的y坐标轴线
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);

        // 最大值
        leftAxis.setAxisMaxValue(90f);

        // 最小值
        leftAxis.setAxisMinValue(40f);

        // 不一定要从0开始
        leftAxis.setStartAtZero(false);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);



        // 每点击一次按钮，增加一个点
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addEntry();
            }
        });
        // 每点击一次按钮，增加一个点
        Button addButton1 = (Button) findViewById(R.id.button1);
        addButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideEntry();
            }
        });
    }

    private void hideEntry() {
        LineData data = mChart.getData();
        LineDataSet set = (LineDataSet)data.getDataSetByIndex(0);
        if(set!=null){
            hintIndex = 1;
            // 折线的颜色
            set.setColor(android.R.color.transparent);

            set.setCircleColor(android.R.color.transparent);
            //set.setLineWidth(10f);
            //set.setCircleSize(5f);
            //set.setFillAlpha(128);
            set.setFillColor(android.R.color.transparent);
            set.setHighLightColor(android.R.color.transparent);
            set.setValueTextColor(android.R.color.transparent);
            //set.setValueTextSize(10f);
            //set.setDrawValues(true);
        }
        mChart.invalidate();
    }

    private int hintIndex = -1;



    // 添加进去一个坐标点
    private void addEntry() {

        LineData data = mChart.getData();

        // 每一个LineDataSet代表一条线，每张统计图表可以同时存在若干个统计折线，这些折线像数组一样从0开始下标。
        // 本例只有一个，那么就是第0条折线
        LineDataSet set = (LineDataSet)data.getDataSetByIndex(0);
        LineDataSet set1 = (LineDataSet)data.getDataSetByIndex(1);
        LineDataSet set2 = (LineDataSet)data.getDataSetByIndex(2);

        // 如果该统计折线图还没有数据集，则创建一条出来，如果有则跳过此处代码。
        if (set == null) {
            set = createLineDataSet(0);
            data.addDataSet(set);
        }

        // 如果该统计折线图还没有数据集，则创建一条出来，如果有则跳过此处代码。
        if (set1 == null) {
            set1 = createLineDataSet(1);
            data.addDataSet(set1);
        }

        // 如果该统计折线图还没有数据集，则创建一条出来，如果有则跳过此处代码。
        if (set2 == null) {
            set2 = createLineDataSet(2);
            data.addDataSet(set2);
        }

        // 先添加一个x坐标轴的值
        // 因为是从0开始，data.getXValCount()每次返回的总是全部x坐标轴上总数量，所以不必多此一举的加1
        data.addXValue((data.getXValCount()) + "");
        // 生成随机测试数
        float f = (float) ((Math.random()) * 20 + 50);
        float f2 = (float) ((Math.random()) * 20 + 50);
        float f1 = (float) ((Math.random()) * 20 + 50);

        // set.getEntryCount()获得的是所有统计图表上的数据点总量，
        // 如从0开始一样的数组下标，那么不必多次一举的加1
        Entry entry = new Entry(f, set.getEntryCount());
        Entry entry1 = new Entry(f1, set1.getEntryCount());
        Entry entry2 = new Entry(f2, set2.getEntryCount());

        // 往linedata里面添加点。注意：addentry的第二个参数即代表折线的下标索引。
        // 因为本例只有一个统计折线，那么就是第一个，其下标为0.
        // 如果同一张统计图表中存在若干条统计折线，那么必须分清是针对哪一条（依据下标索引）统计折线添加。

        data.addEntry(entry, 0);
        data.addEntry(entry1, 1);
        data.addEntry(entry2, 2);

        // 像ListView那样的通知数据更新
        mChart.notifyDataSetChanged();

        // 当前统计图表中最多在x轴坐标线上显示的总量
        mChart.setVisibleXRangeMaximum(5);

        // y坐标轴线最大值
        //mChart.setVisibleYRange(30, AxisDependency.LEFT);

        // 将坐标移动到最新
        // 此代码将刷新图表的绘图
        mChart.moveViewToX(data.getXValCount() - 5);

        // mChart.moveViewTo(data.getXValCount()-7, 55f,
        // AxisDependency.LEFT);
    }

    // 添加进去一个坐标点
    private void addEntry1() {
    }

    // 初始化数据集，添加一条统计折线，可以简单的理解是初始化y坐标轴线上点的表征
    private LineDataSet createLineDataSet(int index) {

        LineDataSet set = new LineDataSet(null, "动态添加的数据");
        set.setAxisDependency(AxisDependency.LEFT);

        if(hintIndex == index){
            // 折线的颜色
            set.setColor(android.R.color.transparent);

            set.setCircleColor(android.R.color.transparent);

            set.setFillColor(android.R.color.transparent);
            set.setHighLightColor(android.R.color.transparent);
            set.setValueTextColor(android.R.color.transparent);
            //set.setValueTextSize(10f);
            set.setDrawValues(false);
        }else {
            // 折线的颜色
            set.setColor(ColorTemplate.PASTEL_COLORS[index]);

            set.setCircleColor(Color.WHITE);
//            set.setLineWidth(10f);
//            set.setCircleSize(5f);
//            set.setFillAlpha(128);
            set.setFillColor(ColorTemplate.PASTEL_COLORS[index]);
            set.setHighLightColor(Color.GREEN);
            set.setValueTextColor(Color.WHITE);
            set.setDrawValues(true);

        }
        set.setLineWidth(10f);
        set.setCircleSize(5f);
        set.setFillAlpha(128);
         set.setValueTextSize(10f);

        return set;
    }
}
