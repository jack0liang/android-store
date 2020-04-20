package com.gialen.baselib.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.gialen.baselib.R;


public class ImageLoader {


    /**
     * @param context
     * @param resId
     * @param placeholderResId
     * @param errorResId
     * @param transformation
     * @param isCached         是否使用缓存
     * @param imageView
     */
    public static void setImageResourceCenterCrop(Context context, int resId, int placeholderResId, int errorResId, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options = options.centerCrop();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
        }
        if (errorResId != -1) {
            options = options.error(errorResId);
        }
        if (transformation != null) {
            options = options.transform(transformation);
        }
        if (isCached) {
            options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        } else {
            options = options.priority(Priority.HIGH).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        Glide.with(context).load(resId).apply(options).into(imageView);
    }

    /**
     * @param context
     * @param imagePath
     * @param placeholderResId
     * @param errorResId
     * @param transformation
     * @param isCached         是否使用缓存
     * @param imageView
     */
    public static void setImageResourceCenterCrop(Context context, String imagePath, int placeholderResId, int errorResId, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options = options.centerCrop();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
        }
        if (errorResId != -1) {
            options = options.error(errorResId);
        }
        if (transformation != null) {
            options = options.transform(transformation);
        }
        if (isCached) {
            options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        } else {
            options = options.priority(Priority.HIGH).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        Glide.with(context).load(imagePath).apply(options).into(imageView);
    }

    /**
     * @param context
     * @param resId
     * @param placeholderResId
     * @param errorResId
     * @param transformation
     * @param isCached         是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, int resId, int placeholderResId, int errorResId, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        isCached = true;
        RequestOptions options = new RequestOptions();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
        }
        if (errorResId != -1) {
            options = options.error(errorResId);
        }
        if (transformation != null) {
            options = options.transform(transformation);
        }
        if (isCached) {
            options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        } else {
            options = options.priority(Priority.HIGH).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        Glide.with(context).load(resId).apply(options).into(imageView);
    }


    /**
     * @param context
     * @param imgPath
     * @param placeholderResId
     * @param errorResId
     * @param transformation
     * @param isCached         是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, int placeholderResId, int errorResId, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        isCached = true;
        RequestOptions options = new RequestOptions();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
        }
        if (errorResId != -1) {
            options = options.error(errorResId);
        }
        if (transformation != null) {
            options = options.transform(transformation);
        }
        if (isCached) {
            options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        } else {
            options = options.priority(Priority.HIGH).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(context).load(imgPath).apply(options).into(imageView);
        }else {
            Glide.with(context).load(R.mipmap.ic_default_logo).apply(options).into(imageView);
        }

    }

    /**
     * @param context
     * @param imgPath
     * @param placeholderResId
     * @param width
     * @param height
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, int placeholderResId, int width, int height, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
            options = options.error(placeholderResId);
        }
        options = options.override(width, height);
        options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(context).load(imgPath).apply(options).into(imageView);
        }else {
            Glide.with(context).load(R.mipmap.ic_default_logo).apply(options).into(imageView);
        }


    }
  /**
     * @param context
     * @param imgPath
     * @param width
     * @param height
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, int width, int height, ImageView imageView) {
        RequestOptions options = new RequestOptions();

        options = options.override(width, height);
        options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(context).load(imgPath).apply(options).into(imageView);
        }else {
            Glide.with(context).load(R.mipmap.ic_default_logo).apply(options).into(imageView);
        }


    }


    public static void setImageResource(Context context, String imgPath,  int width, int height, BitmapTransformation transformation, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options = options.override(width, height);
        options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (transformation != null) {
            options = options.transform(transformation);
        }
        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(context).load(imgPath).apply(options).into(imageView);
        }else {
            Glide.with(context).load(R.mipmap.ic_default_logo).apply(options).into(imageView);
        }

    }

    /**
     * @param context
     * @param imgPath
     * @param placeholderResId
     * @param width
     * @param height
     * @param imageView
     */
    public static void setImageResource(Context context, int imgPath, int placeholderResId, int width, int height,BitmapTransformation transformation, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
            options = options.error(placeholderResId);

        }
        options = options.override(width, height);
        options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context).load(imgPath).apply(options).into(imageView);
    }
    public static void setImageResource(Context context, int imgPath,  int width, int height, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        options = options.override(width, height);
        options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context).load(imgPath).apply(options).into(imageView);
    }
  /**
     * @param context
     * @param imgPath
     * @param placeholderResId
     * @param width
     * @param height
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, int placeholderResId, int width, int height,BitmapTransformation transformation, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        if (placeholderResId != -1) {
            options = options.placeholder(placeholderResId);
            options = options.error(placeholderResId);
        }
        if (transformation!=null){
            options = options.transform(transformation);

        }
        options = options.override(width, height);
        options = options.priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context).load(imgPath).apply(options).into(imageView);
    }


    /**
     * @param context
     * @param resId
     * @param placeholderResId
     * @param errorResId
     * @param isCached         是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, int resId, int placeholderResId, int errorResId, boolean isCached, ImageView imageView) {
        setImageResource(context, resId, placeholderResId, errorResId, null, isCached, imageView);
    }

    /**
     * @param context
     * @param resId
     * @param imageView
     */
    public static void setImageResource(Context context, int resId, ImageView imageView) {
        setImageResource(context, resId, -1, -1, null, false, imageView);
    }

  /**
     * @param context
     * @param imgPath
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, ImageView imageView) {
        setImageResource(context, imgPath, -1, -1, null, false, imageView);
    }


    /**
     * @param context
     * @param imgPath
     * @param placeholderResId
     * @param errorResId
     * @param isCached         是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, int placeholderResId, int errorResId, boolean isCached, ImageView imageView) {
        setImageResource(context, imgPath, placeholderResId, errorResId, null, isCached, imageView);
    }

    /**
     * @param context
     * @param imgPath
     * @param isCached  是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, boolean isCached, ImageView imageView) {
        setImageResource(context, imgPath, -1, -1, null, isCached, imageView);
    }


    /**
     * @param context
     * @param resId
     * @param transformation
     * @param isCached       是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, int resId, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        setImageResource(context, resId, -1, -1, transformation, isCached, imageView);
    }


    /**
     * @param context
     * @param imgPath
     * @param transformation
     * @param isCached       是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        setImageResource(context, imgPath, -1, -1, transformation, isCached, imageView);
    }

    /**
     * @param context
     * @param imgPath
     * @param transformation
     * @param isCached       是否使用缓存
     * @param imageView
     */
    public static void setImageResource(Context context, String imgPath, int placeholderResId, BitmapTransformation transformation, boolean isCached, ImageView imageView) {
        setImageResource(context, imgPath, placeholderResId, -1, transformation, isCached, imageView);
    }


}
