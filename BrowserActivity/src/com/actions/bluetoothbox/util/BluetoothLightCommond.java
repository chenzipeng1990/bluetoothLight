package com.actions.bluetoothbox.util;

import java.util.Arrays;

import android.util.Log;

import com.actions.ibluz.manager.BluzManager;
import com.actions.ibluz.manager.BluzManagerData;
import com.actions.ibluz.manager.IGlobalManager;
import com.actions.ibluz.manager.BluzManagerData.FuncMode;

/**
 * ����������
 * 
 * @author chenzipeng
 *
 */
public class BluetoothLightCommond {

	public static interface FuncMode {
		public static int LIGHT = 0x00;// ��FuncMode.A2DP ��ֵһ��
	}

	int type = BluzManagerData.CommandType.SET;
	int id = 0x81;
	// ����othterdata
	byte[] otherdate = new byte[4];// 4��btye

	// color
	public int r;
	public int g;
	public int b;
	public int rMax;
	public int gMax;
	public int bMax;
	public int rMin = 10;
	public int gMin = 10;
	public int bMin = 10;
	// ������ɫ����
	public int rBrightMax = 10;
	public int gBrightMax = 10;
	public int bBrightMax = 10;
	// ����
	int mode = ON;
	// ģʽ��λ
	int modeValue = 1;

	// param �ı�־
	final int modeFlag = 0x01;
	final int rFlag = 0x02;
	final int gFlag = 0x03;
	final int bFlag = 0x04;

	/**
	 * mode ֵ
	 */
	// ����ģʽ
	final public static int FLASH = 0x02;
	final public static int STROBE = 0x03;
	final public static int FADE = 0x04;
	final public static int SMOOTH = 0x05;
	// ����ֵ
	final public static int ON = 0x01;
	final public static int OFF = 0x00;

	protected int bright = 10;

	/**
	 * ����ģʽ
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getModeValue() {
		return modeValue;
	}

	public void setModeValue(int modeValue) {
		this.modeValue = modeValue;
	}

	/**
	 * ��������
	 */
	public void BrightnessUp() {
		setBright(getBright() + 1);
	}

	/**
	 * ��������
	 */
	public void BrightnessDown() {
		setBright(getBright() - 1);
	}

	/**
	 * �������ȵȼ�
	 * 
	 * @param bright
	 *            0~10
	 */
	public void setBright(int bright) {
		if (bright >= 0 && bright <= 10) {
			this.bright = bright;
		}
		buildRGB();
	}

	public int getBright() {
		return this.bright;
	}

	/**
	 * �������ȼ�����ɫֵ
	 */
	public void buildRGB() {

		int tmpr = rBrightMax == 0 ? 0 : rMin + (rMax - rMin) / rBrightMax * (Math.min(getBright(), rBrightMax));
		int tmpg = gBrightMax == 0 ? 0 : gMin + (gMax - gMin) / gBrightMax * (Math.min(getBright(), gBrightMax));
		int tmpb = bBrightMax == 0 ? 0 : bMin + (bMax - bMin) / bBrightMax * (Math.min(getBright(), bBrightMax));
		setColor(tmpr, tmpg, tmpb);
	}

	/**
	 * ������ɫ������ȡֵ��ΧΪ0~100
	 * 
	 * @param r
	 *            ��
	 * @param g
	 *            ��
	 * @param b
	 *            ��
	 */
	private void setColor(int r, int g, int b) {
		this.r = Math.min(100, r);
		this.g = Math.min(100, g);
		this.b = Math.min(100, b);
	}

	/**
	 * ����
	 * 
	 * @param isOn
	 */
	public void setOn(boolean isOn) {
		mode = isOn ? ON : OFF;
	}

	public boolean isOn() {
		return OFF != mode;
	}

	/**
	 * ��������
	 * 
	 * @param globalManager
	 */
	public void sendCommond(IGlobalManager globalManager) {
		int commandKey = BluzManager.buildKey(type, id);
		int modeParam = buildModeParam(mode, modeValue);
		// �������� ����Ŀ飬ÿ��ռ2λ���ֱ��� 1.���ر�־��2.����ֵ��3.R��־,4Rֵ
		int arg1 = buildParam(modeFlag, modeParam, rFlag, r);
		// �������� ����Ŀ飬ÿ��ռ2λ���ֱ��� 1.G��־��2.Gֵ��3.B��־,4.Bֵ
		int arg2 = buildParam(gFlag, g, bFlag, b);
		byte[] data = otherdate;
		Log.i("BluetoothLightCommond", "COMMOND:" + Integer.toHexString(commandKey));
		Log.i("BluetoothLightCommond", "ARG1:" + Integer.toHexString(arg1));
		Log.i("BluetoothLightCommond", "ARG2:" + Integer.toHexString(arg2));
		globalManager.sendCustomCommand(commandKey, arg1, arg2, data);
	}

	/**
	 * ����ģʽ������
	 * 
	 * ������λʮ������ FF
	 * 
	 * @param mode
	 * @param value
	 * @return
	 */
	private int buildModeParam(int mode, int value) {
		StringBuffer tmpBuffer = new StringBuffer();
		tmpBuffer.append(convertToHex(mode, 1)).append(convertToHex(value, 1));
		return Integer.parseInt(tmpBuffer.toString(), 16);
	}

	/**
	 * ����4�����һ������
	 * 
	 * @param a1
	 * @param a2
	 * @param a3
	 * @param a4
	 * @return
	 */
	private int buildParam(int a1, int a2, int a3, int a4) {
		StringBuffer arg1Buffer = new StringBuffer();
		// ƴ�Ӹ���λ
		arg1Buffer.append(convertToHex(a1)).append(convertToHex(a2)).append(convertToHex(a3)).append(convertToHex(a4));
		// תΪʮ������
		return Integer.parseInt(arg1Buffer.toString(), 16);
	}

	/**
	 * ת�� ʮ���� Ϊ2λ ʮ��������
	 * 
	 * @param i
	 * @return
	 */
	private String convertToHex(int i) {
		if (i > 0xFF) {
			throw new IllegalArgumentException("i must be < 0xFF");
		}
		return convertToHex(i, 2);
	}

	private String convertToHex(int i, int num) {
		return padLeft(Integer.toHexString(i), num);
	}

	/**
	 * ��0
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	private String padLeft(String s, int length) {
		byte[] bs = new byte[length];
		byte[] ss = s.getBytes();
		Arrays.fill(bs, (byte) (48 & 0xff));
		System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
		return new String(bs);
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// ���λ
		targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ
		targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ
		targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�
		return targets;
	}
}
