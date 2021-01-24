package com.zhuanghongji.mpandroidchart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DynamicalAddingActivity extends AppCompatActivity  {

    private LineChart mChart;
    private int[] mColors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.CYAN, Color.GRAY};

    private static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamical_adding);

        mChart = (LineChart) findViewById(R.id.line_chart);
        setLineChart(mChart);
    }

    private void setLineChart(LineChart chart) {
        chart.setDrawGridBackground(false);
        chart.setDescription("");
        chart.setVisibleXRangeMaximum(9);
        chart.setVisibleYRangeMaximum(150, YAxis.AxisDependency.LEFT);

        // 为chart添加空数据
        chart.setData(new LineData());

        // 设置x轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(4);

        // 设置左侧坐标轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        // 设置右侧坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    /**
     * 为最后一个DataSet添加一个y值随机的Entry
     */
    public void addEntry(View view) {
        LineData lineData = mChart.getData();

        if (lineData != null) {
            int indexLast = getLastDataSetIndex(lineData);
            LineDataSet lastSet = lineData.getDataSetByIndex(indexLast);
            // set.addEntry(...); // can be called as well

            if (lastSet == null) {
                lastSet = createLineDataSet();
                lineData.addDataSet(lastSet);
            }
            // 这里要注意，x轴的index是从零开始的
            // 假设index=2，那么getEntryCount()就等于3了
            int count = lastSet.getEntryCount();
            // add a new x-value first 这行代码不能少
            lineData.addXValue(count + "");

            float yValues = (float) (Math.random() * 100);
            // 位最后一个DataSet添加entry
            lineData.addEntry(new Entry(yValues, count), indexLast);

            mChart.notifyDataSetChanged();
            mChart.moveViewTo(yValues, count, YAxis.AxisDependency.LEFT);

            Log.d(TAG, "set.getEntryCount()=" + lastSet.getEntryCount()
                    + " ; indexLastDataSet=" + indexLast);

        }
    }

    /**
     * 获取最后一个LineDataSet的索引
     */
    private int getLastDataSetIndex(LineData lineData) {
        int dataSetCount = lineData.getDataSetCount();
        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
    }

    /**
     * 移除最后一个DataSet的最后一个Entry
     */
    public void removeLastEntry(View view) {
        LineData lineData = mChart.getLineData();
        if (lineData != null) {
            int indexLastDataSet = lineData.getDataSetCount() - 1;
            LineDataSet lastDataSet = lineData.getDataSetByIndex(indexLastDataSet);
            if (lastDataSet != null) {
                Entry lastEntry = lastDataSet.getEntryForXIndex(lastDataSet.getEntryCount() - 1);
                lineData.removeEntry(lastEntry, indexLastDataSet);
                // or remove by index
                // mData.removeEntry(xIndex, dataSetIndex);

                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        }
    }

    /**
     * 移除最后一个DataSet
     */
    public void removeLastDataSet(View view) {
        LineData lineData = mChart.getData();
        if (lineData != null) {
            lineData.removeDataSet(lineData.getDataSetCount() - 1);
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }
    /**
     * 添加一个DataSet
     */
    public void addDataSet(View view) {
        LineData lineData = mChart.getLineData();
        if (lineData != null) {
            int count = (lineData.getDataSetCount() + 1);
            // create 10 y-value
            ArrayList<Entry> yValueList = new ArrayList<>();
            if (lineData.getXValCount() == 0) {
                for (int i = 0; i < 10; i++) {
                    lineData.addXValue((i + 1) + "");
                }
            }

            for (int i = 0; i < lineData.getXValCount(); i++) {
                yValueList.add(new Entry((float) (Math.random() * 100f), i));
            }

            LineDataSet set = new LineDataSet(yValueList, "DataSet " + count);
            // 求余，防止数组越界异常
            int colorIndex = count % mColors.length;
            set.setColor(mColors[colorIndex]);
            set.setCircleColor(mColors[colorIndex]);
            set.setValueTextColor(mColors[colorIndex]);

            lineData.addDataSet(set);
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    public void setEmptyLineData(View view) {
        mChart.setData(new LineData());
        mChart.invalidate();
    }

    public void clearChart(View view) {
        mChart.clear();
    }
    private LineDataSet createLineDataSet() {
        LineDataSet dataSet = new LineDataSet(null, "DataSet 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setValueTextSize(12f);

        return dataSet;
    }

}
