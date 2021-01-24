
package com.zhuanghongji.mpchartexample;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.zhuanghongji.mpchartexample.custom.MyMarkerView;
import com.zhuanghongji.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class InvertedLineChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.chart1)
    LineChart mChart;

    @BindView(R.id.seekBar1)
    SeekBar mSeekBarX;

    @BindView(R.id.seekBar2)
    SeekBar mSeekBarY;

    @BindView(R.id.tvXMax)
    TextView tvX;

    @BindView(R.id.tvYMax)
    TextView tvY;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSeekBarX.setProgress(45);
        mSeekBarY.setProgress(100);

        mChart.setDrawGridBackground(false);
        
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        
        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);
        
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        // add data
        setData(25, 50);

        // // restrain the maximum scale-out factor
        // mChart.setScaleMinima(3f, 3f);
        //
        // // center the view to a specific position inside the chart
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.LINE);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_linechart;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.ci_17_name,R.string.ci_17_desc,R.menu.line,true);
    }

    @Override
    protected void initEvents() {
        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);
        mChart.setOnChartValueSelectedListener(this);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionToggleValues: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            set.setDrawValues(!set.isDrawValuesEnabled());
                        }

                        mChart.invalidate();
                        break;
                    }
                    case R.id.actionToggleHighlight: {
                        if(mChart.getData() != null) {
                            mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                            mChart.invalidate();
                        }
                        break;
                    }
                    case R.id.actionToggleFilled: {

                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            if (set.isDrawFilledEnabled())
                                set.setDrawFilled(false);
                            else
                                set.setDrawFilled(true);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case R.id.actionToggleCircles: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            if (set.isDrawCirclesEnabled())
                                set.setDrawCircles(false);
                            else
                                set.setDrawCircles(true);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case R.id.animateX: {
                        mChart.animateX(3000);
                        break;
                    }
                    case R.id.animateY: {
                        mChart.animateY(3000);
                        break;
                    }
                    case R.id.animateXY: {

                        mChart.animateXY(3000, 3000);
                        break;
                    }
                    case R.id.actionTogglePinch: {
                        if (mChart.isPinchZoomEnabled())
                            mChart.setPinchZoom(false);
                        else
                            mChart.setPinchZoom(true);

                        mChart.invalidate();
                        break;
                    }
                    case R.id.actionToggleAutoScaleMinMax: {
                        mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                        mChart.notifyDataSetChanged();
                        break;
                    }
                    case R.id.actionSave: {
                        if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                            Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                                    Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                                    .show();

                        // mChart.saveToGallery("title"+System.currentTimeMillis())
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());

        // redraw
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float xVal = (float) (Math.random() * range);
            float yVal = (float) (Math.random() * range);
            entries.add(new Entry(xVal, yVal));
        }

        // sort by x-value
        Collections.sort(entries, new EntryXComparator());

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries, "DataSet 1");

        set1.setLineWidth(1.5f);
        set1.setCircleRadius(4f);

        // create a data object with the datasets
        LineData data = new LineData(set1);

        // set data
        mChart.setData(data);
    }
}
