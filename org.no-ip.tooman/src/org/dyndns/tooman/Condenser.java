package org.dyndns.tooman;

import android.os.Bundle;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.EditText;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;

public class Condenser extends Activity {
	private Spinner material, diameter;
	private EditText power, leak, flow, tw0;
	private TextView dlina, ts, work;
	private double uvb, utk, wgm, uts, utsk, tp0, tp1, tp, tw1, dt, e1, e2, e, ktps, sh;
	private double d_leak, d_power, d_work, d_tw0, d_flow, d_ts;
	private int i_diam, i_dlina;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.condenser);
		material = (Spinner) this.findViewById(R.id.spinner1);
		material.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				recalc();
			}
//			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		diameter = (Spinner) this.findViewById(R.id.spinner2);
		diameter.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				recalc();
			}
//			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		power = (EditText) findViewById(R.id.editText1);
		power.setOnEditorActionListener(etListener);
		leak = (EditText) findViewById(R.id.editText2);
		leak.setOnEditorActionListener(etListener);
		flow = (EditText) findViewById(R.id.editText3);
		flow.setOnEditorActionListener(etListener);
		tw0 = (EditText) findViewById(R.id.editText4);
		tw0.setOnEditorActionListener(etListener);
		dlina = (TextView) findViewById(R.id.textView6);
		ts = (TextView) findViewById(R.id.textView9);
		work = (TextView) findViewById(R.id.textView11);
		uvb = 0.987; // удельный вес браги, г/мл
		utk = 2153; // удельная теплота конденсации, Дж/г
		uts = 3.593; // удельная теплоёмкость самогона, Дж/(г*°С)
		tp0 = 93.2; // температура насыщенного пара в кубе в начале перегонки, °С
		tp1 = 99.0; // температура насыщенного пара в кубе в конце перегонки, °С

	}

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		// @Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			Log.i("IME", Integer.toString(actionId));
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

	private void recalc() {
		String s_material = getResources().getStringArray(R.array.condens_mtl)[material
				.getSelectedItemPosition()];
		String s_diameter = getResources().getStringArray(R.array.condens_d)[diameter
				.getSelectedItemPosition()];
// вводим мощность нагревателя
		try {
			d_power = Float.parseFloat(power.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "как-то надо нагревать всё-таки";  
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
			power.setText("1500");
		}
		d_power = Float.parseFloat(power.getText().toString());
		
// вводим процент потерь		
		try {
			d_leak = Float.parseFloat(leak.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "не бывает, чтоб совсем не было потерь";  
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
			leak.setText("10");
		}
		d_leak = Float.parseFloat(leak.getText().toString());

// вводим расход воды		
		try {
			d_flow = Float.parseFloat(flow.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "и охлаждать как-то надо";  
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
			flow.setText("1000");
		}
		d_flow = Float.parseFloat(flow.getText().toString());

// вводим начальную температуру воды		
		try {
			d_tw0 = Float.parseFloat(tw0.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "считаем, что охлаждаем водой, а не льдом";  
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
			tw0.setText("20");
		}
		d_tw0 = Float.parseFloat(tw0.getText().toString());
		
// диаметр холодильника - проверки не нужны, поелику спиннер
		i_diam = (int) Integer.parseInt(s_diameter);

		d_flow=d_flow/1000; // мл в л
		wgm = d_power * (1 - d_leak / 100) / utk * 60 * 1.303; // скорость конденсации, г/мин
		Log.i("wgm", String.valueOf(wgm));
		d_work = wgm / uvb * 60 / 1000; // скорость конденсации, л/час
		if (material.getSelectedItemPosition() == 0) {
			ktps = 1.29; // медь
		} else {
			ktps = 1.23; // нержавейка
		} // коэфф. теплопередачи стенки, кал/(см^2*мин*°С)
			// теплопроводность композитной стенки холодильника+флегмы, W/(m^2*K)
		utsk = uts / 4.1868; // удельная теплоёмкость самогона, кал/(г*°С)
		tp = 0.5 * (tp1 + tp0); // средняя температура насыщенного пара, °С
		tw1 = d_power / 1000 * (1 - d_leak / 100.0) * 14 / d_flow + d_tw0; // температура воды на выходе, °С
		dt = (tw1 - d_tw0) / Math.log((tp - d_tw0) / (tp - tw1)); // средняя разность температур между паром и хладагентом, °С
		d_ts = (uts * wgm * (tp - dt) / 1000 / 60 * 14 / dt + tw1); // средняя температура самого на выходе из холодильника
		e1 = d_power * (1 - d_leak / 100.0) * 60 / 4.1868; // теплосъём на конденсации, кал/мин
		e2 = utsk * wgm * (tp - d_ts); // теплосъём на охлаждении, кал/мин
		e = e1 + e2; // суммарный теплосъём
		sh = (1 + d_leak / 100.0) * e / ktps / dt; // необходимая площадь охлаждения с запасом, cм^2
//		Log.i("sh", String.valueOf(sh));
		i_dlina = (int) ((int) sh / (Math.PI * (i_diam - 2 * 1.0) / 10)); // длина холодильника

		dlina.setText(String.valueOf(i_dlina));
		ts.setText(String.format("%.0f", d_ts));
		work.setText(String.format("%.3f", d_work));

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Condenser.this,Help.class);
		intent.putExtra("parentActivity", 6);
		startActivityForResult(intent, 0);
		return false;
	}

}
