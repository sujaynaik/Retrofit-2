package com.testretrofit.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.testretrofit.R;
import com.testretrofit.fragment.Slide1Fragment;
import com.testretrofit.util.MyPageTransformer;
import com.testretrofit.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalkthroughActivity extends AppCompatActivity implements Slide1Fragment.OnFragmentInteractionListener,
        ViewPager.OnPageChangeListener {

    private String TAG = WalkthroughActivity.class.getSimpleName();
    private Context context = WalkthroughActivity.this;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv)
    ImageView iv;
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        setViewPagerAdapter();
    }

    private void setViewPagerAdapter() {
        viewPager.setOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new MyPageTransformer());
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(screenSlidePagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Utils.loge(TAG, "position : " + position);
        Utils.loge(TAG, "positionOffset : " + positionOffset);
        Utils.loge(TAG, "positionOffsetPixels : " + positionOffsetPixels);

        switch (position) {
            case 0:
                iv.setTranslationX(positionOffsetPixels / 2);
                iv.setTranslationY(positionOffsetPixels / 2);
//                iv.setAlpha(positionOffset);
                break;

            case 1:
                iv.setTranslationX(positionOffsetPixels / 4);
                iv.setTranslationY(positionOffsetPixels / 2);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Slide1Fragment slide1Fragment = Slide1Fragment.newInstance(position);
            return slide1Fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public enum Content {
        ONE("1", Color.GREEN),
        TWO("2", Color.YELLOW),
        THREE("3", Color.RED),
        FOUR("4", Color.BLUE),
        FIVE("5", Color.CYAN),
        SIX("6", Color.LTGRAY),
        SEVEN("7", Color.MAGENTA),
        EIGHT("8", Color.DKGRAY);

        private final String mText;
        private final int mColor;

        Content(final String text, final int color) {
            mText = text;
            mColor = color;
        }

        public String getText() {
            return mText;
        }

        public int getColor() {
            return mColor;
        }
    }
}
