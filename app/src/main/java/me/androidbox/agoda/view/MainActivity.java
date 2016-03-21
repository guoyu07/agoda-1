package me.androidbox.agoda.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.androidbox.agoda.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUM_PAGES = 5;

    private ImageView mIvCircle0;
    private ImageView mIvCircle1;
    private ImageView mIvCircle2;
    private ImageView mIvCircle3;
    private ImageView mIvCircle4;
    private TextView mTvCity;

    /* Animation object */
    private Animation mScaleCircleAnim;
    private Animation mCityAnim;

    /* Keeps track of the page position when swiping right and left */
    private int mPreviousPosition;

    public MainActivity() {
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.fragment_main);

        /* Add the toolbar */
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarInclude);
        setSupportActionBar(toolbar);

        /* Manually adjust status bar for transparency */
    /*    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/

        // Set the padding to match the Status Bar height
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        /* Inflate the view into java objects */
        mIvCircle0 = (ImageView)findViewById(R.id.circle0);
        mIvCircle1 = (ImageView)findViewById(R.id.circle1);
        mIvCircle2 = (ImageView)findViewById(R.id.circle2);
        mIvCircle3 = (ImageView)findViewById(R.id.circle3);
        mIvCircle4 = (ImageView)findViewById(R.id.circle4);

        /* Clear all animations */
        mIvCircle0.clearAnimation();
        mIvCircle1.clearAnimation();
        mIvCircle2.clearAnimation();
        mIvCircle3.clearAnimation();
        mIvCircle4.clearAnimation();

        /* Get the viewpager where are fragment will be displayed */
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        /* Create a new ScreenSlidePageAdapter */
        PagerAdapter pagerAdapter = new ScreenSlidePageAdapter(getFragmentManager());
        /* Set the adapter to the viewpager */
        viewPager.setAdapter(pagerAdapter);

        Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
        mIvCircle0.startAnimation(scaleAnim);
        scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
        mIvCircle1.startAnimation(scaleAnim);
        scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
        mIvCircle2.startAnimation(scaleAnim);
        scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
        mIvCircle3.startAnimation(scaleAnim);
        scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
        mIvCircle4.startAnimation(scaleAnim);

        mTvCity = (TextView)findViewById(R.id.tvCity);
        mCityAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_disappear);

        /* Listen for the swiping events */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /* Ignore */
            }

            /*
               We need to establish if we are moving to the right or the left so we can scale down and up on the
               correct circles. We do this by keeping track of the position to see if it greater or less then the
               previous position
             */
            @Override
            public void onPageSelected(int position) {
                boolean moveRight = false;
                /* if the position is greater than mPreviousPosition we are moving right else we are moving left */
                if (position > mPreviousPosition) {
                    moveRight = true;
                }

                Log.d(TAG, "onPageSelected position " + position);
                switch(position) {
                    case 0:
                        if (moveRight) {
                            Log.d(TAG, "MoveRight");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle0.startAnimation(scaleAnim);

                            mTvCity.setText("Maldivies");
                            final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);

                        }
                        else {
                            Log.d(TAG, "MoveLeft");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle0.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle1.startAnimation(scaleAnim);

                            final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Santorini");
                        }
                        break;

                    case 1:
                        if (moveRight) {
                            Log.d(TAG, "moveRight");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle0.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle1.startAnimation(scaleAnim);

                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_disappear);
                            mTvCity.startAnimation(animation);

                            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Maldives");
                        }
                        else {
                            Log.d(TAG, "moveLeft");
                            mScaleCircleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle1.startAnimation(mScaleCircleAnim);

                            Log.d(TAG, "onPerformAnimation moveLeft");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle1.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle2.startAnimation(scaleAnim);

                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_disappear);
                            mTvCity.startAnimation(animation);

                            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Maldives");
                        }
                        break;

                    case 2:
                        if (moveRight) {
                            Log.d(TAG, "moveRight");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle1.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle2.startAnimation(scaleAnim);

                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_disappear);
                            mTvCity.startAnimation(animation);

                            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Hallstatt");
                        }
                        else {
                            Log.d(TAG, "moveLeft");
                            mScaleCircleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle2.startAnimation(mScaleCircleAnim);

                            Log.d(TAG, "onPerformAnimation moveLeft");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle2.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle3.startAnimation(scaleAnim);

                            final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("HallStatt");
                        }
                        break;

                    case 3:
                        if (moveRight) {
                            Log.d(TAG, "moveRight");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle2.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle3.startAnimation(scaleAnim);

                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_disappear);
                            mTvCity.startAnimation(animation);

                            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Taiwan");
                        }
                        else {
                            Log.d(TAG, "moveLeft");
                            mScaleCircleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle3.startAnimation(mScaleCircleAnim);

                            Log.d(TAG, "onPerformAnimation moveLeft");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle3.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle4.startAnimation(scaleAnim);

                            final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Taiwan");
                        }
                        break;

                    case 4:
                        if (moveRight) {
                            Log.d(TAG, "moveRight");
                            Animation scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaledown_circle);
                            mIvCircle3.startAnimation(scaleAnim);

                            scaleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle4.startAnimation(scaleAnim);

                            mScaleCircleAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scaleup_circle);
                            mIvCircle4.startAnimation(mScaleCircleAnim);

                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_disappear);
                            mTvCity.startAnimation(animation);

                            animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_appear);
                            mTvCity.startAnimation(animation);
                            mTvCity.setText("Bali");
                        }
                        else {
                            Log.d(TAG, "moveLeft");
                        }
                        break;
                }

                /* Update mPreviousPosition */
                mPreviousPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /* Ignore */
            }
        });
    }

    /* Loads each fragment based on the position */
    public static class ScreenSlidePageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList = Collections.emptyList();

        public ScreenSlidePageAdapter(FragmentManager fm) {
            super(fm);

            /* Create an arrayList of fragments that will be used for the viewpager to navigate through */
            mFragmentList = new ArrayList<>();
            mFragmentList.add(new SantoriniFragment());
            mFragmentList.add(new MaldivesFragment());
            mFragmentList.add(new HallstattFragment());
            mFragmentList.add(new TaiwanFragment());
            mFragmentList.add(new BaliFragment());
        }

        @Override
        public Fragment getItem(int position) {
            /* Return the fragment from the arrayList based on the position */
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            /* We need to return the number of pages - or nothing will show */
            return NUM_PAGES;
        }
    }

}
