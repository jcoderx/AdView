package cn.xudaodao.android.adviewsimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.xudaodao.android.adview.InfiniteViewPager;
import cn.xudaodao.android.adview.anim.select.ZoomInEnter;
import cn.xudaodao.android.adview.indicator.DefaultPageIndicator;
import cn.xudaodao.android.adview.siderview.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    InfiniteViewPager infiniteViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String[] urls = new String[]{"http://img12.360buyimg.com/da/jfs/t5530/249/926379937/102829/ced5ea6d/5909448cN8bbce617.jpg",
                "http://img1.360buyimg.com/da/jfs/t5410/118/968510004/75133/54261daf/5909876bN2b46a6c5.jpg",
                "http://img12.360buyimg.com/da/jfs/t3172/29/7532815266/78514/96c6e177/58ba3348N479cafe1.jpg",
                "http://img20.360buyimg.com/da/jfs/t3259/102/5383871463/192761/bd7f9979/586b498aN1db38897.jpg",
                "http://img12.360buyimg.com/da/jfs/t3883/94/1442628802/224199/40dcb547/58c0b2dcN78fd0fb3.jpg",
                "http://img30.360buyimg.com/da/jfs/t5434/242/1018635922/98586/c0b3cb51/59098612Ne8c7fe87.jpg",
                "http://img14.360buyimg.com/da/jfs/t5470/221/956983699/82169/fd06e6bf/590984a5N29ad7687.jpg",
                "http://img13.360buyimg.com/da/jfs/t5200/53/933891628/97685/bbf903a8/5909950dNd0ce5d8f.jpg"};

        infiniteViewPager = (InfiniteViewPager) findViewById(R.id.infiniteviewpager);

        initInfiniteViewPagerLayoutParams(infiniteViewPager);
        infiniteViewPager.setInfinite(true);

        List<DefaultSliderView> views = new ArrayList<DefaultSliderView>();
        for (int i = 0; i < urls.length; i++) {
            final int index = i;
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView.setImageSource(urls[i]).setImageEmptyResId(R.mipmap.ic_launcher).setOnClickImageListener(new DefaultSliderView.OnClickImageListener() {
                @Override
                public void onImageClick(View view) {
                    Toast.makeText(MainActivity.this, index + " click", Toast.LENGTH_SHORT).show();
                }
            });
            views.add(defaultSliderView);

        }
        infiniteViewPager.setSliderViews(views);


        indicator(R.id.indicator_circle, views.size());
        indicator(R.id.indicator_square, views.size());
        indicator(R.id.indicator_round_rectangle, views.size());
        indicator(R.id.indicator_circle_stroke, views.size());
        indicator(R.id.indicator_square_stroke, views.size());
        indicator(R.id.indicator_round_rectangle_stroke, views.size());
        indicator(R.id.indicator_circle_snap, views.size());

        indicatorAnim(views.size());
        indicatorRes(views.size());
    }

    private void initInfiniteViewPagerLayoutParams(InfiniteViewPager infiniteViewPager) {

        //790 * 340
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int width = metrics.widthPixels;

        int infiniteViewPagerHeight = 340 * width / 790;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, infiniteViewPagerHeight);
        infiniteViewPager.setLayoutParams(layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        infiniteViewPager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        infiniteViewPager.stopAutoScroll();
    }


    //--------设置指示器-------------
    private void indicator(int indicatorId, int size) {
        final DefaultPageIndicator indicator = (DefaultPageIndicator) findViewById(indicatorId);
        indicator.setViewPager(infiniteViewPager, size);
    }

    private void indicatorAnim(int size) {
        final DefaultPageIndicator indicator = (DefaultPageIndicator) findViewById(R.id.indicator_circle_anim);
        indicator
                .setIsSnap(true)
                .setSelectAnimClass(ZoomInEnter.class)
                .setViewPager(infiniteViewPager, size);

    }

    private void indicatorRes(int size) {
        final DefaultPageIndicator indicator_res = (DefaultPageIndicator) findViewById(R.id.indicator_res);
        indicator_res
                .setViewPager(infiniteViewPager, size);
    }
}
