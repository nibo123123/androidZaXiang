
package com.zhuanghongji.mpchartexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.zhuanghongji.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CubicLineChartActivity extends DemoBase implements OnSeekBarChangeListener {

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

        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);
        
        XAxis x = mChart.getXAxis();
        x.setEnabled(false);
        
        YAxis y = mChart.getAxisLeft();
        y.setTypeface(mTfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);
        
        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(45, 100);
        
        mChart.getLegend().setEnabled(false);
        
        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_linechart;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.ci_19_name,R.string.ci_19_desc,R.menu.line,true);
    }

    @Override
    protected void initEvents() {
        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionToggleValues: {
                        for (IDataSet set : mChart.getData().getDataSets())
                            set.setDrawValues(!set.isDrawValuesEnabled());

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
                    case R.id.actionToggleCubic: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                                    ? LineDataSet.Mode.LINEAR
                                    :  LineDataSet.Mode.CUBIC_BEZIER);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case R.id.actionToggleStepped: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                                    ? LineDataSet.Mode.LINEAR
                                    :  LineDataSet.Mode.STEPPED);
                        }
                        mChart.invalidate();
                        break;
                    }
                    case R.id.actionToggleHorizontalCubic: {
                        List<ILineDataSet> sets = mChart.getData()
                                .getDataSets();

                        for (ILineDataSet iSet : sets) {

                            LineDataSet set = (LineDataSet) iSet;
                            set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                                    ? LineDataSet.Mode.LINEAR
                                    :  LineDataSet.Mode.HORIZONTAL_BEZIER);
                        }
                        mChart.invalidate();
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
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
                                                           // ((mult *
                                                           // 0.1) / 10);
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }
}
