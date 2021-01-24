package com.zhuanghongji.mpchartexample.realm;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.realm.implementation.RealmCandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.custom.RealmDemoData;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityCandle extends RealmBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.chart1)
    CandleStickChart mChart;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup(mChart);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_candlechart_noseekbar;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.realm_ci_4_name,R.string.realm_ci_4_desc,0,true);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm
        // write some demo-data into the realm.io database
        writeToDBCandle(50);

        // add data to the chart
        setData();
    }

    private void setData() {
        RealmResults<RealmDemoData> result = mRealm.where(RealmDemoData.class).findAll();

        RealmCandleDataSet<RealmDemoData> set = new RealmCandleDataSet<RealmDemoData>(result, "xValue", "high", "low", "open", "close");
        set.setLabel("Realm CandleDataSet");
        set.setShadowColor(Color.DKGRAY);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.RED);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.rgb(122, 242, 84));
        set.setIncreasingPaintStyle(Paint.Style.STROKE);
        set.setNeutralColor(Color.BLUE);

        ArrayList<ICandleDataSet> dataSets = new ArrayList<ICandleDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        CandleData data = new CandleData(dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }

}
