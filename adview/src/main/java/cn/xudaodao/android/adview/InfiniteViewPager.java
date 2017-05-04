package cn.xudaodao.android.adview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.xudaodao.android.adview.adapter.RecycleAdapter;
import cn.xudaodao.android.adview.siderview.BaseSliderView;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 *
 */
public class InfiniteViewPager extends ViewPager {
    public static final int DEFAULT_INTERVAL = 2500;
    private static final int MSG_WHAT_SCROLL = 0;

    private Context mContext;
    private ScrollHandler mScrollHandler;
    private RecycleAdapter mRecycleAdapter;

    /**
     * 是否无限循环
     */
    private boolean isInfinite;
    /**
     * 间隔
     */
    private long interval = DEFAULT_INTERVAL;

    public InfiniteViewPager(Context context) {
        super(context);
        mContext = context;
        initViewPager();
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViewPager();
    }

    private void initViewPager() {
        mScrollHandler = new ScrollHandler(this);
        mRecycleAdapter = new RecycleAdapter(mContext);
        setAdapter(mRecycleAdapter);
    }

    public <T extends BaseSliderView> void setSliderViews(List<T> sliderViews) {
        mRecycleAdapter.setSliderViews(sliderViews);
        initFirstPage();
    }

    /**
     * 滚动到下一个page
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = ++currentItem;
        if (nextItem == totalCount) {
            if (isInfinite) {
                setCurrentItem(0);
            }
        } else {
            setCurrentItem(nextItem);
        }
    }

    private void sendScrollMessage(long delayTimeInMills) {
        mScrollHandler.removeMessages(MSG_WHAT_SCROLL);
        mScrollHandler.sendEmptyMessageDelayed(MSG_WHAT_SCROLL, delayTimeInMills);
    }

    private void sendScrollMessage() {
        sendScrollMessage(interval);
    }


    public static class ScrollHandler extends Handler {
        public WeakReference<InfiniteViewPager> mLeakActivityRef;

        public ScrollHandler(InfiniteViewPager infiniteViewPager) {
            mLeakActivityRef = new WeakReference<InfiniteViewPager>(infiniteViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            InfiniteViewPager infiniteViewPager = mLeakActivityRef.get();
            if (infiniteViewPager != null) {
                switch (msg.what) {
                    case MSG_WHAT_SCROLL:
                        infiniteViewPager.scrollOnce();
                        infiniteViewPager.sendScrollMessage();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private boolean isAutoScroll;
    private boolean isStopByTouch;

    /**
     * 开始自动滚动
     */
    public void startAutoScroll() {
        if (mRecycleAdapter.getRealCount() > 1) {
            isAutoScroll = true;
            sendScrollMessage();
        }
    }

    /**
     * 停止自动滚动
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        mScrollHandler.removeMessages(MSG_WHAT_SCROLL);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_DOWN && isAutoScroll) {
            isStopByTouch = true;
            stopAutoScroll();
        }
        if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) && isStopByTouch) {
            startAutoScroll();
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isInfinite() {
        return isInfinite;
    }

    public void setInfinite(boolean isInfinite) {
        this.isInfinite = isInfinite;
        mRecycleAdapter.setLoop(isInfinite);
        initFirstPage();
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScrollHandler.removeCallbacksAndMessages(null);
    }

    public void initFirstPage() {
        if (isInfinite && mRecycleAdapter.getRealCount() > 1) {
            setCurrentItem(mRecycleAdapter.getRealCount() * 50);
        } else {
            setCurrentItem(0);
        }
    }
}
