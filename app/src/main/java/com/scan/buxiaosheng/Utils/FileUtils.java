package com.scan.buxiaosheng.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

/**
 * Created by Bertram on 2019/8/2
 */
public class FileUtils {
    private static FileUtils defalt = null;

    private FileUtils() {

    }

    public static FileUtils getInstance() {
        if (defalt == null){
            synchronized (FileUtils.class){
                if (defalt == null){
                    defalt = new FileUtils();
                }
            }
        }
        return defalt;
    }

    //删除文件夹文件
    public void deleteAllFile(String filePath){
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            for (int i = 0; i < file.listFiles().length; i++) {
                file.listFiles()[i].delete();
            }
        }
    }

    //删除文件
    public void deleteFile(String filePath){
        File file = new File(filePath);
        if (file.exists() && file.isFile()){
            file.delete();
        }
    }

    //查看文件夹内容
    public File[] getFiles(String filePath){
        return new File(filePath).listFiles();
    }

    //判断文件夹路径下文件是否存在
    public boolean FileExists(String path, String fileName){
        for (int i=0;i<getFiles(path).length;i++){
            if (getFiles(path)[i].isFile()){
                if (getFiles(path)[i].getName().equals(fileName)){
                    return true;
                }
            }
        }
        return false;
    }

    //查看文件属性
    public PackageInfo getApkInfo(Context context, String filePath){
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (info != null){
            return info;
        }else{
            return null;
        }
    }
}
