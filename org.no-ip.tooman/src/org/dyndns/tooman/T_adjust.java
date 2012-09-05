package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.dyndns.tooman.Desterm;

public class T_adjust extends Activity {
	private EditText ar0, t0, t1;
	private TextView ar1, ro0, ro1;
	private Integer i_ar0, i_t0, i_t1;
	
	
	TextView.OnEditorActionListener etListener = new TextView.OnEditorActionListener() {
//		@Override
		public boolean onEditorAction(TextView exampleView, int actionId,
				KeyEvent event) {
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
		
		
	}

	private void recalc() {
		Log.i("KeyBoard", "recalc()");
		try {
			i_ar0 = (int) Integer.parseInt(ar0.getText().toString());
			if (i_ar0 < 0 || i_ar0 > 96) {
				Toast.makeText(this,
						"Спиритуозность должна быть в пределах 0..96 об.%",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			ar0.setText("96");
			ar0.requestFocus();
		}
		try {
			i_t0 = (int) Integer.parseInt(t0.getText().toString());
			if (i_t0 < 0 || i_t0 > 50) {
				Toast.makeText(this,
						"Температура должна быть в пределах 0..100 °С",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			t0.setText("20");
		}
		try {
			i_t1 = (int) Integer.parseInt(t1.getText().toString());
			if (i_t1 < 0 || i_t1 > 50) {
				Toast.makeText(this,
						"Температура должна быть в пределах 0..100 °С",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			t1.setText("20");
		}
		ar1.setText(String.valueOf(i_ar0));
		ro0.setText(String.valueOf(i_t0 * 0.01));
		ro1.setText(String.valueOf(i_t1 * 0.01));
	}
}