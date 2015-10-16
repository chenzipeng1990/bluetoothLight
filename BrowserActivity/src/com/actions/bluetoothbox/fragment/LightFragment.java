package com.actions.bluetoothbox.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actions.bluetoothbox.R;
import com.actions.bluetoothbox.app.BrowserActivity;
import com.actions.bluetoothbox.util.BluetoothLightDao;
import com.actions.bluetoothbox.util.BluetoothLightCommond;
import com.actions.bluetoothbox.util.VerticalSeekBar;
import com.actions.ibluz.a.b.m;


public class LightFragment extends SherlockFragment {
	private static final String TAG = LightFragment.class.getSimpleName();

	private BrowserActivity mActivity;
	private View mView; 
	private SeekBar mModeValueSeekbar;
	private Menu mMenu = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		mView = inflater.inflate(R.layout.fragment_light, container, false);
		mActivity = (BrowserActivity) getActivity();
		init();
		return mView;
	}

	public void init() {
		mModeValueSeekbar = (SeekBar) mView.findViewById(R.id.control_modeValue_seekbar);
		
		bindCommand(R.id.control_brightness_up);
		bindCommand(R.id.control_brightness_down);
		bindCommand(R.id.control_trun_on);
		bindCommand(R.id.control_trun_off);
		bindCommand(R.id.control_color_R);
		bindCommand(R.id.control_color_R_1);
		bindCommand(R.id.control_color_R_2);
		bindCommand(R.id.control_color_R_3);
		bindCommand(R.id.control_color_R_4);
		bindCommand(R.id.control_color_G);
		bindCommand(R.id.control_color_G_1);
		bindCommand(R.id.control_color_G_2);
		bindCommand(R.id.control_color_G_3);
		bindCommand(R.id.control_color_G_4);
		bindCommand(R.id.control_color_B);
		bindCommand(R.id.control_color_B_1);
		bindCommand(R.id.control_color_B_2);
		bindCommand(R.id.control_color_B_3);
		bindCommand(R.id.control_color_B_4);
		bindCommand(R.id.control_color_W);
		bindCommand(R.id.control_flash);
		bindCommand(R.id.control_strobe);
		bindCommand(R.id.control_fade);
		bindCommand(R.id.control_smoot);
		Log.v(TAG, "init finish!");

	}

	
	private void bindCommand(final int viewId) {
		View view = mView.findViewById(viewId);
		view.setOnClickListener(mButtonClickListenner);
	}
	
	private BluetoothLightCommond mBluetoothLightCommond = new BluetoothLightCommond();
	/**
	 * 按钮触发器
	 */
	private OnClickListener mButtonClickListenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				mBluetoothLightCommond = BluetoothLightDao.getInstance(mActivity).loadBluetoothLightCommond(mBluetoothLightCommond);
				
				switch (v.getId()) {
				case R.id.control_trun_on:
					mBluetoothLightCommond.setOn(true);
					break;
				case R.id.control_trun_off:
					mBluetoothLightCommond.setOn(false);
					break;
				case R.id.control_color_R:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 0;
					mBluetoothLightCommond.bMax = 0;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 0;
					mBluetoothLightCommond.bMin = 0;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 0;
					mBluetoothLightCommond.bBrightMax = 0;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_R_1:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 25;
					mBluetoothLightCommond.bMax = 0;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 5;
					mBluetoothLightCommond.bMin = 0;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 4;
					mBluetoothLightCommond.bBrightMax = 0;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_R_2:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 50;
					mBluetoothLightCommond.bMax = 0;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 0;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 8;
					mBluetoothLightCommond.bBrightMax = 0;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_R_3:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 80;
					mBluetoothLightCommond.bMax = 0;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 30;
					mBluetoothLightCommond.bMin = 0;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 8;
					mBluetoothLightCommond.bBrightMax = 0;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_R_4:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 0;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 0;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 0;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_G:
					mBluetoothLightCommond.rMax = 0;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 0;
					mBluetoothLightCommond.rMin = 0;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 0;
					mBluetoothLightCommond.rBrightMax = 0;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 0;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_G_1:
					mBluetoothLightCommond.rMax = 0;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 25;
					mBluetoothLightCommond.rMin = 0;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 5;
					mBluetoothLightCommond.rBrightMax = 0;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 4;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_G_2:
					mBluetoothLightCommond.rMax = 0;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 50;
					mBluetoothLightCommond.rMin = 0;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 0;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 8;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_G_3:
					mBluetoothLightCommond.rMax = 0;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 80;
					mBluetoothLightCommond.rMin = 0;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 30;
					mBluetoothLightCommond.rBrightMax = 0;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 8;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_G_4:
					mBluetoothLightCommond.rMax = 0;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 0;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 0;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_B:
					mBluetoothLightCommond.rMax = 0;
					mBluetoothLightCommond.gMax = 0;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 0;
					mBluetoothLightCommond.gMin = 0;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 0;
					mBluetoothLightCommond.gBrightMax = 0;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_B_1:
					mBluetoothLightCommond.rMax = 25;
					mBluetoothLightCommond.gMax = 0;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 5;
					mBluetoothLightCommond.gMin = 0;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 4;
					mBluetoothLightCommond.gBrightMax = 0;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_B_2:
					mBluetoothLightCommond.rMax = 50;
					mBluetoothLightCommond.gMax = 0;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 0;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 8;
					mBluetoothLightCommond.gBrightMax = 0;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_B_3:
					mBluetoothLightCommond.rMax = 80;
					mBluetoothLightCommond.gMax = 0;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 30;
					mBluetoothLightCommond.gMin = 0;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 8;
					mBluetoothLightCommond.gBrightMax = 0;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_B_4:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 0;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 0;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 0;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_color_W:
					mBluetoothLightCommond.rMax = 100;
					mBluetoothLightCommond.gMax = 100;
					mBluetoothLightCommond.bMax = 100;
					mBluetoothLightCommond.rMin = 10;
					mBluetoothLightCommond.gMin = 10;
					mBluetoothLightCommond.bMin = 10;
					mBluetoothLightCommond.rBrightMax = 10;
					mBluetoothLightCommond.gBrightMax = 10;
					mBluetoothLightCommond.bBrightMax = 10;
					mBluetoothLightCommond.buildRGB();
					break;
				case R.id.control_brightness_up:
					mBluetoothLightCommond.BrightnessUp();
					break;
				case R.id.control_brightness_down:
					mBluetoothLightCommond.BrightnessDown();
					break;
				case R.id.control_flash:
					mBluetoothLightCommond.setMode(BluetoothLightCommond.FLASH);
					mBluetoothLightCommond.setModeValue(getSelectedModeValue());
					break;
				case R.id.control_fade:
					mBluetoothLightCommond.setMode(BluetoothLightCommond.FADE);
					mBluetoothLightCommond.setModeValue(getSelectedModeValue());
					break;
				case R.id.control_smoot:
					mBluetoothLightCommond.setMode(BluetoothLightCommond.SMOOTH);
					mBluetoothLightCommond.setModeValue(getSelectedModeValue());
					break;
				case R.id.control_strobe:
					mBluetoothLightCommond.setMode(BluetoothLightCommond.STROBE);
					mBluetoothLightCommond.setModeValue(getSelectedModeValue());
					break;
				default:
					break;
				}
				
				BluetoothLightDao.getInstance(mActivity).save(mBluetoothLightCommond);
				mBluetoothLightCommond.sendCommond(mActivity.getIGlobalManager());
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
				String msg = getActivity().getString(R.string.notice_control_error);
				Toast.makeText(getActivity(),msg ,Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private int getSelectedModeValue(){
		//因为 档值是从1~10 所以+1
		return mModeValueSeekbar.getProgress()+1;
	}
	
	private void switchFragment() {
		LightFragment fragment = new LightFragment();
		mActivity.replaceFragment(fragment, SlideoutMenuFragment.FRAGMENT_TAG_LIGHT);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		mMenu = menu;
		inflater.inflate(R.menu.soundsetting_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		mActivity.menuItemSelected(mMenu, item.getItemId());
		return super.onOptionsItemSelected(item);
	}
}
