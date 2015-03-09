package stupad.openapi.lib;

import java.io.IOException;

public class GetNetOpenapiData {

	HttpRequestCommon httpRequestCommon = new HttpRequestCommon();
	public static String GetNavigationApiName="net.getnavigationlist";
	public static String SECERTKEY="79790D6E1F86880A";
	public static String apiUrl = "http://openapi2.stu.test.nd/handler/nethandler.ashx";
	public String GetNavigationListByCategoryId(String url, int categoryid)
	throws Exception
    {
		String param = "{\"op\":\"" + GetNavigationApiName + "\",\"categoryId\":" + categoryid +  "}";
		String validate =StuPadFormatCommon.getMd5(param+SECERTKEY, "UTF-8");
		param = param.substring(0, param.length()-1)+ ",\"validate\":\"" + validate + "\"}";
		String res = httpRequestCommon.GetResponseData(url, param);
        return res;
    }
	
	public String GetWeather(String url,String jd,String wd,String province,String city,String area) throws Exception
	{
		String param="{\"jd\":\""+jd+"\",\"wd\":\""+wd+"\",\"province\":\""+province+"\",\"city\":\""+city+"\",\"area\":\""+area+"\",\"op\":\"sys.getweather\"}";
		String validate=StuPadFormatCommon.getMd5(param+SECERTKEY, "UTF-8");
		param = param.substring(0, param.length()-1)+ ",\"validate\":\"" + validate + "\"}";
		String res = httpRequestCommon.GetResponseData(url, param);
		return res;
	}
	
	public String GetYiJi(String url,String date) throws Exception
	{
		String param="{\"date\":\""+date+"\",\"op\":\"sys.getdayyj\"}";
		String validate=StuPadFormatCommon.getMd5(param+SECERTKEY, "UTF-8");
		param = param.substring(0, param.length()-1)+ ",\"validate\":\"" + validate + "\"}";
		String res = httpRequestCommon.GetResponseData(url, param);
		return res;
	}
	
	public String GetStars(String url,String birthday,String effectiveDate) throws Exception
	{
		String param="{\"birthday\":\""+birthday+"\",\"effectiveDate\":\""+effectiveDate+"\",\"op\":\"sys.getstardata\"}";
		String validate=StuPadFormatCommon.getMd5(param+SECERTKEY, "UTF-8");
		param = param.substring(0, param.length()-1)+ ",\"validate\":\"" + validate + "\"}";
		String res = httpRequestCommon.GetResponseData(url, param);
		return res;
	}
	//广播用户信息
	public String SendBoradcast(String url,String sid,String uid,String frompackage,String toPackage,String content,int overtime)
	throws Exception {
		String param="{\"sid\":\""+sid+"\",\"uid\":\""+uid+"\",\"frompackage\":\""+frompackage+"\",\"topackage\":\""+toPackage+"\",\"content\":\""+content+"\",\"overtime\":"+overtime+",\"op\":\"push.sendboradcasttouser\"}";
		String validate=StuPadFormatCommon.getMd5(param+SECERTKEY, "UTF-8");
		param = param.substring(0, param.length()-1)+ ",\"validate\":\"" + validate + "\"}";
		String res = httpRequestCommon.GetResponseData(url, param);
		return res;
	}
	//组播用户信息
	public String SendMessageToUsers(String url,String sid,String uid,String frompackage,String toPackage,String content,int overtime,String toUids)
			throws Exception {
				String param="{\"sid\":\""+sid+"\",\"uid\":\""+uid+"\",\"frompackage\":\""+frompackage+"\",\"topackage\":\""+toPackage+"\",\"content\":\""+content+"\",\"overtime\":"+overtime+"\",\"userids\":"+toUids+",\"op\":\"push.sendboradcasttouser\"}";
				String validate=StuPadFormatCommon.getMd5(param+SECERTKEY, "UTF-8");
				param = param.substring(0, param.length()-1)+ ",\"validate\":\"" + validate + "\"}";
				String res = httpRequestCommon.GetResponseData(url, param);
				return res;
			}
}
