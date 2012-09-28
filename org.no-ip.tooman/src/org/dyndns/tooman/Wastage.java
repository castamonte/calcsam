package org.dyndns.tooman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.Toast;
import android.content.Context;

public class Wastage extends Activity {
	private EditText power, vol, t0, t1;
	private TextView time, time_sec, time_nom, waste;
	private Button knopka;
	TimerTask counter;
	Timer timer;
	String s_seconds;
	boolean tiktak;
	private int i_power, i_vol, i_t0, i_t1, i_time, deciseconds;
	private double d_waste, energy, d_time_nom;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wastage);
		power = (EditText) findViewById(R.id.editText0);
		vol = (EditText) findViewById(R.id.editText1);
		t0 = (EditText) findViewById(R.id.editText2);
		t1 = (EditText) findViewById(R.id.editText3);
		time = (TextView) findViewById(R.id.textView10);
		time_sec = (TextView) findViewById(R.id.textView5);
		time_nom = (TextView) findViewById(R.id.textView8);
		waste = (TextView) findViewById(R.id.textView7);

		knopka = (Button) findViewById(R.id.button1);
		knopka.setText(getString(R.string.s_btnStart));
		knopka.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (tiktak) {
					knopka.setText(getString(R.string.s_btnStart));
					tiktak = false;
					time_sec.setText(s_seconds);
					time.setText("0.0");
					power.setEnabled(true);
					vol.setEnabled(true);
					t0.setEnabled(true);
					t1.setEnabled(true);
					report();
				} else {
					if (test()) {
						knopka.setText(getString(R.string.s_btnStop));
						tiktak = true;
						time_sec.setText("*");
						time_nom.setText("");
						waste.setText("*");
						power.setEnabled(false);
						vol.setEnabled(false);
						t0.setEnabled(false);
						t1.setEnabled(false);
					}
				}
				;
				deciseconds = 0;
			}
		});

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}
		}, 0, 100);
	}

	private void TimerMethod() {
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			if (tiktak) {
				deciseconds++;
				s_seconds = String.valueOf(deciseconds / 10.0);
				time.setText(s_seconds);
			}
			// Log.i("timer", String.valueOf(deciseconds));
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	public boolean test() {
		boolean flag;
		flag = true;
		// вводим мощность ТЭНа
		try {
			i_power = (int) Integer.parseInt(power.getText().toString());
		} catch (NumberFormatException ex) {
			Context context = getApplicationContext();
			CharSequence text = "ТЭН сгорел?";
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			power.setText("1");
			flag = false;
		}
		i_power = (int) Integer.parseInt(power.getText().toString());

		// вводим объём воды
		try {
			i_vol = (int) Integer.parseInt(vol.getText().toString());
		} catch (NumberFormatException ex) {
			Context context = getApplicationContext();
			CharSequence text = "водички надо бы плеснуть..";
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			vol.setText("1");
			flag = false;
		}
		i_vol = (int) Integer.parseInt(vol.getText().toString());

		// вводим начальную температуру
		try {
			i_t0 = (int) Integer.parseInt(t0.getText().toString());
		} catch (NumberFormatException ex) {
			Context context = getApplicationContext();
			CharSequence text = "какую воду заливаем?";
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			t0.setText("0");
			flag = false;
		}
		if (i_t0 > 100) {
			CharSequence text = "температура воды выше 100°C ?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
					.show();
			t0.setText("100");
			flag = false;
		}
		i_t0 = (int) Integer.parseInt(t0.getText().toString());

		// вводим конечную температуру
		try {
			i_t1 = (int) Integer.parseInt(t1.getText().toString());
		} catch (NumberFormatException ex) {
			Context context = getApplicationContext();
			CharSequence text = "какую воду заливаем?";
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			t1.setText("0");
			flag = false;
		}
		if (i_t1 > 100) {
			CharSequence text = "температура воды выше 100°C ?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
					.show();
			t1.setText("100");
			flag = false;
		}
		i_t1 = (int) Integer.parseInt(t1.getText().toString());

		if (i_t0 >= i_t1) {
			CharSequence text = "думаешь, при нагревании вода остывает?";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
					.show();
			flag = false;
		}
		return flag;
	}

	public void report() {
		String t_nom;
		int m_nom, s_nom, ds_nom;
		m_nom = 0;
		energy = 4180 * i_vol * (i_t1 - i_t0);
		d_time_nom = 10 * energy / i_power; // в децисекундах
		d_waste = 100 * (deciseconds - d_time_nom) / deciseconds;
		t_nom = "   ";
		m_nom = (int) d_time_nom / 600;
		s_nom = (int) (d_time_nom - m_nom * 600) / 10;
		if (m_nom > 0) {
			t_nom = t_nom + String.valueOf(m_nom) + "'";
			t_nom = t_nom + String.valueOf(s_nom) + "\"";
		} else {
			ds_nom=(int) (d_time_nom - m_nom * 600 - s_nom*10);
			t_nom = t_nom + String.valueOf(s_nom) +"."+ String.valueOf(ds_nom) + "\"";
		}

		if (d_time_nom > deciseconds) {
			CharSequence text = "При таких параметрах вода не может согреться быстрее чем за "
					+ t_nom + "!!";
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
		} else {
			time_nom.setText(t_nom);
			waste.setText(String.format("%.0f", d_waste));
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Wastage.this, Help.class);
		intent.putExtra("parentActivity", 7);
		startActivityForResult(intent, 0);
		return false;
	}
}
