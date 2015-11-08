package com.actions.bluetoothbox.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
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
	private TextView mControlModeView;

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
		mControlModeView = (TextView) mView.findViewById(R.id.control_mode_value);

		// bindCommand(R.id.control_brightness_up);
		// bindCommand(R.id.control_brightness_down);
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

		setSeekbarMode(SEEKBAR_MODE_BRIGHT);
		mModeValueSeekbar.setOnSeekBarChangeListener(mControlModeChangeListener);
		Log.v(TAG, "init finish!");

	}

	private void bindCommand(final int viewId) {
		View view = mView.findViewById(viewId);
		view.setOnClickListener(mButtonClickListenner);
	}

	private final int SEEKBAR_MODE_BRIGHT = 0;
	private final int SEEKBAR_MODE_MODE = 1;
	private int mSeekBarMode = SEEKBAR_MODE_BRIGHT;

	private void setSeekbarMode(int mode) {
		mSeekBarMode = mode;
		initSeekBar();
	}

	/**
	 * 根据状态和数据更新seekbar
	 */
	private void initSeekBar() {
		//更新seekbar值，从存储的数据中获取
		mBluetoothLightCommond = BluetoothLightDao.getInstance(mActivity).loadBluetoothLightCommond(mBluetoothLightCommond);
		//什么模式下
		if(mSeekBarMode == SEEKBAR_MODE_BRIGHT){
			// 更新文字提示
			String barLabel = getString(R.string.control_mode_bright);
			mControlModeView.setText(barLabel);
			//设置亮度值
			mModeValueSeekbar.setProgress(mBluetoothLightCommond.getBright());
		}else{
			// 更新文字提示
			String barLabel = getString(R.string.control_mode_mode);
			mControlModeView.setText(barLabel);
			//设置模式值
			// 因为书中存储的档位1~10，但是seekbar中的值范围为0~9 所以要-1
			mModeValueSeekbar.setProgress(mBluetoothLightCommond.getModeValue() -1);
		}
	}

	private OnSeekBarChangeListener mControlModeChangeListener = new OnSeekBarChangeListener() {

		private boolean changed = false;

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (!changed) {
				return;
			}

			Log.i(TAG, "control mode onProgressChanged " + seekBar.getProgress());
			int value = getSelectedModeValue();
			
			mBluetoothLightCommond = BluetoothLightDao.getInstance(mActivity).loadBluetoothLightCommond(mBluetoothLightCommond);

			switch (mSeekBarMode) {
			case SEEKBAR_MODE_BRIGHT:
				mBluetoothLightCommond.setBright(value);
				break;
			case SEEKBAR_MODE_MODE:
				mBluetoothLightCommond.setModeValue(value);
				break;
			default:
				break;
			}

			sendCommond();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			changed = false;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (!fromUser) {
				return;
			}
			changed = true;
			Log.i("AAA","============"+progress);
		}
	};

	private BluetoothLightCommond mBluetoothLightCommond = new BluetoothLightCommond();
	/**
	 * 按钮触发器
	 */
	private OnClickListener mButtonClickListenner = new OnClickListener() {

		@Override
		public void onClick(View v) {

			mBluetoothLightCommond = BluetoothLightDao.getInstance(mActivity).loadBluetoothLightCommond(mBluetoothLightCommond);

			switch (v.getId()) {
			case R.id.control_trun_on:
				mBluetoothLightCommond.setOn(true);
				break;
			case R.id.control_trun_off:
				mBluetoothLightCommond.setOn(false);
				break;
			case R.id.control_color_R:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_R_1:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_R_2:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_R_3:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_R_4:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);

				break;
			case R.id.control_color_G:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_G_1:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_G_2:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_G_3:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_G_4:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_B:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_B_1:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_B_2:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_B_3:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_B_4:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			case R.id.control_color_W:
				setSeekbarMode(SEEKBAR_MODE_BRIGHT);
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
				mBluetoothLightCommond.setMode(BluetoothLightCommond.ON);
				break;
			// case R.id.control_brightness_up:
			// mBluetoothLightCommond.BrightnessUp();
			// break;
			// case R.id.control_brightness_down:
			// mBluetoothLightCommond.BrightnessDown();
			// break;
			case R.id.control_flash:
				setSeekbarMode(SEEKBAR_MODE_MODE);
				mBluetoothLightCommond.setMode(BluetoothLightCommond.FLASH);
				mBluetoothLightCommond.setModeValue(getSelectedModeValue());
				break;
			case R.id.control_fade:
				setSeekbarMode(SEEKBAR_MODE_MODE);
				mBluetoothLightCommond.setMode(BluetoothLightCommond.FADE);
				mBluetoothLightCommond.setModeValue(getSelectedModeValue());
				break;
			case R.id.control_smoot:
				setSeekbarMode(SEEKBAR_MODE_MODE);
				mBluetoothLightCommond.setMode(BluetoothLightCommond.SMOOTH);
				mBluetoothLightCommond.setModeValue(getSelectedModeValue());
				break;
			case R.id.control_strobe:
				setSeekbarMode(SEEKBAR_MODE_MODE);
				mBluetoothLightCommond.setMode(BluetoothLightCommond.STROBE);
				mBluetoothLightCommond.setModeValue(getSelectedModeValue());
				break;
			default:
				break;
			}
			sendCommond();

		}
	};

	private void sendCommond() {
		try {
			BluetoothLightDao.getInstance(mActivity).save(mBluetoothLightCommond);
			mBluetoothLightCommond.sendCommond(mActivity.getIGlobalManager());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			String msg = getActivity().getString(R.string.notice_control_error);
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	private int getSelectedModeValue() {
		// 因为 档值是从1~10 所以+1
		return mModeValueSeekbar.getProgress() + 1;
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
