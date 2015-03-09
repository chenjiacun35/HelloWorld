package stupad.openapi.lib;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.BitSet;


public class HttpRequestCommon {

	/*
	 * 获取POST请求数据
	 * */
	public String GetResponseData(String urlStr,String param) throws IOException
	{
		String result="";
		URL url = new URL(urlStr);
		HttpURLConnection connect =(HttpURLConnection) url.openConnection();
		connect.setRequestMethod("POST");
		connect.setRequestProperty("ACCEPT", "text/plain");
		connect.setRequestProperty("Content-type", "application/octet-stream");
		connect.setDoInput(true);
		connect.setDoOutput(true);
		byte[] parambyte = param.getBytes("utf-8");
		OutputStream os = connect.getOutputStream();
		os.write(parambyte,0,parambyte.length);
		os.flush();
		os.close();
		InputStream is = connect.getInputStream();
		byte[] resbyte = new byte[connect.getContentLength()];
		int c=0;
		while(c<resbyte.length)
		{
			c+=is.read(resbyte,c,resbyte.length-c);
		}
		result = new String(resbyte,0,resbyte.length);
		is.close();
		connect=null;
		return result;
	}
	
	
}
