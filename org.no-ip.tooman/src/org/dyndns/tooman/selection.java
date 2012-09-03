package org.dyndns.tooman;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class selection extends Activity {
	private EditText ml;
	private TextView lh, mls, ts, t;
	private Button knopka;
	private int i_ml, deciseconds;
	private double d_lh, d_mls;
	TimerTask counter;
	Timer timer;
	boolean tiktak;
	String seconds_i, lh_i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection);
		i_ml = 10;
		d_lh = 1.0;
		tiktak = false;
		deciseconds = 0;
		ml = (EditText) findViewById(R.id.editText1);
		lh = (TextView) findViewById(R.id.textView2);
		mls = (TextView) findViewById(R.id.textView8);
		ts = (TextView) findViewById(R.id.textView5);
		t = (TextView) findViewById(R.id.textView6);
		knopka = (Button) findViewById(R.id.button1);
		knopka.setText(getString(R.string.s_btnStart));
		knopka.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (tiktak) {
					knopka.setText(getString(R.string.s_btnStart));
					tiktak = false;
					ts.setText(seconds_i);
					t.setText("0.0");
					i_ml = (int) Integer.parseInt(ml.getText().toString());
					d_mls = 10.0*i_ml/deciseconds;
					d_lh = 3.6*d_mls;
//					lh.setText(String.valueOf(d_lh));
					lh.setText(String.format("%.3f", d_lh));
					mls.setText(String.format("%.2f", d_mls));
				} else {
					knopka.setText(getString(R.string.s_btnStop));
					tiktak = true;
				};
				deciseconds = 0;
			}
		});

// возможно, лучше использовать postDelayed() 
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}
		}, 0, 100);
	}

	private void TimerMethod() {
		// This method is called directly by the timer
		// and runs in the same thread as the timer.
		// We call the method that will work with the UI
		// through the runOnUiThread method.
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			// This method runs in the same thread as the UI.
			// Do something to the UI thread here
			if (tiktak) {
				deciseconds++;
				seconds_i = String.valueOf(deciseconds/10.0);
				t.setText(seconds_i);
			}
//			Log.i("timer", String.valueOf(deciseconds));
		}
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		timer.cancel();
	}
}
