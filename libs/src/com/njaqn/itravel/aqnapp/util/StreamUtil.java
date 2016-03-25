package com.njaqn.itravel.aqnapp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {

	public static byte[] read(InputStream in) throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		while((len = in.read(data)) != -1 )
		{
			out.write(data,0,len);
		}
		
		in.close();
		return out.toByteArray();
	}
}
