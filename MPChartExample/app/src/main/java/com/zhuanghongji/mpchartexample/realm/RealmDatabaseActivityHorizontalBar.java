package com.zhuanghongji.mpchartexample.realm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
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
public class RealmDatabaseActivityHorizontalBar extends RealmBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.chart1)
    HorizontalBarChart mChart;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup(mChart);

        mChart.getAxisLeft().setAxisMinimum(0f);
        mChart.setDrawValueAboveBar(false);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_horizontalbarchart_noseekbar;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.realm_ci_2_name,R.string.realm_ci_2_desc,0,true);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        // write some demo-data into the realm.io database
        writeToDBStack(50);

        // add data to the chart
        setData();
    }

    private void setData() {
        RealmResults<RealmDemoData> result = mRealm.where(RealmDemoData.class).findAll();

        //RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "stackValues", "xIndex"); // normal entries
        RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "xValue", "stackValues", "floatValue"); // stacked entries
        set.setColors(new int[]{ColorTemplate.rgb("#8BC34A"), ColorTemplate.rgb("#FFC107"), ColorTemplate.rgb("#9E9E9E")});
        set.setLabel("Mobile OS distribution");
        set.setStackLabels(new String[]{"iOS", "Android", "Other"});

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        BarData data = new BarData(dataSets);
        styleData(data);
        data.setValueTextColor(Color.WHITE);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }

}
