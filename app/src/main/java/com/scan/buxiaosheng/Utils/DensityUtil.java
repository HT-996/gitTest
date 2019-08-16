package com.scan.buxiaosheng.Utils;

import android.content.Context;

/**
 * 屏幕计算工具类
 * Created by Thong on 2017/12/11.
 */

public class DensityUtil {

    /**
     * dp to px
     * @param context context
     * @param dipValue dp value
     * @return px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px to dp
     * @param context context
     * @param pxValue px value
     * @return dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp to px
     * @param context context
     * @param spVal sp value
     * @return px
     */

    public static int sp2px(Context context, float spVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) ((spVal * scale) + 0.5f);
    }

    /**
     * px to sp
     * @param context context
     * @param pxVal px value
     * @return sp
     */

    public static float px2sp(Context context, float pxVal) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxVal / fontScale + 0.5f);
    }
}
