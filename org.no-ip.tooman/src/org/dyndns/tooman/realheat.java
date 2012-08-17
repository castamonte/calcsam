package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class realheat extends Activity {
	private EditText nomu, nomp, realu;
	private TextView realp;
	private int i_nomu, i_nomp, i_realu, i_realp;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
//		@Override
		public boolean onEditorAction(TextView v, int actionId,	KeyEvent event) {
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
		i_nomu=230;i_nomp=1500;i_realu=220;
		setContentView(R.layout.realheat);

		realp = (TextView) findViewById(R.id.textView5);

		nomu = (EditText) findViewById(R.id.editText1);
		nomu.setOnEditorActionListener(etListener);

		nomp = (EditText) findViewById(R.id.editText2);
		nomp.setOnEditorActionListener(etListener);

		realu = (EditText) findViewById(R.id.editText3);
		realu.setOnEditorActionListener(etListener);
	}

	private void recalc() {
		try {
			i_nomu = (int) Integer.parseInt(nomu.getText().toString());
//			Log.i("nomu", nomu.getText().toString());
			if (i_nomu < 0 || i_nomu > 380) {
				Toast.makeText(
						this,
						"Номинальное напряжение должно быть в пределах 0..380 В",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			nomu.setText("220");
			nomu.requestFocus();
		}
		try {
			i_nomp = (int) Integer.parseInt(nomp.getText().toString());
//			Log.i("nomp", nomp.getText().toString());
			if (i_nomp < 0 || i_nomp > 9999) {
				Toast.makeText(
						this,
						"Номинальная мощность должна быть в пределах 0..9999 Вт",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			nomp.setText("2000");
			nomp.requestFocus();
		}
		try {
			i_realu = (int) Integer.parseInt(realu.getText().toString());
//			Log.i("realu", realu.getText().toString());
			if (i_realu < 0 || i_realu > 380) {
				Toast.makeText(this,
						"Реальное напряжение должно быть в пределах 0..380 В",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			realu.setText("220");
			realu.requestFocus();
		}
		double dv = 1.0 * i_realu / i_nomu;
//		Log.i("dv", Double.toString(dv));
		i_realp = (int) (dv * dv * i_nomp);
		realp.setText(String.valueOf(i_realp));
	}

}
