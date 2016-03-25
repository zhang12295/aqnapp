package com.njaqn.itravel.aqnapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtil {
	
	private Context ctx;
	
	public FileUtil(Context ctx) {
		this.ctx = ctx;
	}

	public void saveToSDCard(String fileName, String content) throws Exception 
	{
		File f = new File(Environment.getExternalStorageDirectory(), fileName);
		
		FileOutputStream out = new FileOutputStream(f);
		out.write(content.getBytes());
		out.close();
	}
	
	public void save(String fileName, String content) throws Exception 
	{
		FileOutputStream out = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
		out.write(content.getBytes());
		out.close();
	}
	
	public void saveAppend(String fileName, String content) throws Exception 
	{
		FileOutputStream out = ctx.openFileOutput(fileName, Context.MODE_APPEND);
		out.write(content.getBytes());
		out.close();
	}
	
	public void saveReadable(String fileName, String content) throws Exception 
	{
		FileOutputStream out = ctx.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
		out.write(content.getBytes());
		out.close();
	}
	
	public void saveWriteable(String fileName, String content) throws Exception 
	{
		FileOutputStream out = ctx.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
		out.write(content.getBytes());
		out.close();
	}
	
	public void saveRW(String fileName, String content) throws Exception 
	{
		FileOutputStream outStream = ctx.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE + Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}
	
	public String read(String fileName) throws Exception 
	{
		FileInputStream in = ctx.openFileInput(fileName);
		byte[] data = StreamUtil.read(in);
		return new String(data);
	}
}
