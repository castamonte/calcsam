package org.dyndns.tooman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

// done
public class Dilution extends Activity {
	private EditText st0, stResult, vol;
	private TextView vol0, volWater, volResult;
	private int fw, i_st0, i_stResult, i_vol;
	private int i_vol0, i_volWater, i_volResult;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		// @Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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
				this, R.array.liquid, R.layout.spinnerlayout);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		spinner.setSelection(0);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			// @Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				fw = position;
				recalc();
			}

			// @Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void recalc() {
		long id = 0;  
// вводим исходную крепость
		try {
			i_st0 = (int) Integer.parseInt(st0.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "что ж тут разводить-то?";  
            int duration = Toast.LENGTH_LONG;  
            Toast.makeText(context, text, duration).show();  
			st0.setText("1");
		}
		i_st0 = (int) Integer.parseInt(st0.getText().toString());
		st0.setText("" + (int) i_st0);
// вводим желаемую крепость		
		try {
			i_stResult = (int) Integer.parseInt(stResult.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "не, совсем спирт исчезнуть не может";  
            int duration = Toast.LENGTH_LONG;  
            Toast.makeText(context, text, duration).show();  
            stResult.setText("1");
		}
		if (i_stResult==0){
            Context context = getApplicationContext();  
            CharSequence text = "не, совсем спирт исчезнуть не может";  
            int duration = Toast.LENGTH_LONG;  
            Toast.makeText(context, text, duration).show();  
            stResult.setText("1");
		}
		i_stResult = (int) Integer.parseInt(stResult.getText().toString());
		stResult.setText("" + (int) i_stResult);
// вводим объём
		try {
			i_vol = (int) Integer.parseInt(vol.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "даже у сферического коня в вакууме есть объём какой-нибудь";  
            int duration = Toast.LENGTH_LONG;  
            Toast.makeText(context, text, duration).show();  
            vol.setText("1");
		}
		if (i_vol==0){
            Context context = getApplicationContext();  
            CharSequence text = "даже у сферического коня в вакууме есть объём какой-нибудь";  
            int duration = Toast.LENGTH_LONG;  
            Toast.makeText(context, text, duration).show();  
            vol.setText("1");
		}
		i_vol = (int) Integer.parseInt(vol.getText().toString());
		vol.setText("" + (int) i_vol);
// проверка на разводимость
		if (i_stResult > i_st0) {
            Context context = getApplicationContext();  
            CharSequence text = "это уже не разбавление, это дистилляция какая-то!";  
            int duration = Toast.LENGTH_LONG;  
            Toast.makeText(context, text, duration).show();  
		}
		
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

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Dilution.this,Help.class);
		intent.putExtra("parentActivity", 0);
		startActivityForResult(intent, 0);
		return false;
	}

}