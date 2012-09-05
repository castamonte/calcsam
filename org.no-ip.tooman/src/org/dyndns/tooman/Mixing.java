package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

public class Mixing extends Activity {
	private EditText st1, st2, v1, v2;
	private TextView str, vr;
	private int i_st1, i_st2, i_str, i_v1, i_v2, i_vr;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
//		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			Log.i("IME", Integer.toString(actionId));
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT) {
				recalc();
			}
			return false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mixing);
		i_st1 = 60;
		i_v1 = 200;
		i_st2 = 40;
		i_v2 = 500;

		st1 = (EditText) findViewById(R.id.editText1);
		st1.setOnEditorActionListener(etListener);

		v1 = (EditText) findViewById(R.id.editText2);
		v1.setOnEditorActionListener(etListener);

		st2 = (EditText) findViewById(R.id.editText3);
		st2.setOnEditorActionListener(etListener);

		v2 = (EditText) findViewById(R.id.editText4);
		v2.setOnEditorActionListener(etListener);

		str = (TextView) findViewById(R.id.textView6);
		vr = (TextView) findViewById(R.id.textView8);
	}

	protected void recalc() {
		try {
			i_st1 = (int) Integer.parseInt(st1.getText().toString());
			if (i_st1 < 0 || i_st1 > 97) {
				Toast.makeText(this, "Крепость выше 96% ? Не верится что-то..",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			st1.setText("40");
			st1.requestFocus();
		}

		try {
			i_v1 = (int) Integer.parseInt(v1.getText().toString());
			if (i_v1 < 0 || i_v1 > 9999) {
				Toast.makeText(
						this,
						"С такими объёмами можно купить и более точную программу..",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			v1.setText("40");
			v1.requestFocus();
		}

		try {
			i_st2 = (int) Integer.parseInt(st2.getText().toString());
			if (i_st2 < 0 || i_st2 > 97) {
				Toast.makeText(this, "Крепость выше 96% ? Не верится что-то..",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			st2.setText("40");
			st2.requestFocus();
		}

		try {
			i_v2 = (int) Integer.parseInt(v2.getText().toString());
			if (i_v2 < 0 || i_v2 > 9999) {
				Toast.makeText(
						this,
						"С такими объёмами можно купить и более точную программу..",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			st1.setText("40");
			v2.requestFocus();
		}

		i_vr = i_v1 + i_v2;
		vr.setText(String.valueOf(i_vr));
		i_str = (int) (((i_st1 * i_v1) + (i_st2 * i_v2)) / i_vr);
		str.setText(String.valueOf(i_str));

	}

}