package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class dilution extends Activity {
	private EditText st0, stResult, vol;
	private TextView vol0, volWater, volResult;
	private int fw, i_st0, i_stResult, i_vol;
	private int i_vol0, i_volWater, i_volResult;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		@Override
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
		setContentView(R.layout.dilution);

		st0 = (EditText) findViewById(R.id.editText1);
		st0.setOnEditorActionListener(etListener);
		stResult = (EditText) findViewById(R.id.editText2);
		stResult.setOnEditorActionListener(etListener);
		vol = (EditText) findViewById(R.id.editText3);
		vol.setOnEditorActionListener(etListener);
		vol0 = (TextView) findViewById(R.id.textView5);
		volWater = (TextView) findViewById(R.id.textView7);
		volResult = (TextView) findViewById(R.id.textView9);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.liquid, android.R.layout.simple_list_item_1);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		spinner.setSelection(0);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getBaseContext(), "Position = " + position,
				// 1000).show();
				fw = position;
				recalc();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void recalc() {
		// Toast.makeText(getBaseContext(), "recalc()", 1000).show();
		i_vol = (int) Integer.parseInt(vol.getText().toString());
		i_st0 = (int) Integer.parseInt(st0.getText().toString());
		i_stResult = (int) Integer.parseInt(stResult.getText().toString());
		switch (fw) {
		case 0:
			// vol - объём самогона
			i_vol0 = i_vol;
			int spiritInSam = i_vol * i_st0;
			i_volResult = spiritInSam / i_stResult;
			i_volWater = i_volResult - i_vol;
			break;
		case 1:
			// vol - объём воды
			i_volWater = i_vol;
			double propSprt = (double) i_st0 / i_stResult - 1;
			i_vol0 = (int) (i_vol / propSprt);
			i_volResult = i_vol0 + i_volWater;
			break;
		case 2:
			// vol - объём результата
			i_volResult = i_vol;
			int spiritInRez = i_vol * i_stResult;
			i_vol0 = spiritInRez / i_st0;
			i_volWater = i_vol - i_vol0;
			break;
		}
		vol0.setText(String.valueOf(i_vol0));
		volWater.setText(String.valueOf(i_volWater));
		volResult.setText(String.valueOf(i_volResult));
	}

}