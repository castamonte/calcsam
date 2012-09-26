package org.dyndns.tooman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Steam extends Activity {
	private EditText temp, power, sel, fn;
	private TextView vol_sh, mass_sh, m_kgh, m_gs, v_lh, v_mls;
	private int i_temp, i_power;
	private double d_sel, d_fn, d_vol_sh, d_mass_sh, d_m_kgh, d_m_gs, d_v_lh, d_v_mls;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		// @Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT
					|| actionId == EditorInfo.IME_NULL
					|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
				recalc(v);
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
		sel = (EditText) findViewById(R.id.editText3);
		sel.setOnEditorActionListener(etListener);
		fn = (EditText) findViewById(R.id.editText4);
		fn.setOnEditorActionListener(etListener);
		vol_sh = (TextView) findViewById(R.id.textView3);
		mass_sh = (TextView) findViewById(R.id.textView5);
		m_kgh = (TextView) findViewById(R.id.textView8);
		m_gs = (TextView) findViewById(R.id.textView10);
		v_lh = (TextView) findViewById(R.id.textView12);
		v_mls = (TextView) findViewById(R.id.textView14);
		d_m_kgh = 3.77;
	}

	protected void recalc(TextView v) {
		if (test()) {
/*
			i_temp = (int) Integer.parseInt(temp.getText().toString());
			i_power = (int) Integer.parseInt(power.getText().toString());
			d_sel = Float.parseFloat(sel.getText().toString());
			d_fn = Float.parseFloat(fn.getText().toString());
*/
			d_vol_sh = temp_to_volp(i_temp);
			d_mass_sh = volp_to_masp(d_vol_sh);
			d_m_kgh = 3.6 * i_power
					/ (8.40 * d_mass_sh + 22.56 * (100 - d_mass_sh));
			d_m_gs = d_m_kgh / 3.6;
			d_v_lh = (d_m_kgh * d_vol_sh / 78.93 + d_m_kgh / 100
					* (100 - d_vol_sh));
			d_v_mls = d_v_lh / 3.6;

			if (v.getId() == sel.getId()) {
				d_fn = (d_v_lh - d_sel) / d_sel;
			} else if (v.getId() == fn.getId()) {
				d_sel = d_v_lh / (1 + d_fn);
			} else {
				d_sel = d_v_lh;
				d_fn = 0;
			}

			vol_sh.setText(String.format("%.1f", d_vol_sh));
			mass_sh.setText(String.format("%.1f", d_mass_sh));
			m_kgh.setText(String.format("%.2f", d_m_kgh));
			m_gs.setText(String.format("%.2f", d_m_gs));
			v_lh.setText(String.format("%.2f", d_v_lh));
			v_mls.setText(String.format("%.2f", d_v_mls));
			sel.setText(String.format("%.2f", d_sel));
			fn.setText(String.format("%.1f", d_fn));
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Steam.this, Help.class);
		intent.putExtra("parentActivity", 7);
		startActivityForResult(intent, 0);
		return false;
	}

	public boolean test() {
		boolean flag;
		flag = true;
		// вводим температуру кипения
		try {
			i_temp = (int) Integer.parseInt(temp.getText().toString());
		} catch (NumberFormatException ex) {
			CharSequence text = "а температура в кубе какая?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			temp.setText("88");
			flag = false;
		}
		if (i_temp > 100) {
			CharSequence text = "температура в кубе выше 100°C ?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			temp.setText("88");
			flag = false;
		}
		if (i_temp < 79) {
			CharSequence text = "или термометр неисправен, или ацетон  в кубе..";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			temp.setText("88");
			flag = false;
		}
		i_temp = (int) Integer.parseInt(temp.getText().toString());

		// вводим мощность ТЭНа
		try {
			i_power = (int) Integer.parseInt(power.getText().toString());
		} catch (NumberFormatException ex) {
			CharSequence text = "ТЭН сгорел?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			power.setText("1");
			flag = false;
		}
		i_power = (int) Integer.parseInt(power.getText().toString());

		// вводим скорость отбора
		try {
			d_sel = Float.parseFloat(sel.getText().toString());
		} catch (NumberFormatException ex) {
//			CharSequence text = "совсем не капает?";
//			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			sel.setText("0.01");
			flag = false;
		}
		if (d_sel <= 0) {
//			CharSequence text = "ФЧ устремилось к ∞";
//			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			sel.setText("0.01");
			flag = false;
		}
		if (d_sel > d_v_lh) {
//			CharSequence text = "больше, чем сконденсировалось, не отобрать";
//			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			d_sel = d_v_lh;
			sel.setText(String.format("%.2f", d_v_lh));
			flag = false;
		}
		d_sel = Float.parseFloat(sel.getText().toString());
		
		//вводим флегмовое число
		try {
			d_fn = Float.parseFloat(fn.getText().toString());
		} catch (NumberFormatException ex) {
//			CharSequence text = "ФЧ какое?";
//			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
			fn.setText("0.01");
			flag = false;
		}
		d_fn = Float.parseFloat(fn.getText().toString());

//		return flag;
		return true;
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
}