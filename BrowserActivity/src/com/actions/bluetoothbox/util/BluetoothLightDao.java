package com.actions.bluetoothbox.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存上次蓝牙灯的数据
 * @author chenzipeng
 *
 */
public class BluetoothLightDao {
	private final String LIGHT_PREFRENCE_NAME = "BlueLight";

	private final String KEY_R_MIN = "R_MIN";
	private final String KEY_G_MIN = "R_MIN";
	private final String KEY_B_MIN = "R_MIN";
	private final String KEY_R_MAX = "R_MAX";
	private final String KEY_G_MAX = "G_MAX";
	private final String KEY_B_MAX = "B_MAX";
	private final String KEY_R_BRIGHT_MAX = "R_BRIGHT_MAX";
	private final String KEY_G_BRIGHT_MAX = "G_BRIGHT_MAX";
	private final String KEY_B_BRIGHT_MAX = "B_BRIGHT_MAX";
	private final String KEY_BRIGHT = "BRIGHT";
	private final String KEY_MODE = "MODE";
	
	private static BluetoothLightDao mInstance;
	private SharedPreferences mPrefrences;

	private BluetoothLightDao(Context context){
		mPrefrences = context.getSharedPreferences(LIGHT_PREFRENCE_NAME, Context.MODE_PRIVATE);
	}
	
	public static BluetoothLightDao getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new BluetoothLightDao(context);
		}
		return mInstance;
	}
	
	/**
	 * 保存当前蓝牙灯状态
	 * @param commond
	 */
	public void save(BluetoothLightCommond commond){
		Editor e = mPrefrences.edit();
		e.putInt(KEY_R_MIN, commond.rMin);
		e.putInt(KEY_G_MIN, commond.gMin);
		e.putInt(KEY_B_MIN, commond.bMin);
		e.putInt(KEY_R_MAX, commond.rMax);
		e.putInt(KEY_G_MAX, commond.gMax);
		e.putInt(KEY_B_MAX, commond.bMax);
		e.putInt(KEY_B_MAX, commond.bMax);
		e.putInt(KEY_R_BRIGHT_MAX, commond.rBrightMax);
		e.putInt(KEY_G_BRIGHT_MAX, commond.gBrightMax);
		e.putInt(KEY_B_BRIGHT_MAX, commond.bBrightMax);
		e.putInt(KEY_BRIGHT, commond.getBright());
		e.putInt(KEY_MODE, commond.mode);
		e.commit();
	};
	
	public BluetoothLightCommond loadBluetoothLightCommond(){
		BluetoothLightCommond c = new BluetoothLightCommond();
		return loadBluetoothLightCommond(c);
	}
	
	/**
	 * 加载 颜色亮度
	 * @param commond
	 * @return
	 */
	public BluetoothLightCommond loadBluetoothLightCommond(BluetoothLightCommond commond){
		commond.rMin = mPrefrences.getInt(KEY_R_MIN, 10);
		commond.gMin = mPrefrences.getInt(KEY_G_MIN, 10);
		commond.bMin = mPrefrences.getInt(KEY_B_MIN, 10);
		commond.rMax = mPrefrences.getInt(KEY_R_MAX, 100);
		commond.gMax = mPrefrences.getInt(KEY_G_MAX, 100);
		commond.bMax = mPrefrences.getInt(KEY_B_MAX, 100);
		commond.rBrightMax = mPrefrences.getInt(KEY_R_BRIGHT_MAX, 10);
		commond.gBrightMax = mPrefrences.getInt(KEY_G_BRIGHT_MAX, 10);
		commond.bBrightMax = mPrefrences.getInt(KEY_B_BRIGHT_MAX, 10);
		commond.setBright(mPrefrences.getInt(KEY_BRIGHT, 10));
		commond.mode = mPrefrences.getInt(KEY_MODE, BluetoothLightCommond.OFF);
		return commond;
	}
}
