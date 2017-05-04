package cn.xudaodao.android.adview.siderview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * 默认实现SliderView
 */
public class DefaultSliderView extends BaseSliderView {
    /**
     * 图片点击
     */
    public interface OnClickImageListener {
        public void onImageClick(View view);
    }

    private int mImageErrorResId;
    private int mImageEmptyResId;

    private String mUrl;
    private File mFile;
    private int mResId;

    private OnClickImageListener mOnClickImageListener;

    public DefaultSliderView(Context context) {
        super(context);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public View getView() {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickImageListener != null) {
                    mOnClickImageListener.onImageClick(imageView);
                }
            }
        });
        loadImage(imageView);

        return imageView;
    }

    public DefaultSliderView setImageSource(String url) {
        if (mFile != null || mResId != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mUrl = url;
        return this;
    }

    public DefaultSliderView setImageSource(File file) {
        if (mUrl != null || mResId != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    public DefaultSliderView setImageSource(int resId) {
        if (mUrl != null || mFile != null) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mResId = resId;
        return this;
    }

    public DefaultSliderView setImageErrorResId(int resId) {
        mImageErrorResId = resId;
        return this;
    }

    public DefaultSliderView setImageEmptyResId(int resId) {
        mImageEmptyResId = resId;
        return this;
    }

    public DefaultSliderView setOnClickImageListener(OnClickImageListener listener) {
        mOnClickImageListener = listener;
        return this;
    }

    public int getImageErrorResId() {
        return mImageErrorResId;
    }

    public int getmImageEmptyResId() {
        return mImageEmptyResId;
    }

    public String getImageUrl() {
        return mUrl;
    }

    public File getImageFile() {
        return mFile;
    }

    public int getImageResId() {
        return mResId;
    }

    private void loadImage(ImageView imageView) {
        if (mResId != 0) {
            imageView.setImageResource(mResId);
        } else {
            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
            if (mImageEmptyResId != 0) {
                //builder.showImageForEmptyUri(mImageEmptyResId);
                builder.showImageOnLoading(mImageEmptyResId);
            }
            if (mImageErrorResId != 0) {
                builder.showImageOnFail(mImageErrorResId);
            }
            builder.cacheInMemory(true).cacheOnDisk(true);
            DisplayImageOptions options = builder.build();

            ImageLoader imageLoader = ImageLoader.getInstance();

            String uri = "";
            if (mUrl != null) {
                uri = mUrl;
            } else if (mFile != null) {
                uri = "file:///" + mFile.getAbsolutePath();
            }
            imageLoader.displayImage(uri, imageView, options);
        }
    }
}
