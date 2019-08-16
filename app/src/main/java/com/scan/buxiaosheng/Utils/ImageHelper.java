package com.scan.buxiaosheng.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Helper of image
 * Created by Thong on 2017/11/30.
 */

public class ImageHelper {
    /**
     * 加载图片
     *
     * @param context   context (activty is better)
     * @param url       img url (can be a uri to file)
     * @param imageview imageview
     */
    public static void loadImage(Context context, String url, ImageView imageview) {
        Glide.with(context).load(url).into(imageview);
    }

    //截取webView的屏幕，需要宽高
    public static Bitmap getWebViewBitmap(WebView webView, int height, int width) {
        float scale = webView.getScale();//获取缩放率
        scale += 0.1;
        Bitmap bitmap = Bitmap.createBitmap((int) (width * scale), (int )(height * scale), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //绘制
        webView.draw(canvas);
        return bitmap;
    }

    //截取屏幕
    public static String caputureScreen(Activity activity) {
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                LogUtils.e("存储完成");
                return file.getAbsolutePath();
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        }
        return "";
    }

    //保存图片
    public static void saveBitmap(Context context, Bitmap bitmap) {
        if (bitmap == null){
            return;
        }
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "BxsImg");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            UIUtils.ToastMsg(context, "保存图片失败");
        } finally{
            bitmap.recycle();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            UIUtils.ToastMsg(context, "保存图片成功，可在" + file.getAbsolutePath() + "文件夹中查看");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            UIUtils.ToastMsg(context, "保存图片失败");
        }
        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
}
