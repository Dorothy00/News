package com.dorothy.hacknews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dorothy on 2017/3/18.
 */

public class BubbleView extends View {
    private int maxHeight;
    private int maxLength;
    private int gap;
    private int column;
    private int rowNum;
    private int rowHeight;
    private int curRow;
    private int curX;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    //private int[] colors = {Color.rgb(0x8C, 0xD1, 0xEA), Color.rgb(0x70, 0xB9, 0x4D)};
    private int[] colors = {Color.rgb(0xFA, 0x73, 0x5C), Color.rgb(0x72, 0x7C, 0xC9), Color.rgb(0x57, 0xCF, 0xFD), Color.rgb(0x8F, 0xCF, 0x70), Color.rgb(0xFD, 0xB6, 0x67)};
    private int[] textColors = {Color.rgb(0x0D, 0x99, 0xFC), Color.rgb(0x00, 0x8D, 0x14)};
    private int[] textSize = {20, 25, 30, 35, 40};
    private int maxSize = 0;
    private int minSize = 0;

    private Random random = new Random();

    private List<Bubble> mDataSet = new ArrayList<>();

    private OnBubbleSelectedListener mListener;

    private Context context;
    private int toolbarHeight;

    interface OnBubbleSelectedListener {
        void bubbleSelected(Bubble entry);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);
        this.context = context;
        toolbarHeight = Utils.dip2px(context, 56);
        maxHeight = Utils.getScreenMetrics(context).heightPixels - toolbarHeight;
        gap = 2;
        rowNum = 8;
        curX = getPaddingLeft();
        maxLength = Utils.getScreenMetrics(context).widthPixels;
        rowHeight = maxHeight / rowNum;
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);

        initData();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



    private void initData() {

        Bubble entry1 = new Bubble();
        entry1.setSize(300);
        entry1.setX(maxLength / 2);
        entry1.setY(maxHeight / 2);
        mDataSet.add(entry1);

        Bubble entry2 = new Bubble();
        entry2.setSize(220);
        entry2.setX(maxLength / 3 - 60);
        entry2.setY(maxHeight / 3 - 180);
        mDataSet.add(entry2);

        Bubble entry3 = new Bubble();
        entry3.setSize(200);
        entry3.setX(maxLength/2 + 300);
        entry3.setY(maxHeight/2 - 420);
        mDataSet.add(entry3);

        Bubble entry4 = new Bubble();
        entry4.setSize(180);
        entry4.setX(maxLength/2 - 330);
        entry4.setY(maxHeight / 2 + 380);
        mDataSet.add(entry4);

        Bubble entry5 = new Bubble();
        entry5.setSize(230);
        entry5.setX(maxLength / 2 + 220);
        entry5.setY(maxHeight /2 + 500);
        mDataSet.add(entry5);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        mCirclePaint.setShadowLayer(12, 0, 0, Color.rgb(0xC0, 0xC0, 0xC0));
//        mCirclePaint.setColor(colors[0]);
//        canvas.drawCircle(maxLength / 2, maxHeight / 2, 300, mCirclePaint);
//
//        mCirclePaint.setColor(colors[1]);
//        canvas.drawCircle(maxLength / 3 - 60, maxHeight / 3 - 180, 220, mCirclePaint);
//
//        mCirclePaint.setColor(colors[2]);
//        canvas.drawCircle(maxLength/2 + 300, maxHeight/2 - 400, 200, mCirclePaint);
//
//        mCirclePaint.setColor(colors[3]);
//        canvas.drawCircle(maxLength/2 - 330, maxHeight / 2 + 380, 180, mCirclePaint);
//
//        mCirclePaint.setColor(colors[4]);
//        canvas.drawCircle(maxLength / 2 + 220, maxHeight /2 + 500, 230, mCirclePaint);

        for(int i = 0; i < 5; i++){
            Bubble entry = mDataSet.get(i);
            mCirclePaint.setColor(colors[i]);
            canvas.drawCircle(entry.getX(),entry.getY(),entry.getSize(), mCirclePaint);
        }

    }


    private int selectedIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < mDataSet.size(); i++) {
                    Bubble entry = mDataSet.get(i);
                    if (isInBubble(x, y, entry)) {
                        selectedIndex = i;
                        entry.setSize(entry.getSize() + 20);
                        invalidate();
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (selectedIndex != -1) {
                    Bubble entry = mDataSet.get(selectedIndex);
                    entry.setSize(entry.getSize() - 20);
                    invalidate();
                    if (mListener != null) {
                        mListener.bubbleSelected(entry);
                    }
                }
                break;
        }
        return true;
    }


    private boolean isInBubble(int x, int y, Bubble bubbleEntry) {
        return Math.sqrt((bubbleEntry.getX() - x) * (bubbleEntry.getX() - x) + (bubbleEntry.getY
                () - y) * (bubbleEntry.getY() - y)) <= bubbleEntry.getSize();
    }

    public void setOnBubbleSelectedListener(OnBubbleSelectedListener listener) {
        mListener = listener;
    }
}
