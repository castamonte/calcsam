package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class t_adjust extends Activity {
	private EditText ar0, t0, t1;
	private TextView ar1, ro0, ro1;
	private Integer i_ar0, i;
	TextView.OnEditorActionListener etListener = new TextView.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView exampleView, int actionId,KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
				recalc();
			}
			return false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t_adjust);

		ar1 = (TextView) findViewById(R.id.textView5);
		ro0 = (TextView) findViewById(R.id.textView7);
		ro1 = (TextView) findViewById(R.id.textView9);

		ar0 = (EditText) findViewById(R.id.editText1);
		ar0.setOnEditorActionListener(etListener);

		t0 = (EditText) findViewById(R.id.editText2);
		t0.setOnEditorActionListener(etListener);
		
		t1 = (EditText) findViewById(R.id.editText3);
		t1.setOnEditorActionListener(etListener);
		
		i = 0;
	}

	private void recalc() {
		Log.i("KeyBoard", "recalc()");
		// i_ar0 = Integer.valueOf(ar0.toString());
		// i_t0 = Integer.parseInt(t0.toString());
		// i_t1 = Integer.parseInt(t1.toString());
		// ar1.setText(String.valueOf(i_ar0));
		/*
		 * i++; ar1.setText(String.valueOf(i));
		 */
	}

}