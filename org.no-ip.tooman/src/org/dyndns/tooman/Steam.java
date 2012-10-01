package org.dyndns.tooman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Steam extends Activity {
	private EditText temp, power, f_s_value;
	private TextView vol_sh, mass_sh, m_kgh, m_gs, v_lh, v_mls, s_f_title, s_f_value;
	private ToggleButton toggle; 
	private int i_temp, i_power;
	private double d_f_s_value, d_s_f_value, d_vol_sh, d_mass_sh, d_m_kgh, d_m_gs, d_v_lh, d_v_mls;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		// @Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT
					|| actionId == EditorInfo.IME_NULL
					|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
				recalc();
				return true;
			}
			return false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.steam);
		temp = (EditText) findViewById(R.id.editText1);
		temp.setOnEditorActionListener(etListener);
		power = (EditText) findViewById(R.id.editText2);
		power.setOnEditorActionListener(etListener);
		f_s_value = (EditText) findViewById(R.id.editText3);
		f_s_value.setOnEditorActionListener(etListener);
		toggle = (ToggleButton) findViewById(R.id.toggleButton1);
		vol_sh = (TextView) findViewById(R.id.textView3);
		mass_sh = (TextView) findViewById(R.id.textView5);
		m_kgh = (TextView) findViewById(R.id.textView8);
		m_gs = (TextView) findViewById(R.id.textView10);
		v_lh = (TextView) findViewById(R.id.textView12);
		v_mls = (TextView) findViewById(R.id.textView14);
		s_f_title = (TextView) findViewById(R.id.textView15);
		s_f_value = (TextView) findViewById(R.id.textView16);
		d_m_kgh = 3.77;
	}

	protected void recalc() {
		if (test()) {
			d_vol_sh = temp_to_volp(i_temp);
			d_mass_sh = volp_to_masp(d_vol_sh);
			d_m_kgh = 3.6 * i_power
					/ (8.40 * d_mass_sh + 22.56 * (100 - d_mass_sh));
			d_m_gs = d_m_kgh / 3.6;
			d_v_lh = (d_m_kgh * d_vol_sh / 78.93 + d_m_kgh / 100
					* (100 - d_vol_sh));
			d_v_mls = d_v_lh / 3.6;

			if (toggle.isChecked()) {
			// задан отбор, считаем ФЧ
				if (d_f_s_value != 0) {
					d_s_f_value = (d_v_lh - d_f_s_value) / d_f_s_value;
					s_f_value.setText(String.format("%.2f", d_s_f_value));
				} else {
					s_f_value.setText("∞");
				}
			} else {
			// задано ФЧ, считаем отбор
				d_s_f_value = d_v_lh / (1 + d_f_s_value);
				s_f_value.setText(String.format("%.2f", d_s_f_value));
			}
		}

		vol_sh.setText(String.format("%.1f", d_vol_sh));
		mass_sh.setText(String.format("%.1f", d_mass_sh));
		m_kgh.setText(String.format("%.2f", d_m_kgh));
		m_gs.setText(String.format("%.2f", d_m_gs));
		v_lh.setText(String.format("%.2f", d_v_lh));
		v_mls.setText(String.format("%.2f", d_v_mls));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Steam.this, Help.class);
		intent.putExtra("parentActivity", 3);
		startActivityForResult(intent, 0);
		return false;
	}

	public boolean test() {
		// нормализуем вводимые значения и проверяем количество отбора
		boolean flag;
		flag = true;
		// вводим температуру кипения
		try {
			i_temp = (int) Integer.parseInt(temp.getText().toString());
		} catch (NumberFormatException ex) {
			CharSequence text = "а температура в кубе какая?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			temp.setText("88");
		}
		if (i_temp < 79) {
			CharSequence text = "предполагаем, что давление нормальное и термометр не врёт..";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			temp.setText("79");
		}
		i_temp = (int) Integer.parseInt(temp.getText().toString());

		// вводим мощность ТЭНа
		try {
			i_power = (int) Integer.parseInt(power.getText().toString());
		} catch (NumberFormatException ex) {
			CharSequence text = "введите мощность ТЭНа";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			power.setText("1");
		}
		i_power = (int) Integer.parseInt(power.getText().toString());

		// вводим скорость отбора или флегмово число
		try {
			d_f_s_value = Float.parseFloat(f_s_value.getText().toString());
		} catch (NumberFormatException ex) {
			f_s_value.setText("0");
		}
		if ((d_f_s_value > d_v_lh) & (toggle.isChecked())) {
//			CharSequence text = "больше, чем сконденсировалось, не отобрать";
//			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			d_f_s_value = d_v_lh;
			f_s_value.setText(String.format("%.2f", d_v_lh));
		}
		d_f_s_value = Float.parseFloat(f_s_value.getText().toString());

		return flag;
	}

	protected double temp_to_volp(int t) {
		double x;
		// зависимость крепости браги от температуры кипения
		x = 1.340861289354e+04 - 4.209916659595e+02 * t + 4.421702244267e+00
				* t * t - 1.552843491420e-02 * t * t * t;
		return x;
	}

	protected double volp_to_masp(double vp) {
		double x;
		// зависимость массовой доли спирта от объёмной
		x = -0.13412 + 0.83749 * vp - 0.00116 * vp * vp + 0.0000276728 * vp
				* vp * vp;
		return x;
	}
	
	public void onToggleClicked(View view) {
	    boolean on = ((ToggleButton) view).isChecked();
	    if (on) {
			s_f_title.setText(getString(R.string.steam_fc));
	    } else {
			s_f_title.setText(getString(R.string.steam_otbor));
	    }
	    recalc();
	}
}