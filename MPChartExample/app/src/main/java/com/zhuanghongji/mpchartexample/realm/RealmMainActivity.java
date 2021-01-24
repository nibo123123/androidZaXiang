package com.zhuanghongji.mpchartexample.realm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.notimportant.ContentItem;
import com.zhuanghongji.mpchartexample.notimportant.DemoBase;
import com.zhuanghongji.mpchartexample.notimportant.MyAdapter;

import java.util.ArrayList;

import butterknife.BindFloat;
import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Philipp Jahoda on 07/12/15.
 */
public class RealmMainActivity extends DemoBase {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.listView1)
    ListView lv;

    private Context mContext;

    @SuppressWarnings("ButterKnifeInjectNotCalled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        ArrayList<ContentItem> objects = new ArrayList<>();
        objects.add(new ContentItem(
                getString(R.string.realm_ci_0_name),
                getString(R.string.realm_ci_0_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_1_name),
                getString(R.string.realm_ci_1_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_2_name),
                getString(R.string.realm_ci_2_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_3_name),
                getString(R.string.realm_ci_3_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_4_name),
                getString(R.string.realm_ci_4_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_5_name),
                getString(R.string.realm_ci_5_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_6_name),
                getString(R.string.realm_ci_6_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_7_name),
                getString(R.string.realm_ci_7_desc)));
        objects.add(new ContentItem(
                getString(R.string.realm_ci_8_name),
                getString(R.string.realm_ci_8_desc)));
        MyAdapter adapter = new MyAdapter(this, objects);
        lv.setAdapter(adapter);
        View footer = LayoutInflater.from(this).inflate(R.layout.tip_end,null);
        lv.addFooterView(footer);

        Realm.init(this);
        // Create a RealmConfiguration that saves the Realm file in the app's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setupToolbar(mToolbar,R.string.ci_28_name,R.string.ci_28_desc,R.menu.realm,true);
    }

    @Override
    protected void initEvents() {
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://realm.io"));
                startActivity(i);

                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i;
                switch (pos) {
                    case 0:
                        i = new Intent(mContext, RealmDatabaseActivityLine.class);
                        break;
                    case 1:
                        i = new Intent(mContext, RealmDatabaseActivityBar.class);
                        break;
                    case 2:
                        i = new Intent(mContext, RealmDatabaseActivityHorizontalBar.class);
                        break;
                    case 3:
                        i = new Intent(mContext, RealmDatabaseActivityScatter.class);
                        break;
                    case 4:
                        i = new Intent(mContext, RealmDatabaseActivityCandle.class);
                        break;
                    case 5:
                        i = new Intent(mContext, RealmDatabaseActivityBubble.class);
                        break;
                    case 6:
                        i = new Intent(mContext, RealmDatabaseActivityPie.class);
                        break;
                    case 7:
                        i = new Intent(mContext, RealmDatabaseActivityRadar.class);
                        break;
                    case 8:
                        i = new Intent(mContext, RealmWikiExample.class);
                        break;
                    default:
                        i = null;
                        break;
                }
                if (i != null){
                    startActivity(i);
                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                }
            }
        });
    }

}
