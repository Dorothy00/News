package com.dorothy.hacknews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {
    private ListView mListView;
    private NewsAdapter mAdapter;
    private List<News> mNewsList = new ArrayList<>();
    private FlowLayout mFlowLayout;
    private int type;
    private ArrayList<String> labels = new ArrayList<>();

    private int[] colors = {R.drawable.btn_round, R.drawable.btn_round2, R.drawable.btn_round3, R.drawable.btn_round4, R.drawable.btn_round5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        // todo
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        type = getIntent().getIntExtra("type", 0);
        //labels = getIntent().getStringArrayListExtra("labels");
        // mock
        labels.add("label1");
        labels.add("labl1");
        labels.add("label1");
        labels.add("lablel1");
        labels.add("label1");
        labels.add("labellll1");
        labels.add("lal1");
        labels.add("label1");
        labels.add("labelppp1");
        renderFlow();

        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, mNewsList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsListActivity.this, WebViewActivity.class);
                News news = mNewsList.get(position);
                intent.putExtra("url", news.getUrl());
                startActivity(intent);
            }
        });

        fetchData();
    }

    private void renderFlow() {
        for (int i = 0; i < labels.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.btn_flow, null);
            TextView btn = (TextView) view.findViewById(R.id.btn);
            btn.setBackgroundResource(colors[i % 5]);
            btn.setText(labels.get(i));
            mFlowLayout.addView(view);
        }
    }

    private void fetchData() {
        AVQuery<News> query = new AVQuery<>("News");
        query.whereEqualTo("type", 0);
        query.limit(1000);
        query.findInBackground(new FindCallback<News>() {
            @Override
            public void done(List<News> list, AVException e) {
                mNewsList.clear();
                mNewsList.addAll(list);
                mAdapter.setData(mNewsList);
            }
        });
    }

    private class NewsAdapter extends BaseAdapter {
        private int[] backgroundColor = {Color.rgb(0x8C, 0xD1, 0xEA), Color.rgb(0x70, 0xB9, 0x4D)};
        private int[] textColors = {Color.rgb(0x0D, 0x99, 0xFC), Color.rgb(0x00, 0x8D, 0x14)};
        private LayoutInflater inflater;
        private List<News> newsList = new ArrayList<>();

        public NewsAdapter(Context context, List<News> list) {
            inflater = LayoutInflater.from(context);
            newsList.addAll(list);
        }

        public void setData(List<News> list) {
            newsList.clear();
            newsList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_news, null);
                viewHolder = new ViewHolder();
                viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.title);
                viewHolder.mTvAuthor = (TextView) convertView.findViewById(R.id.author);
                viewHolder.mTvTime = (TextView) convertView.findViewById(R.id.time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            News news = newsList.get(position);
            viewHolder.mTvTitle.setText(news.getTitle());
            //viewHolder.mTvAuthor.setText(news.getAuthor());
            viewHolder.mTvTime.setText(news.getTime().substring(0, news.getTime().length() - 4));
            if (position % 2 == 0) {
                convertView.setBackgroundColor(backgroundColor[0]);
                viewHolder.mTvTitle.setTextColor(textColors[0]);
            } else {
                convertView.setBackgroundColor(backgroundColor[1]);
                viewHolder.mTvTitle.setTextColor(textColors[1]);
            }
            return convertView;
        }

        public class ViewHolder {
            TextView mTvTitle;
            TextView mTvAuthor;
            TextView mTvTime;
        }
    }
}
