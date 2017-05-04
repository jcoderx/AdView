package cn.xudaodao.android.adview.indicator;

import android.support.v4.view.ViewPager;

/**
 *
 */
public interface BasePageIndicator extends ViewPager.OnPageChangeListener {
    /**
     * Bind the indicator to a ViewPager.
     *
     * @param viewPager
     */
    void setViewPager(ViewPager viewPager);

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param viewPager
     * @param realCount
     */
    void setViewPager(ViewPager viewPager, int realCount);

    /**
     * <p>Set the current page of both the ViewPager and indicator.</p>
     * <p/>
     * <p>This <strong>must</strong> be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).</p>
     *
     * @param item
     */
    void setCurrentItem(int item);
}
