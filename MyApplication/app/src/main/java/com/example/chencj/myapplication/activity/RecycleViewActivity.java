package com.example.chencj.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chencj.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHENCJ on 2020/11/13.
 * 导入recycleview的包
 * compile 'com.android.support:recyclerview-v7:26.0.0'
 *
 *
 */

public class RecycleViewActivity extends Activity implements OnItemClickListener{
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);

        initData();
    }

    private void initData() {
        //2、设置布局管理器
//设置纵向默认排列
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
////设置横向排列
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
////设置表格布局排列
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
////设置瀑布流显示
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //3、设置分割线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //4、设置删除时动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // 5、关联适配器
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getData(), this);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        //需要自定义实现item的点击事件，item在adapter需要自定义实现
        recyclerViewAdapter.setOnItemClickListener(this);
    }

    private List<String> getData() {
        int size = 20;
        ArrayList<String> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(i,"recycleview item "+i);
        }
        return result;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("RecycleViewActivity chencj ", "onItemClick: "+view+",position="+position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d("RecycleViewActivity chencj ", "onItemLongClick: "+view+",position="+position);
    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{


        private List<String> mList;
        private Context mContext;
        private OnItemClickListener mOnItemClickListener;


        public RecyclerViewAdapter(List<String> mList, Context mContext) {
            //大部分只有数据与上下文
            this.mList = mList;
            this.mContext = mContext;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            /**
             * 主要负责把加载子项的布局
             * 将xml定义的布局实例化为view对象
             */
            View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, viewGroup, false);
            //adapter中实现item的点击事件，
            view.setOnClickListener(this);
            return new ContentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            //主要负责绑定数据
            contentViewHolder.tvItem.setText(mList.get(position));
            //holder.itemView就是在onCreateViewHolder，初始化的view
            holder.itemView.setTag(position);
        }


        @Override
        public int getItemCount() {
            //返回集合的数量
            return mList.size();
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        //通过view的onclick去绑定自定义的item点击事件
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null) {
                mOnItemClickListener.onItemClick(v, (int)v.getTag());
            }
            if(mOnItemClickListener!=null) {
                mOnItemClickListener.onItemLongClick(v, (int)v.getTag());
            }
        }

        class ContentViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvItem;
            private final LinearLayout llLayout;

            public ContentViewHolder(View itemView) {
                super(itemView);
                tvItem = (TextView) itemView.findViewById(R.id.tv_item);
                llLayout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
            }
        }
    }



}
interface OnItemClickListener{
    //点击事件
    void onItemClick(View view, int position);

    //长按点击事件
    void onItemLongClick(View view, int position);
}