
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhuanghongji.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MultiLineChartActivity extends DemoBase implements OnSeekBarChangeListener,
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

        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawBorders(false);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mSeekBarX.setProgress(20);
        mSeekBarY.setProgress(100);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_linechart;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.ci_12_name,R.string.ci_12_desc,R.menu.line,true);
    }

    @Override
    protected void initEvents() {
        mSeekBarX.setOnSeekBarChangeListener(this);
        mSeekBarY.setOnSeekBarChangeListener(this);
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
                    case R.id.actionSave: {
                        // mChart.saveToGallery("title"+System.currentTimeMillis());
                        mChart.saveToPath("title" + System.currentTimeMillis(), "");
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
                }
                return true;
            }
        });
    }

    private int[] mColors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0], 
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        
        mChart.resetTracking();

        tvX.setText("" + (mSeekBarX.getProgress()));
        tvY.setText("" + (mSeekBarY.getProgress()));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < mSeekBarX.getProgress(); i++) {
                double val = (Math.random() * mSeekBarY.getProgress()) + 3;
                values.add(new Entry(i, (float) val));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);

            int color = mColors[z % mColors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        // make the first DataSet dashed
        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        LineData data = new LineData(dataSets);
        mChart.setData(data);
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
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
