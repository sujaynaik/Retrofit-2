package com.testretrofit.util;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.testretrofit.activity.WalkthroughActivity;

/**
 * Created by hclpc on 2/11/2017.
 */

public class MyPageTransformer implements ViewPager.PageTransformer {

    private String TAG = MyPageTransformer.class.getSimpleName();

    @Override
    public void transformPage(View page, float position) {
        int pageIndex = (Integer) page.getTag(); // reading index
        pageIndex++;

        if (position > -2 && position < 2) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setBackgroundColor(WalkthroughActivity.Content.values()[pageIndex].getColor());

            } else if (position < 0) { // [-1,0)
                // Left visible page fraction
                final int leftIndex = pageIndex - 1;
                final int rightIndex = pageIndex;
                final int leftColor = WalkthroughActivity.Content.values()[leftIndex].getColor();
                final int rightColor = WalkthroughActivity.Content.values()[rightIndex].getColor();

                final int composedColor = Utils.blendColors(leftColor, rightColor, 1 - Math.abs(position));
                page.setBackgroundColor(composedColor);

            } else if (position == 0) {
                // One page centered
                page.setBackgroundColor(WalkthroughActivity.Content.values()[pageIndex].getColor());

            } else if (position > 0 && position <= 1) { // (0, 1]
                // Right visible page fraction
                final int leftIndex = pageIndex;
                final int rightIndex = pageIndex + 1;
                final int leftColor = WalkthroughActivity.Content.values()[leftIndex].getColor();
                final int rightColor = WalkthroughActivity.Content.values()[rightIndex].getColor();

                final int composedColor = Utils.blendColors(leftColor, rightColor, position);
                page.setBackgroundColor(composedColor);

            } else { // (1, Infinity]
                // This page is way off-screen to the right.
                page.setBackgroundColor(WalkthroughActivity.Content.values()[pageIndex].getColor());
            }
        }
    }
}
