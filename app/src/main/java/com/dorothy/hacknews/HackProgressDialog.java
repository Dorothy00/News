package com.dorothy.hacknews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by dorothy on 2017/3/19.
 */


public class HackProgressDialog extends ProgressDialog {

    public HackProgressDialog(Context context) {
        super(context, R.style.Progress_Dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
    }
}
