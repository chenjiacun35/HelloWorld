package com.example.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final String DB_NAME="mydata.db";
	private static final int Version =1;
	public SqliteHelper(Context context)
	{
		super(context,DB_NAME,null,Version);
	}
	public void onCreate(SQLiteDatabase db)
	{
		String createMemSQL = "create table meminfo(id integer primary key autoincrement,";
		createMemSQL = createMemSQL + "totalmem int(11) NOT NULL default '0',";
		createMemSQL = createMemSQL +"totalfreemem int(11) NOT NULL default '0',";
		createMemSQL =	createMemSQL+"memfree int(11) NOT NULL default '0',";
		createMemSQL = createMemSQL +"buffers int(11) NOT NULL default '0',";
		createMemSQL =createMemSQL + "cached	int(11) NOT NULL default '0',";
		createMemSQL = createMemSQL+"createtime datetime NOT NULL default '0')";
		db.execSQL(createMemSQL);
		String createCpuSQL = "create table cpuinfo(id integer primary key autoincrement,";
		createCpuSQL = createCpuSQL + "totaluse int(11) NOT NULL default '0',";
		createCpuSQL = createCpuSQL +"useruse int(11) NOT NULL default '0',";
		createCpuSQL =	createCpuSQL+"systemuse int(11) NOT NULL default '0',";
		createCpuSQL = createCpuSQL +"iow int(11) NOT NULL default '0',";
		createCpuSQL =createCpuSQL + "irq	int(11) NOT NULL default '0',";
		createCpuSQL =createCpuSQL + "createtime datetime NOT NULL default '0')";
		db.execSQL(createCpuSQL);
	}
	
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
	{
		
	}
}
