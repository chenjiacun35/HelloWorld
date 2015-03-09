package com.example.helloworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.android.commands.uiautomator.RunTestCommand;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import com.nd.pad.sdk.api.LocationMapCallBack;
import com.nd.pad.sdk.api.NdMapAPI;
import com.nd.pad.sdk.api.NdPadAPI;
import com.nd.pad.sdk.net.socket.packet.ErrorPacket;
import com.nd.pad.sdk.net.socket.packet.MessagePacket;
import com.nd.pad.sdk.net.socket.packet.Packet;
import com.nd.pad.sdk.net.socket.packet.ResultPacket;
import com.nd.pad.sdk.net.update.OnUpdateCheckListener;
import com.nd.pad.sdk.service.PushWorker;
import com.nd.pad.sdk.util.Tools;
import com.nd.pad.sdk.widget.CustomToast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateUtils;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import stupad.openapi.lib.*;

public class MainActivity extends Activity implements OnClickListener{
	
	TextView textview = null;
	Button pushButton ;
	Button mapButton;
	Button updateButton ;
	Button weatherButton;
	Button yjButton;
	Button starButton;
	Button fillRamButton;
	MyReceiver receiver ;
	NdPadAPI api;
	NdMapAPI mapApi;
	GetNetOpenapiData getdata = new GetNetOpenapiData();
	String url="http://openapi.101.com/handler/syshandler.ashx";
	String pushUrl = "http://openapi.101.com/handler/syshandler.ashx";
	private SQLiteDatabase mSQLiteDB;
	SqliteHelper mSqliteHelper = new SqliteHelper(this); 
//	private static final String DB_NAME ="mydata.db";
	public Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)  {
			
            if(msg.what==99) {
            	textview.setText(String.valueOf(msg.obj));
            	
            }else{
            	CustomToast.showToast(MainActivity.this, String.valueOf(msg.obj), 3000);
            }
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = NdPadAPI.registerApi(this, true);
		setContentView(R.layout.activity_main);
		pushButton = (Button)findViewById(R.id.pushtest);
		mapButton =(Button)findViewById(R.id.maptest);
		updateButton = (Button)findViewById(R.id.updatetest);
		weatherButton = (Button)findViewById(R.id.weathertest);
		yjButton =(Button)findViewById(R.id.yjtest);
		starButton = (Button)findViewById(R.id.starstest);
		textview =(TextView)findViewById(R.id.firsttext);
		fillRamButton =(Button) findViewById(R.id.fillram);
		pushButton.setOnClickListener(this);
		mapButton.setOnClickListener(this);
		updateButton.setOnClickListener(this);
		weatherButton.setOnClickListener(this);
		yjButton.setOnClickListener(this);
		starButton.setOnClickListener(this);
		fillRamButton.setOnClickListener(this);
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onResume()
	{
		super.onResume();
		mapApi = NdMapAPI.getInstance(this, "WuwRHEOsmdUA3bGkwqkESvFD", true);
	}
	@Override
	public void onClick(View v)
	{
		
		
		PackageManager packageManager = getPackageManager();
		File file = new File("/sdcard");
		File[] files=file.listFiles();
		for(File f:files)
		{
			if(f.getName().contains("in.debug"))
			{
				url="http://openapi2.stu.test.nd/handler/syshandler.ashx";
				pushUrl="http://openapi2.stu.test.nd/handler/syshandler.ashx";
			}
		}
		Thread thread =null;
		switch (v.getId()) {
		case R.id.pushtest:
			 thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					RunTestCommand cmd = new RunTestCommand();
					String[] args ={"-e","jars","UiAuto.jar"};
					cmd.run(args);
					
				}
			});
			 thread.start();
			
			break;
		case R.id.maptest:
			Intent intent = new Intent();
			intent=packageManager.getLaunchIntentForPackage("com.nazumi.sdk");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
			
			break;
		case R.id.updatetest:
			
			api.checkSoftUpdate(this, new OnUpdateCheckListener() {
				
				@Override
				public void onPreExecute() {
					Tools.toast(getApplicationContext(), "onPreExecute");
					
				}
				
				@Override
				public void onPostExecute(Boolean hasNewVersion, String msg) {
					
					Tools.toast(getApplicationContext(), "onPostExecute");
					Tools.toast(getApplicationContext(), msg);
				}
			});
			break;
		case R.id.weathertest:
			textview.setText("测试开始");
			//由于http请求在主线程中进行会阻塞主线程，所以要通过异步方式
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					final String weather = NdPadAPI.getWeather(MainActivity.this, "119.319341", "26.097448", "", "", "");
					Message msgMessage = new Message();
					msgMessage.what = 99;
					String res="";
	            	try{
	            	 res =getdata.GetWeather(url,"119.319341", "26.097448", "", "", "");
	            	}catch(Exception e)
	            	{
	            		Log.i("[HTTP]", e.toString());
	            	}
	            	if(res.equals(weather))
	            	{
	            		Log.i("[SDK-TEST]","测试通过");
	            		msgMessage.obj="测试通过";
	            	}else
	            	{
	            		msgMessage.obj="测试失败";
	            	}
	            	//最后通过handler操作应用
					mHandler.sendMessage(msgMessage);
				}
			});
			thread.start();
			break;
		case R.id.yjtest:
			textview.setText("测试开始");
			//由于http请求在主线程中进行会阻塞主线程，所以要通过异步方式
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					String date ="2014-3-14";
					final String yiji = NdPadAPI.getAvoidAndSuitable(MainActivity.this, date);
					Message msgMessage = new Message();
					msgMessage.what = 99;
					String res="";
	            	try{
	            	 res =getdata.GetYiJi(url, date);
	            	}catch(Exception e)
	            	{
	            		Log.i("[HTTP]", e.toString());
	            	}
	            	if(res.equals(yiji))
	            	{
	            		Log.i("[SDK-TEST]","测试通过");
	            		msgMessage.obj="测试通过";
	            	}else
	            	{
	            		msgMessage.obj="测试失败";
	            	}
	            	//最后通过handler操作应用
					mHandler.sendMessage(msgMessage);
				}
			});
			thread.start();
			break;
		case R.id.starstest:
			textview.setText("测试开始");
			//由于http请求在主线程中进行会阻塞主线程，所以要通过异步方式
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					String birthday ="1990-1-14";
					String effectiveDate ="2014-3-15";
					final String stardata = NdPadAPI.getStardata(MainActivity.this, birthday, effectiveDate);
					Message msgMessage = new Message();
					msgMessage.what = 99;
					String res="";
	            	try{
	            	 res =getdata.GetStars(url, birthday, effectiveDate);
	            	}catch(Exception e)
	            	{
	            		Log.i("[HTTP]", e.toString());
	            	}
	            	if(res.equals(stardata))
	            	{
	            		Log.i("[SDK-TEST]","测试通过");
	            		msgMessage.obj="测试通过";
	            	}else
	            	{
	            		msgMessage.obj="测试失败";
	            	}
	            	//最后通过handler操作应用
					mHandler.sendMessage(msgMessage);
				}
			});
			thread.start();
			break;
		case R.id.fillram:
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					mSQLiteDB  = mSqliteHelper.getWritableDatabase();
					MemoryInfo info = new MemoryInfo();
					ActivityManager mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
					String memresult="";
					String cpuresult="";
					String memargs = "/system/bin/cat /proc/meminfo";
					String cpuargs ="top -s cpu -m 10 -n 1";
					while(true)
					{
					try{
					Date mDate = new Date(System.currentTimeMillis());
					ProcessBuilder memcmd = new ProcessBuilder(memargs);
					String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mDate);
					Process process1 = Runtime.getRuntime().exec(memargs);
					mActivityManager.getMemoryInfo(info);
					Process process2 = Runtime.getRuntime().exec(cpuargs);
					InputStream inS1 = process1.getInputStream();
					InputStream inS2 = process2.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(inS2));
					StringBuilder sb = new StringBuilder();
					byte[] re1 = new byte[1024];
					byte[] re2 = new byte[1024];
					while (inS1.read(re1) != -1) {
						memresult = memresult + new String(re1);
					}
					inS1.close();
					while (inS2.read(re2) != -1) {
						cpuresult = cpuresult + new String(re2);
					}
//					while((cpuresult=br.readLine())!=null)
//					{
//						if(cpuresult.trim().length()<1)
//						{
//							continue;
//						}
//						else
//						{
//							sb.append(cpuresult);
//						}
//					}
					inS2.close();
					String[] meminfo =  memresult.split("kB");
					int total =Integer.parseInt(meminfo[0].replace("MemTotal: ", "").trim());
					int memfree = Integer.parseInt(meminfo[1].replace("MemFree:", "").trim());
					int buffers = Integer.parseInt(meminfo[2].replace("Buffers: ", "").trim());
					int cached = Integer.parseInt(meminfo[3].replace("Cached:  ", "").trim());
					int totalFree = memfree+buffers+cached;
					ContentValues cv = new ContentValues();
					cv.put("totalmem", total);
					cv.put("totalfreemem", totalFree);
					cv.put("memfree", memfree);
					cv.put("buffers", buffers);
					cv.put("cached", cached);
					cv.put("createtime", currentTime);
//					mSQLiteDB.insert("meminfo",null, cv);
//					String insert = "insert into meminfo(totalmem,totalfreemem,memfree,buffers,cached,createtime) values("+total+","+totalFree+",0,0,0)";
//					mSQLiteDB.execSQL(insert);
					System.out.println("可用内存："+totalFree/total);
					System.out.println("API获取内存总："+info.totalMem/1024/1024+";可用内存："+info.availMem/1024/1024);
					cv = new ContentValues();
					cv.put("totalmem", (int)info.totalMem/1024/1024);
					cv.put("totalfreemem",(int) info.availMem/1024/1024);
					cv.put("memfree", 0);
					cv.put("buffers", 0);
					cv.put("cached", 0);
					cv.put("createtime", currentTime);
					mSQLiteDB.insert("meminfo",null, cv);
					String[] cpuinfo = cpuresult.replace("\\n", "@").split("@");
					process1.destroy();
					process2.destroy();
					memcmd = null;
					
					}
					catch (IOException e )
					{
						e.printStackTrace();
					}
				}
				}
			});
			thread.start();
			break;
		default:
			break;
		}
	}

}
