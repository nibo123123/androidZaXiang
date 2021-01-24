package com.zhuanghongji.mpchartexample.realm;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.custom.RealmDemoData;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityBar extends RealmBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.chart1)
    BarChart mChart;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup(mChart);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_barchart_noseekbar;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.realm_ci_1_name,R.string.realm_ci_1_desc,0,true);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm
        // write some demo-data into the realm.io database
        writeToDB(20);

        // add data to the chart
        setData();
    }

    private void setData() {
        RealmResults<RealmDemoData> result = mRealm.where(RealmDemoData.class).findAll();

        //RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "stackValues", "xIndex"); // normal entries
        RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "xValue", "yValue"); // stacked entries
        set.setColors(new int[] {ColorTemplate.rgb("#FF5722"), ColorTemplate.rgb("#03A9F4")});
        set.setLabel("Realm BarDataSet");

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        BarData data = new BarData(dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }

}
