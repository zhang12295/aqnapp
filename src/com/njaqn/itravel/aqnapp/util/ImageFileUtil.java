package com.njaqn.itravel.aqnapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageFileUtil {
	 /** 
     * sd���ĸ�Ŀ¼ 
     */  
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();  
    /** 
     * �ֻ�Ļ����Ŀ¼ 
     */  
    private static String mDataRootPath = null;  
    /** 
     * ����Image��Ŀ¼�� 
     */  
    private final static String FOLDER_NAME = "/AQN";  
      
      
    public ImageFileUtil(Context context){  
        mDataRootPath = context.getCacheDir().getPath();  
    }  
      
  
    /** 
     * ��ȡ����Image��Ŀ¼ 
     * @return 
     */  
    public String getStorageDirectory(){  
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?  
                mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;  
    }  
      
    /** 
     * ����Image�ķ�������sd���洢��sd����û�оʹ洢���ֻ�Ŀ¼ 
     * @param fileName  
     * @param bitmap    
     * @throws IOException 
     */  
    public void savaBitmap(String fileName, Bitmap bitmap) throws IOException{  
        if(bitmap == null){  
            return;  
        }  
        String path = getStorageDirectory();  
        File folderFile = new File(path);  
        if(!folderFile.exists()){  
            folderFile.mkdir();  
        }  
        File file = new File(path + File.separator + fileName);  
        file.createNewFile();  
        FileOutputStream fos = new FileOutputStream(file);  
        bitmap.compress(CompressFormat.JPEG, 100, fos);  
        fos.flush();  
        fos.close();  
    }  
      
    /** 
     * ���ֻ����sd����ȡBitmap 
     * @param fileName 
     * @return 
     */  
    public Bitmap getBitmap(String fileName){  
        return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + fileName);  
    }  
      
    /** 
     * �ж��ļ��Ƿ���� 
     * @param fileName 
     * @return 
     */  
    public boolean isFileExists(String fileName){  
        return new File(getStorageDirectory() + File.separator + fileName).exists();  
    }  
      
    /** 
     * ��ȡ�ļ��Ĵ�С 
     * @param fileName 
     * @return 
     */  
    public long getFileSize(String fileName) {  
        return new File(getStorageDirectory() + File.separator + fileName).length();  
    }  
      
      
    /** 
     * ɾ��SD�������ֻ�Ļ���ͼƬ��Ŀ¼ 
     */  
    public void deleteFile() {  
        File dirFile = new File(getStorageDirectory());  
        if(! dirFile.exists()){  
            return;  
        }  
        if (dirFile.isDirectory()) {  
            String[] children = dirFile.list();  
            for (int i = 0; i < children.length; i++) {  
                new File(dirFile, children[i]).delete();  
            }  
        }  
          
        dirFile.delete();  
    }  

}
