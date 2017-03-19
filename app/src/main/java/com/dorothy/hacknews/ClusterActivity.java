package com.dorothy.hacknews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BubbleChart;

public class ClusterActivity extends AppCompatActivity implements BubbleView.OnBubbleSelectedListener{

    private  BubbleView mBubbleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBubbleView = (BubbleView) findViewById(R.id.bubble_view);
        mBubbleView.setOnBubbleSelectedListener(this);

    }

    @Override
    public void bubbleSelected(Bubble entry) {
        // todo
        Intent intent = new Intent(this, NewsListActivity.class);
        //intent.putExtra("type", entry.getType());
        intent.putExtra("type", 0);
        //intent.putExtra("name", entry.getLabel());
        intent.putExtra("name", "type1");
        intent.putStringArrayListExtra("labels", entry.getLabels());

        startActivity(intent);

    }
}

