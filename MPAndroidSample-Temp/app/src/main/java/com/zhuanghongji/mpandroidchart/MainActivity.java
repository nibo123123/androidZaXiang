package com.zhuanghongji.mpandroidchart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnChartGestureListener {

    private static final String TAG = "myTag";
    private int[] mDataYs = new int[]{90, 50, 60, 30, 40, 80, 70, 20, 60, 10, 50, 70, 80, 90};

    //    private int[] mDataYs1 = new int[]{90,50,60,30,40,80,70,20,60,10,50,70,80,90};
//    private int[] mDataYs2 = new int[]{60,50,30,40,60,70,50,10,40,20,70,80,90,100};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.i(TAG,new MyValueFormatter().getFormattedValue())

        //折线图
        LineChart mLineChart = (LineChart) findViewById(R.id.line_chart);
        setLineChart(mLineChart);
        loadLineChartData(mLineChart);
//        loadDoubleLineChartData(mLineChart, this);

        //柱形图
        BarChart mBarChart = (BarChart) findViewById(R.id.bar_chart);
        setBarChart(mBarChart);
        loadBarChartData(mBarChart);

        //饼形图
        PieChart mPieChart = (PieChart) findViewById(R.id.pie_chart);
        setPieChart(mPieChart);
        loadPieChartData(mPieChart);
    }


    private void loadPieChartData(PieChart chart) {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry(mDataYs[i], i));
        }

        PieDataSet mPieDataSet = new PieDataSet(entries, "");

        // space between slices
        mPieDataSet.setSliceSpace(2f);
        mPieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("第一季度");
        xValues.add("第二季度");
        xValues.add("第三季度");
        xValues.add("第四季度");
        PieData mPieData = new PieData(xValues, mPieDataSet);

        // set data
        chart.setData(mPieData);

        //设置动画
        chart.animateXY(900, 900);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

        mPieData.setValueFormatter(new PercentFormatter());//设置显示成百分比
//        mChartData.setValueTypeface(mTf);
        mPieData.setValueTextSize(18f);//设置文字大小
        mPieData.setValueTextColor(Color.WHITE);

        // 设置动画
        chart.animateX(8000);
        chart.animateY(8000);
        chart.animateXY(8000,8000);
        chart.animateY(8000, Easing.EasingOption.EaseOutBounce);
    }

    /**
     * 设置饼形图样式
     *
     * @param chart
     */
    private void setPieChart(PieChart chart) {
        // apply styling
        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(60f);

        chart.setDescription("zhuanghongji");
        chart.setDescriptionTextSize(36f);

        chart.setCenterText("MPChart\nAndroid");
        chart.setCenterTextSize(18f);
//        chart.setCenterTextRadiusPercent(0.8f);
//        chart.setCenterTextRadiusPercent(3f);
        chart.setCenterTextRadiusPercent(1f);
//        chart.setCenterTextTypeface(mTf);

        chart.setDrawSliceText(false);
        chart.setUsePercentValues(false);
        chart.setRotationAngle(0f);

//        chart.setCenterTextRadiusPercent(0.9f);

        // 设置颜色
        chart.setCenterTextColor(Color.GREEN);
        chart.setHoleColor(Color.BLACK);
        chart.setHoleColorTransparent(false);
//        chart.setBackgroundColor(Color.BLUE);
        chart.setDescriptionColor(Color.BLUE);
//        chart.setTransparentCircleColor(Color.RED);
        chart.setDrawingCacheBackgroundColor(Color.YELLOW);
//        chart.setColor
//        chart.set
//        chart.set

    }

    /**
     * 加载并设置柱形图的数据
     *
     * @param chart
     */
    private void loadBarChartData(BarChart chart) {
        //所有数据点的集合
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(mDataYs[i], i));
        }
        //柱形数据的集合
        BarDataSet mBarDataSet = new BarDataSet(entries, "barDataSet");
        mBarDataSet.setBarSpacePercent(20f);
        mBarDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);//设置每条柱子的颜色
        mBarDataSet.setHighLightAlpha(255);//设置点击后高亮颜色透明度
        mBarDataSet.setHighLightColor(Color.BLUE);

        //BarData表示挣个柱形图的数据
        BarData mBarData = new BarData(getXAxisShowLable(), mBarDataSet);
        chart.setData(mBarData);

        // 设置动画
//        chart.animateX(8000);
        chart.animateY(8000);
//        chart.animateXY(8000, 8000);
//        chart.animateY(8000, Easing.EasingOption.EaseInSine);
    }

    /**
     * 设置柱形图的样式
     *
     * @param chart
     */
    private void setBarChart(BarChart chart) {
        chart.setDescription("zhuanghongji");
        chart.setDrawGridBackground(false);//设置网格背景
        chart.setScaleEnabled(false);//设置缩放
        chart.setDoubleTapToZoomEnabled(false);//设置双击不进行缩放

        chart.setDrawValueAboveBar(true);
//        chart.setDrawBarShadow(true);
//        chart.setDrawHighlightArrow(false);

        //设置X轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置
//        xAxis.setTypeface(mTf); // 设置字体
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);

        // 查看setLimitLinesBehindData()方法，true或false的效果图
//        LimitLine xLimitLine = new LimitLine(2f,"is Behind");
//        xLimitLine.setLineColor(Color.BLUE);
//        xLimitLine.setTextColor(Color.BLUE);
//        xAxis.addLimitLine(xLimitLine);
//        xAxis.setDrawLimitLinesBehindData(true);
//        xAxis.setDrawLimitLinesBehindData(false);

        //获得左侧侧坐标轴
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
//        leftAxis.setAxisLineWidth(1.5f);
//        leftAxis.setSpaceTop(100f);

        leftAxis.setLabelCount(5, false);
//        leftAxis.setShowOnlyMinMax(true);


        //设置右侧坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false); // 右侧坐标轴线
        rightAxis.setDrawLabels(false); // 右侧坐标轴数组Label
//        rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5);
//        rightAxis.setDrawGridLines(false);
    }

    /**
     * 为折线图设置数据
     *
     * @param chart
     */
    private void loadLineChartData(final LineChart chart) {
        //所有线的List
        ArrayList<LineDataSet> allLinesList = new ArrayList<LineDataSet>();

        ArrayList<Entry> entryList = new ArrayList<Entry>();
        for (int i = 0; i < 8; i++) {
            //Entry(yValue,xIndex);一个Entry表示一个点，第一个参数为y值，第二个为X轴List的角标
            entryList.add(new Entry(mDataYs[i], i));
        }
        //LineDataSet可以看做是一条线
        LineDataSet dataSet = new LineDataSet(entryList, "dataLine");
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleSize(4.5f);
        dataSet.setHighLightColor(Color.RED); // 设置点击某个点时，横竖两条线的颜色
        dataSet.setDrawValues(false); // 是否在点上绘制Value
        dataSet.setValueTextColor(Color.GREEN);
        dataSet.setValueTextSize(24f);
        allLinesList.add(dataSet);

//        Typeface tf1 = Typeface.createFromAsset(getAssets(), "OpenSans-BoldItalic.ttf");
//        dataSet.setValueTypeface(tf1);

//        Typeface tf2 = Typeface.createFromAsset(getAssets(), "OpenSans-LightItalic.ttf");
//        dataSet.setValueTypeface(tf2);

        //LineData表示一个LineChart的所有数据(即一个LineChart中所有折线的数据)
        LineData mChartData = new LineData(getXAxisShowLable(), allLinesList);

        // set data
        chart.setData((LineData) mChartData);

        // 设置动画
//        chart.animateX(8000);
//        chart.animateY(8000);
//        chart.animateXY(8000, 8000);
        // 1
//        chart.animateY(8000, Easing.EasingOption.Linear);
//        chart.animateY(8000, Easing.EasingOption.EaseInQuad);
//
//        chart.animateY(8000, Easing.EasingOption.EaseOutQuad);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutQuad);
//
//        chart.animateY(8000, Easing.EasingOption.EaseInCubic);
//        chart.animateY(8000, Easing.EasingOption.EaseOutCubic);
        // 7
//        chart.animateY(8000, Easing.EasingOption.EaseInOutCubic);
//        chart.animateY(8000, Easing.EasingOption.EaseInQuart);
//
//        chart.animateY(8000, Easing.EasingOption.EaseOutQuart);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutQuart);
//
//        chart.animateY(8000, Easing.EasingOption.EaseInSine);
//        chart.animateY(8000, Easing.EasingOption.EaseOutSine);
        // 13
//        chart.animateY(8000, Easing.EasingOption.EaseInOutSine);
//        chart.animateY(8000, Easing.EasingOption.EaseInExpo);

//        chart.animateY(8000, Easing.EasingOption.EaseOutExpo);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutExpo);

//        chart.animateY(8000, Easing.EasingOption.EaseInCirc);
//        chart.animateY(8000, Easing.EasingOption.EaseOutCirc);
        // 19
//        chart.animateY(8000, Easing.EasingOption.EaseInOutCirc);
//        chart.animateY(8000, Easing.EasingOption.EaseInElastic);

//        chart.animateY(8000, Easing.EasingOption.EaseOutElastic);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutElastic);
        // 23
//        chart.animateY(8000, Easing.EasingOption.EaseInBack);
//        chart.animateY(8000, Easing.EasingOption.EaseOutBack);

//        chart.animateY(8000, Easing.EasingOption.EaseInOutBack);
//        chart.animateY(8000, Easing.EasingOption.EaseInBounce);
        // 27
//        chart.animateY(8000, Easing.EasingOption.EaseOutBounce);
//        chart.animateY(8000, Easing.EasingOption.EaseInOutBounce);

//        chart.animateY(2000, Easing.EasingOption.EaseInOutBounce);

        // Modifying the Viewport -> Restraining what's visible
//        chart.setVisibleXRangeMaximum(3);
//        chart.setVisibleXRangeMinimum(17);
        chart.setVisibleYRangeMaximum(50f,YAxis.AxisDependency.LEFT);
//        chart.setVisibleYRangeMaximum(70f,YAxis.AxisDependency.RIGHT);
//        chart.setViewPortOffsets(50,50,50,50);
//        chart.resetViewPortOffsets();
//        chart.setExtraOffsets(50,0,0,0);

        // Modifying the Viewport -> Moving the view
//        chart.moveViewToX(2);
        chart.moveViewToY(60,YAxis.AxisDependency.LEFT);
//        chart.fitScreen();

        // Modifying the Viewport -> Zooming
//        chart.zoomIn();
//        chart.zoomOut();
    }

    // 模拟加载公司四个季度的收入数据
    private void loadDoubleLineChartData(LineChart chart, Context context) {
        int[] mDataYs1 = new int[]{90, 70, 50, 60};
        int[] mDataYs2 = new int[]{70, 50, 40, 40};

        ArrayList<LineDataSet> allLinesList = new ArrayList<LineDataSet>();

        ArrayList<Entry> entryList1 = new ArrayList<Entry>();
        ArrayList<Entry> entryList2 = new ArrayList<Entry>();

        for (int i = 0; i < mDataYs1.length; i++) {
            //Entry(yValue,xIndex);一个Entry表示一个点，第一个参数为y值，第二个为X轴List的角标
            entryList1.add(new Entry(mDataYs1[i], i));
            entryList2.add(new Entry(mDataYs2[i], i));
        }
        //LineDataSet可以看做是一条线
        LineDataSet dataSet1 = new LineDataSet(entryList1, "dataLine1");
        LineDataSet dataSet2 = new LineDataSet(entryList2, "dataLine2");

//        dataSet1.setColor(Color.RED);
//        dataSet2.setColor(Color.GREEN);

        dataSet1.setColors(new int[]{R.color.red1, R.color.red2, R.color.red3, R.color.red4}, context);
        dataSet2.setColors(new int[]{R.color.green1, R.color.green2, R.color.green3, R.color.green4}, context);

        dataSet1.setLineWidth(2.5f);
        dataSet1.setCircleSize(4.5f);
        dataSet1.setDrawValues(false); // 是否在点上绘制Value
        allLinesList.add(dataSet1);

        dataSet2.setLineWidth(2.5f);
        dataSet2.setCircleSize(4.5f);
        dataSet2.setDrawValues(false); // 是否在点上绘制Value
        allLinesList.add(dataSet2);

        List<String> quarterStrs = new ArrayList<String>();
        quarterStrs.add("第一季度");
        quarterStrs.add("第二季度");
        quarterStrs.add("第三季度");
        quarterStrs.add("第四季度");
        //LineData表示一个LineChart的所有数据(即一个LineChart中所有折线的数据)
        LineData mChartData = new LineData(quarterStrs, allLinesList);

        // set data
        chart.setData((LineData) mChartData);
        chart.animateX(1500);//设置动画
    }


    /**
     * 设置折现图的样式
     *
     * @param chart
     */
    private void setLineChart(LineChart chart) {

//        chart.setBackgroundColor(0xff00ff00);

//        chart.setDescription("zhuanghongji");
//        chart.setDescriptionColor(Color.RED);
//
//        chart.setDescriptionPosition(150f,150f);
//        chart.setDescriptionTextSize(16f);

//        chart.setNoDataTextDescription("Data 为空！");

        // 设置手势
        chart.setOnChartGestureListener(this);
//        chart.setScaleEnabled(true);
//        chart.setTouchEnabled(true);
//        chart.setScaleXEnabled(true);
//        chart.setScaleYEnabled(true);
//        chart.setPinchZoom(true);
//        chart.setDragEnabled(true);

        chart.setDrawGridBackground(false); // 设置网格背景
//        chart.setGridBackgroundColor(Color.BLUE);

//        chart.setDrawBorders(true);
//        chart.setBorderColor(Color.YELLOW);
//        chart.setBorderWidth(4f);

        chart.setTouchEnabled(true);
        chart.setDoubleTapToZoomEnabled(true);

        chart.setScaleEnabled(false); // 设置缩放
        chart.setDoubleTapToZoomEnabled(false); // 设置双击不进行缩放

        chart.setAutoScaleMinMaxEnabled(false);

        // 设置X轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
//        xAxis.setTypeface(mTf); // 设置字体
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setDrawLabels(false);

//        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());


        // Styling / modifying the axis
//        xAxis.setTextColor(Color.BLUE);
//        xAxis.setTextSize(24f);
//        xAxis.setGridLineWidth(10f);
//        xAxis.setGridColor(Color.RED);
//        xAxis.setAxisLineColor(Color.GREEN);
//        xAxis.setAxisLineWidth(5f);
//        xAxis.enableGridDashedLine(4,4,1);

        // 设置x轴的LimitLine，index是从0开始的
//        LimitLine xLimitLine = new LimitLine(4f,"xL 测试");
//        xLimitLine.setLineColor(Color.GREEN);
//        xLimitLine.setTextColor(Color.GREEN);
//        xAxis.addLimitLine(xLimitLine);
//        xAxis.setDrawLimitLinesBehindData(true);

        xAxis.setSpaceBetweenLabels(1);
//        xAxis.setLabelsToSkip(4);
//        xAxis.resetLabelsToSkip();
//        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setPosition(XAxis.XAxisPosition.TOP);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        // 图例
        Legend legend = chart.getLegend();
//        legend.setEnabled(false);
//        legend.setTextColor(Color.GREEN);
        legend.setTextSize(10f);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
//        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        legend.setFormSize(18f);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setForm(Legend.LegendForm.LINE);

        // 设置x轴的LimitLine
//        LimitLine yLimitLine = new LimitLine(50f,"yLimit 测试");
//        yLimitLine.setLineColor(Color.RED);
//        yLimitLine.setTextColor(Color.RED);

        // 获得左侧侧坐标轴
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setTextSize(18f);
//        leftAxis.setTextColor(Color.GREEN);

        // 出现错误，leftAxis的setValueFormatter参数类型只能是 YAxisFormatter
//        leftAxis.setValueFormatter(new MyValueFormatter());


//        leftAxis.addLimitLine(yLimitLine);
//        leftAxis.setDrawLimitLinesBehindData(false);
//        leftAxis.setStartAtZero(false);
//        leftAxis.setAxisMinValue(30);
//        leftAxis.setAxisMaxValue(60);

//        leftAxis.setInverted(true);

//        leftAxis.setTypeface(mTf);
//        leftAxis.setAxisLineWidth(1.5f);

//        leftAxis.setLabelCount(5, false);
//        leftAxis.setShowOnlyMinMax(true);

//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        //设置右侧坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false); // 右侧坐标轴线
        rightAxis.setDrawLabels(false); // 右侧坐标轴数组Label
//        rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5);
//        rightAxis.setDrawGridLines(false);



    }

    private ArrayList<String> getXAxisShowLable() {
        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");
        return m;
    }

    /**
     * 饼形图的划分
     *
     * @return
     */
    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("1st Quarter");
        q.add("2nd Quarter");
        q.add("3rd Quarter");
        q.add("4th Quarter");

        return q;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(TAG, "onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(TAG, "onChartGestureEnd");
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.d(TAG, "onChartLongPressed");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.d(TAG, "onChartDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.d(TAG, "onChartSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.d(TAG, "onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.d(TAG, "onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.d(TAG, "onChartTranslate");
    }
}
