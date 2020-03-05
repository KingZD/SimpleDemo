package com.zed.simple.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.zed.simple.SimpleApplication;


/**
 * 和Ui相关的工具类
 */
public class UiUtils {

    public static Resources getResource() {
        return SimpleApplication.getContext().getResources();
    }

    public static Context getContext() {
        return SimpleApplication.getContext();
    }

    /**
     * 获取到string字符数组
     *
     * @param tabNames 字符数组id
     * @return
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    /**
     * dip转换px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int dip2px(Resources resources, int dip) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     *
     * @param px
     * @return
     */
    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 绑定布局文件
     * <p/>
     * Fragment的initView()可使用
     *
     * @param id layout布局文件
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /**
     * 得到图片 - use Resource by id
     *
     * @param id
     * @return
     */
    public static Drawable getDrawalbe(int id) {
        return getResource().getDrawable(id);
    }

    /**
     * 得到颜色 - use Resource by id
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getResource().getColor(id);
    }

    /**
     * 获取dimens中的值 - use Resource by id
     *
     * @param homePictureHeight id
     * @return
     */
    public static int getDimens(int homePictureHeight) {
        return (int) getResource().getDimension(homePictureHeight);
    }

    /**
     * 得到屏幕宽度
     *
     * @return px
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);// 取得窗口属性

        return dm.widthPixels;
    }

    /**
     * 得到屏幕高度
     *
     * @return px
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);// 取得窗口属性

        return dm.heightPixels;
    }

    /**
     * 图片高度(宽度全屏,得到图片的显示高度)
     * <p/>
     * 通过比例公式:screenWidth/x = 2/1 (微绘图片规定尺寸)
     *
     * @return
     */
    public static int getPicShowHeight() {
        int h = Math.round((float) (getScreenWidth() * 1) / (float) 2);
        return h;
    }

    /**
     * 图片高度(宽度输入,得到图片的显示高度)
     * <p/>
     * 通过比例公式:screenWidth/x = 750/560 (微绘图片规定尺寸)
     *
     * @return
     */
    public static int getPicShowHeight(int width) {
        int h = Math.round((float) (width * 560) / (float) 750);
        return h;
    }

    /**
     * 得到状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResource().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResource().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
