package com.testretrofit.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyFont {

    private Context context;
    private Typeface mTypeface;

    private static final String NotoSansUI_Regular = "NotoSansUI-Regular.ttf";

    private static final String DEFAULT_FONT = NotoSansUI_Regular;
    private final String folder = "font/";

    public MyFont(Context context) {
        this.context = context;
        mTypeface = Typeface.createFromAsset(context.getAssets(), folder + DEFAULT_FONT);
    }

    public void setTypeface(ViewGroup viewTree) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);

            if (child instanceof ViewGroup) {
                setTypeface((ViewGroup) child);

            } else if (child instanceof TextView) {
                setTypeface((TextView) child);

            } else if (child instanceof EditText) {
                setTypeface((EditText) child);

            } else if (child instanceof Button) {
                setTypeface((Button) child);

            } else if (child instanceof SwitchCompat) {
                setTypeface((SwitchCompat) child);
            }
        }
    }

    public void setTypeface(TextView textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(EditText textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(Button textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(SwitchCompat textView) {
        textView.setTypeface(mTypeface);
    }
}