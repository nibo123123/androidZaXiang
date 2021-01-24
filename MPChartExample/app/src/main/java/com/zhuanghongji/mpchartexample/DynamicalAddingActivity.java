
package com.zhuanghongji.mpchartexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhuanghongji.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;

import butterknife.BindView;

public class DynamicalAddingActivity extends DemoBase implements OnChartValueSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.chart1)
    LineChart mChart;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        // add an empty data object
        mChart.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_linechart_noseekbar;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.ci_23_name,R.string.ci_23_desc,R.menu.dynamical,true);
    }

    @Override
    protected void initEvents() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionAddEntry:
                        addEntry();
                        Toast.makeText(DynamicalAddingActivity.this, "Entry added!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.actionRemoveEntry:
                        removeLastEntry();
                        Toast.makeText(DynamicalAddingActivity.this, "Entry removed!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.actionAddDataSet:
                        addDataSet();
                        Toast.makeText(DynamicalAddingActivity.this, "DataSet added!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.actionRemoveDataSet:
                        removeDataSet();
                        Toast.makeText(DynamicalAddingActivity.this, "DataSet removed!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.actionAddEmptyLineData:
                        mChart.setData(new LineData());
                        mChart.invalidate();
                        Toast.makeText(DynamicalAddingActivity.this, "Empty data added!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.actionClear:
                        mChart.clear();
                        Toast.makeText(DynamicalAddingActivity.this, "Chart cleared!", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });
    }

    int[] mColors = ColorTemplate.VORDIPLOM_COLORS;

    private void addEntry() {
        LineData data = mChart.getData();
        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well
        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        float yValue = (float) (Math.random() * 10) + 50f;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();

        mChart.setVisibleXRangeMaximum(6);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//            
//            // this automatically refreshes the chart (calls invalidate())
        mChart.moveViewTo(data.getEntryCount() - 7, 50f, AxisDependency.LEFT);

    }

    private void removeLastEntry() {
        LineData data = mChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set != null) {
//                Entry e = set.getEntryForXValue(set.getEntryCount() - 1); // TODO
                Entry e = set.getEntryForXValue(set.getEntryCount() - 1, 1f);

                data.removeEntry(e, 0);
                // or remove by index
                // mData.removeEntryByXValue(xIndex, dataSetIndex);
                data.notifyDataChanged();
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            }
        }
    }

    private void addDataSet() {
        LineData data = mChart.getData();
        if (data != null) {
            int count = (data.getDataSetCount() + 1);
            ArrayList<Entry> yVals = new ArrayList<>();
            for (int i = 0; i < data.getEntryCount(); i++) {
                yVals.add(new Entry(i, (float) (Math.random() * 50f) + 50f * count));
            }

            LineDataSet set = new LineDataSet(yVals, "DataSet " + count);
            set.setLineWidth(2.5f);
            set.setCircleRadius(4.5f);

            int color = mColors[count % mColors.length];

            set.setColor(color);
            set.setCircleColor(color);
            set.setHighLightColor(color);
            set.setValueTextSize(10f);
            set.setValueTextColor(color);

            data.addDataSet(set);
            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    private void removeDataSet() {
        LineData data = mChart.getData();
        if (data != null) {
            data.removeDataSet(data.getDataSetByIndex(data.getDataSetCount() - 1));
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
}
