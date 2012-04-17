package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class distillation extends Activity {
	private EditText amount, t0, t_end;
	private TextView s0, s_end, s_res, v_res;
	private int i_amount, i_t0, i_t_end, i_s_res;
	private double d_v_res, d_s0, d_s_end;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//			Log.i("IME", Integer.toString(actionId));
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT) {
				recalc();
			}
			return false;
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distillation);
		amount = (EditText) findViewById(R.id.editText1);
		amount.setOnEditorActionListener(etListener);
		t0 = (EditText) findViewById(R.id.editText2);
		t0.setOnEditorActionListener(etListener);
		t_end = (EditText) findViewById(R.id.editText3);
		t_end.setOnEditorActionListener(etListener);
		s0 = (TextView) findViewById(R.id.textView3);
		s_end = (TextView) findViewById(R.id.textView6);
   }

	protected void recalc() {
		try {
			i_amount = (int) Integer.parseInt(amount.getText().toString());
			i_t0 = (int) Integer.parseInt(t0.getText().toString());
			i_t_end = (int) Integer.parseInt(t_end.getText().toString());
			if (i_t_end <= i_t0) {
				Toast.makeText(this, "В нашем мире температура куба при нагревании повышается..",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
		}
		d_s0 = ttos(i_t0);
		d_s_end = ttos(i_t_end);
		Log.i("s0", String.format("%.1f", d_s0));
		Log.i("s_end", String.format("%.1f", d_s_end));
		s0.setText(String.format("%.1f", d_s0));
		s_end.setText(String.format("%.1f", d_s_end));
	}

	protected double ttos(int spirit) {
		double x;
		x = 1.340861289354e+04 
			- 4.209916659595e+02 * spirit
		    + 4.421702244267e+00 * spirit*spirit
		    - 1.552843491420e-02 * spirit*spirit*spirit;
		return x;		
	}
}
