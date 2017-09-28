package com.example.savr.mlayu.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.savr.mlayu.BoardingIndicator;
import com.example.savr.mlayu.Fragment.AboutFragment1;
import com.example.savr.mlayu.Fragment.AboutFragment2;
import com.example.savr.mlayu.R;

public class AboutActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout wrapindicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        wrapindicator = (LinearLayout) findViewById(R.id.wrapIndicator);

        BoardingAdapter adapter = new BoardingAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //indicator
        BoardingIndicator indicator = new BoardingIndicator(this,
                wrapindicator, viewPager, R.drawable.indicator_circle);
        indicator.setPageCount(2);
        indicator.show();
    }

    public class BoardingAdapter extends FragmentPagerAdapter{

        public BoardingAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            if(position==0){
                fragment=new AboutFragment1();
            }else if(position==1){
                fragment=new AboutFragment2();
            }
            Bundle b= new Bundle();
            b.putInt("position",position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
