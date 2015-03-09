package com.example.helloworld;

import com.nd.pad.sdk.net.socket.packet.ErrorPacket;
import com.nd.pad.sdk.net.socket.packet.MessagePacket;
import com.nd.pad.sdk.net.socket.packet.Packet;
import com.nd.pad.sdk.net.socket.packet.ResultPacket;
import com.nd.pad.sdk.service.PushWorker;
import com.nd.pad.sdk.widget.CustomToast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class MyReceiver extends BroadcastReceiver {

//	TextView textview = null;
	private static String msg;
	public static String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		Packet packet = (Packet)intent.getExtras().getSerializable(PushWorker.MESSAGE_FROM_PUSH);
		String message ="";
		String content="";
		Intent i = new Intent();
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setClass(context, SecondActivity.class);
		//MainActivity activity = new MainActivity();
		//textview = (TextView)activity.findViewById(R.id.firsttext);
		
			if(packet instanceof MessagePacket) {
				message = "广播";
				MessagePacket msgPacket = (MessagePacket)packet;
				setMsg("广播信息: "+msgPacket.getContentInfo());
				//textview.setText("�û��㲥��"+msgPacket.getContentInfo());
	            CustomToast.showToast(context,"广播: "+msgPacket.getContentInfo(), 30*1000);
	            content = msgPacket.getContentInfo();
			}
		
		if(packet instanceof ErrorPacket){
			ErrorPacket ePacket = (ErrorPacket)packet;
			//textview.setText("�㲥�쳣��"+ePacket.getErrorInfo());
			CustomToast.showToast(context,"BroadcastReceiver ERROR: "+ePacket.getErrorInfo(), 30*1000);
		}else if(packet instanceof ResultPacket){
			ResultPacket rPacket = (ResultPacket)packet;
			message ="BroadcastReceiver RESULT: ";
			if(rPacket.getAck()==1)
			{
				//textview.setText(" �豸����");
			CustomToast.showToast(context,"�豸���� "+rPacket.getAck(), 30*1000);
			}else if(rPacket.getAck()==2)
			{
				//textview.setText(" �û�����");
				CustomToast.showToast(context,"�û����� "+rPacket.getAck(), 30*1000);
			}
			content = "ȷ����Ϣ��"+rPacket.getAck();
		}
		context.startActivity(i);
	}
}
