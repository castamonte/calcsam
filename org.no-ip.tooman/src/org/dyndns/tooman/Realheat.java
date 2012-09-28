package org.dyndns.tooman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Realheat extends Activity {
	private EditText nomu, nomp, realu;
	private TextView realp;
	private int i_nomu, i_nomp, i_realu, i_realp;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
//		@Override
		public boolean onEditorAction(TextView v, int actionId,	KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT
					|| actionId == EditorInfo.IME_NULL
					|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER
					) {
				recalc();
				return true;
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
// вводим номинальное напряжение
		try {
			i_nomu = (int) Integer.parseInt(nomu.getText().toString());
		} catch (NumberFormatException e) {
			nomu.setText("220");
			nomu.requestFocus();
		}
		if (i_nomu < 0 || i_nomu > 380) {
			Toast.makeText(
					this, "Удалось достать ТЭН на "+Integer.parseInt(nomu.getText().toString())+" вольт? Могу только поздравить :)",
					Toast.LENGTH_SHORT).show();
		}
		i_nomu = (int) Integer.parseInt(nomu.getText().toString());

// вводим мощность ТЭНа
		try {
			i_nomp = (int) Integer.parseInt(nomp.getText().toString());
		} catch (NumberFormatException e) {
			nomp.setText("2000");
			nomp.requestFocus();
		}
		if (i_nomp < 0 || i_nomp > 5000) {
			Toast.makeText(
					this, "ТЭН мощнее полудесятка киловатт? Уважаю..",
					Toast.LENGTH_SHORT).show();
		}
		i_nomp = (int) Integer.parseInt(nomp.getText().toString());

// вводим реальное напряжение		
		try {
			i_realu = (int) Integer.parseInt(realu.getText().toString());
		} catch (NumberFormatException e) {
			realu.setText("220");
			realu.requestFocus();
		}
		if (i_realu < 0 || i_realu > 380) {
			Toast.makeText(this,
					"прямо-таки больше 380 В в розетке? :)",
					Toast.LENGTH_SHORT).show();
		}
		i_realu = (int) Integer.parseInt(realu.getText().toString());
		
		double dv = 1.0 * i_realu / i_nomu;
		i_realp = (int) (dv * dv * i_nomp);
		realp.setText(String.valueOf(i_realp));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Realheat.this,Help.class);
		intent.putExtra("parentActivity", 1);
		startActivityForResult(intent, 0);
		return false;
	}

}
