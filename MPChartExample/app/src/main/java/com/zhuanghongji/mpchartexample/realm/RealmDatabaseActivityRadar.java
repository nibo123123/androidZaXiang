package com.zhuanghongji.mpchartexample.realm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.realm.implementation.RealmRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.custom.RealmDemoData;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityRadar extends RealmBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.chart1)
    RadarChart mChart;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup(mChart);

        mChart.getYAxis().setEnabled(false);
        mChart.getXAxis().setEnabled(false);
        mChart.setWebAlpha(180);
        mChart.setWebColorInner(Color.DKGRAY);
        mChart.setWebColor(Color.GRAY);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_radarchart_noseekbar;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.realm_ci_7_name,R.string.realm_ci_7_desc,0,true);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm
        // write some demo-data into the realm.io database
        writeToDB(7);

        // add data to the chart
        setData();
    }

    private void setData() {
        RealmResults<RealmDemoData> result = mRealm.where(RealmDemoData.class).findAll();

        //RealmBarDataSet<RealmDemoData> set = new RealmBarDataSet<RealmDemoData>(result, "stackValues", "xIndex"); // normal entries
        RealmRadarDataSet<RealmDemoData> set = new RealmRadarDataSet<RealmDemoData>(result, "yValue"); // stacked entries
        set.setLabel("Realm RadarDataSet");
        set.setDrawFilled(true);
        set.setColor(ColorTemplate.rgb("#009688"));
        set.setFillColor(ColorTemplate.rgb("#009688"));
        set.setFillAlpha(130);
        set.setLineWidth(2f);

        ArrayList<IRadarDataSet> dataSets = new ArrayList<IRadarDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        RadarData data = new RadarData(dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400);
    }

}
