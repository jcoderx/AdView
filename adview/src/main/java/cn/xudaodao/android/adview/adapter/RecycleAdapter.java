package cn.xudaodao.android.adview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xudaodao.android.adview.siderview.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclingPagerAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<BaseSliderView> mSliderViews;
    private boolean isLoop;

    public RecycleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mSliderViews = new ArrayList<BaseSliderView>();
    }

    public int getRealCount() {
        return mSliderViews.size();
    }

    public <T extends BaseSliderView> void setSliderViews(List<T> sliderViews) {
        mSliderViews.clear();
        mSliderViews.addAll(sliderViews);
        notifyDataSetChanged();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    public int getPosition(int position) {
        return isLoop ? position % getRealCount() : position;
    }

    @Override
    public int getCount() {
        return (isLoop && getRealCount() != 1) ? getRealCount() * 100 : getRealCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        return ((BaseSliderView) mSliderViews.get(getPosition(position))).getView();
    }

    public void removeAllSliders() {
        mSliderViews.clear();
        notifyDataSetChanged();
    }

    /**
     * @return the is Loop
     */
    public boolean isLoop() {
        return isLoop;
    }

    /**
     * @param isLoop the is InfiniteLoop to set
     */
    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
        notifyDataSetChanged();
    }

}
