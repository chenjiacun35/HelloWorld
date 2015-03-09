package stupad.openapi.lib;
import java.io.*;
import java.security.*;

public class StuPadFormatCommon {
 
	/*
	 * MD5加密
	 * */
	public static String getMd5(String param, String charset)
			throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		byte[] data = null;
		if (charset != null && charset != "") {
			md.update(param.getBytes(charset));
			data= md.digest();
		}else
		{
			md.update(param.getBytes());
			data= md.digest();
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<data.length;i++){
			if(Integer.toHexString(0xFF & data[i]).length() == 1){
				sb.append("0").append(Integer.toHexString(0xFF & data[i]));
			}else
			{
			sb.append(Integer.toHexString(0xFF & data[i]));
			}
		}
		return sb.toString();
	}
}
