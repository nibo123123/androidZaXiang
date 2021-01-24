package com.zhuanghongji.mpchartexample.notimportant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;
import com.zhuanghongji.mpchartexample.AboutActivity;
import com.zhuanghongji.mpchartexample.AnotherBarActivity;
import com.zhuanghongji.mpchartexample.BarChartActivity;
import com.zhuanghongji.mpchartexample.BarChartActivityMultiDataset;
import com.zhuanghongji.mpchartexample.BarChartActivitySinus;
import com.zhuanghongji.mpchartexample.BarChartPositiveNegative;
import com.zhuanghongji.mpchartexample.BubbleChartActivity;
import com.zhuanghongji.mpchartexample.CandleStickChartActivity;
import com.zhuanghongji.mpchartexample.CombinedChartActivity;
import com.zhuanghongji.mpchartexample.CubicLineChartActivity;
import com.zhuanghongji.mpchartexample.DynamicalAddingActivity;
import com.zhuanghongji.mpchartexample.FilledLineActivity;
import com.zhuanghongji.mpchartexample.HalfPieChartActivity;
import com.zhuanghongji.mpchartexample.HorizontalBarChartActivity;
import com.zhuanghongji.mpchartexample.InvertedLineChartActivity;
import com.zhuanghongji.mpchartexample.LineChartActivity1;
import com.zhuanghongji.mpchartexample.LineChartActivity2;
import com.zhuanghongji.mpchartexample.LineChartActivityColored;
import com.zhuanghongji.mpchartexample.LineChartTime;
import com.zhuanghongji.mpchartexample.ListViewBarChartActivity;
import com.zhuanghongji.mpchartexample.ListViewMultiChartActivity;
import com.zhuanghongji.mpchartexample.MultiLineChartActivity;
import com.zhuanghongji.mpchartexample.PerformanceLineChart;
import com.zhuanghongji.mpchartexample.PieChartActivity;
import com.zhuanghongji.mpchartexample.PiePolylineChartActivity;
import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.RadarChartActivitry;
import com.zhuanghongji.mpchartexample.RealtimeLineChartActivity;
import com.zhuanghongji.mpchartexample.ScatterChartActivity;
import com.zhuanghongji.mpchartexample.ScrollViewActivity;
import com.zhuanghongji.mpchartexample.StackedBarActivity;
import com.zhuanghongji.mpchartexample.StackedBarActivityNegative;
import com.zhuanghongji.mpchartexample.fragments.SimpleChartDemo;
import com.zhuanghongji.mpchartexample.realm.RealmMainActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.listView1)
    ListView lv;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // initialize the utilities
        Utils.init(this);

        ArrayList<ContentItem> objects = getContentItems();
        MyAdapter adapter = new MyAdapter(this, objects);

        lv.setAdapter(adapter);
        View footer = LayoutInflater.from(this).inflate(R.layout.tip_end,null);
        lv.addFooterView(footer);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i;
                switch (pos) {
                    case 0:
                        i = new Intent(mContext, LineChartActivity1.class);
                        break;
                    case 1:
                        i = new Intent(mContext, LineChartActivity2.class);
                        break;
                    case 2:
                        i = new Intent(mContext, BarChartActivity.class);
                        break;
                    case 3:
                        i = new Intent(mContext, HorizontalBarChartActivity.class);
                        break;
                    case 4:
                        i = new Intent(mContext, CombinedChartActivity.class);
                        break;
                    case 5:
                        i = new Intent(mContext, PieChartActivity.class);
                        break;
                    case 6:
                        i = new Intent(mContext, PiePolylineChartActivity.class);
                        break;
                    case 7:
                        i = new Intent(mContext, ScatterChartActivity.class);
                        break;
                    case 8:
                        i = new Intent(mContext, BubbleChartActivity.class);
                        break;
                    case 9:
                        i = new Intent(mContext, StackedBarActivity.class);
                        break;
                    case 10:
                        i = new Intent(mContext, StackedBarActivityNegative.class);
                        break;
                    case 11:
                        i = new Intent(mContext, AnotherBarActivity.class);
                        break;
                    case 12:
                        i = new Intent(mContext, MultiLineChartActivity.class);
                        break;
                    case 13:
                        i = new Intent(mContext, BarChartActivityMultiDataset.class);
                        break;
                    case 14:
                        i = new Intent(mContext, SimpleChartDemo.class);
                        break;
                    case 15:
                        i = new Intent(mContext, ListViewBarChartActivity.class);
                        break;
                    case 16:
                        i = new Intent(mContext, ListViewMultiChartActivity.class);
                        break;
                    case 17:
                        i = new Intent(mContext, InvertedLineChartActivity.class);
                        break;
                    case 18:
                        i = new Intent(mContext, CandleStickChartActivity.class);
                        break;
                    case 19:
                        i = new Intent(mContext, CubicLineChartActivity.class);
                        break;
                    case 20:
                        i = new Intent(mContext, RadarChartActivitry.class);
                        break;
                    case 21:
                        i = new Intent(mContext, LineChartActivityColored.class);
                        break;
                    case 22:
                        i = new Intent(mContext, RealtimeLineChartActivity.class);
                        break;
                    case 23:
                        i = new Intent(mContext, DynamicalAddingActivity.class);
                        break;
                    case 24:
                        i = new Intent(mContext, PerformanceLineChart.class);
                        break;
                    case 25:
                        i = new Intent(mContext, BarChartActivitySinus.class);
                        break;
                    case 26:
                        i = new Intent(mContext, ScrollViewActivity.class);
                        break;
                    case 27:
                        i = new Intent(mContext, BarChartPositiveNegative.class);
                        break;
                    case 28:
                        i = new Intent(mContext, RealmMainActivity.class);
                        break;
                    case 29:
                        i = new Intent(mContext, LineChartTime.class);
                        break;
                    case 30:
                        i = new Intent(mContext, FilledLineActivity.class);
                        break;
                    case 31:
                        i = new Intent(mContext, HalfPieChartActivity.class);
                        break;
                    case 32:
                        i = new Intent(mContext, AboutActivity.class);
                        break;
                    default:
                        i = null;
                        break;

                }
                if (i != null) {
                    startActivity(i);
                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                }
            }
        });
    }

    @NonNull
    private ArrayList<ContentItem> getContentItems() {
        ArrayList<ContentItem> objects = new ArrayList<>();
        objects.add(new ContentItem(
                getString(R.string.ci_0_name),
                getString(R.string.ci_0_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_1_name),
                getString(R.string.ci_1_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_2_name),
                getString(R.string.ci_2_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_3_name),
                getString(R.string.ci_3_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_4_name),
                getString(R.string.ci_4_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_5_name),
                getString(R.string.ci_5_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_6_name),
                getString(R.string.ci_6_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_7_name),
                getString(R.string.ci_7_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_8_name),
                getString(R.string.ci_8_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_9_name),
                getString(R.string.ci_9_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_10_name),
                getString(R.string.ci_10_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_11_name),
                getString(R.string.ci_11_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_12_name),
                getString(R.string.ci_12_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_13_name),
                getString(R.string.ci_13_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_14_name),
                getString(R.string.ci_14_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_15_name),
                getString(R.string.ci_15_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_16_name),
                getString(R.string.ci_16_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_17_name),
                getString(R.string.ci_17_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_18_name),
                getString(R.string.ci_18_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_19_name),
                getString(R.string.ci_19_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_20_name),
                getString(R.string.ci_20_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_21_name),
                getString(R.string.ci_21_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_22_name),
                getString(R.string.ci_22_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_23_name),
                getString(R.string.ci_23_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_24_name),
                getString(R.string.ci_24_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_25_name),
                getString(R.string.ci_25_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_26_name),
                getString(R.string.ci_26_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_27_name),
                getString(R.string.ci_27_desc)));

        ContentItem realm = new ContentItem(
                getString(R.string.ci_28_name),
                getString(R.string.ci_28_desc));
        objects.add(realm);

        ContentItem time = new ContentItem(
                getString(R.string.ci_29_name),
                getString(R.string.ci_29_desc));
        time.isNew = true;
        objects.add(time);
        objects.add(new ContentItem(
                getString(R.string.ci_30_name),
                getString(R.string.ci_30_desc)));
        objects.add(new ContentItem(
                getString(R.string.ci_31_name),
                getString(R.string.ci_31_desc)));

        // 关于
        objects.add(new ContentItem(
                getString(R.string.about_name),
                getString(R.string.about_desc)));
        return objects;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("MPAndroidChart Example");
        mToolbar.inflateMenu(R.menu.main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.viewGithub:
                        i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://github.com/zhuanghongji/MPAndroidChart"));
                        startActivity(i);
                        break;
                    case R.id.report:
                        i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "377232403@qq.com", null));
                        i.putExtra(Intent.EXTRA_SUBJECT, "MPAndroidChart Issue");
                        i.putExtra(Intent.EXTRA_TEXT, "Your error report here...");
                        startActivity(Intent.createChooser(i, "Report Problem"));
                        break;
                    case R.id.blog:
                        i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("http://www.zhuanghongji.com"));
                        startActivity(i);
                        break;
                    case R.id.website:
                        i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("http://www.linkedin.com/in/zhuanghongji"));
                        startActivity(i);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void initEvents() {

    }

}
