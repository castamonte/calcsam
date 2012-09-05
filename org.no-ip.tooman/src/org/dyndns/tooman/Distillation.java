package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Distillation extends Activity {
	private EditText amount, t0, t_end;
	private TextView s0, s_end, s_res, v_res;
	private int i_amount, i_t0, i_t_end, i_s_res;
	private double d_v_res, d_s0, d_s_end;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
//		@Override
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
		Log.i("s0", String.format("%.2f", d_s0));
		Log.i("s_end", String.format("%.2f", d_s_end));
		s0.setText(String.format("%.0f", d_s0));
		s_end.setText(String.format("%.0f", d_s_end));
	}

	protected double ttos(int temp) {
		double x;
// зависимость градуса от температуры кипения
		x = 1.340861289354e+04 
			- 4.209916659595e+02 * temp
		    + 4.421702244267e+00 * temp*temp
		    - 1.552843491420e-02 * temp*temp*temp;
		return x;		
	}
	
	protected double ttom(int temp) {
		double x;
// зависимость содержания спирта в парах (% молярный) от температуры кипения		
		x = 1.554571860871e+06
		    - 8.712650943627e+04 * temp
		    + 1.950864596202e+03 * temp * temp
		    - 2.181278765435e+01 * temp * temp * temp
		    + 1.217832635022e-01 * temp * temp * temp * temp
		    - 2.716106516360e-04 * temp * temp * temp * temp * temp;
		return x;
	}
	
	protected double mtot(double mp) {
		double x;
// зависимость температуры кипения от %(молярного) спирта в баке
		x = 9.973680403705e+01
			- 2.664647907634e+00 * mp
			+ 1.995928679785e-01 * mp * mp
			- 8.594968601874e-03 * mp * mp * mp
			+ 2.122481550887e-04 * mp * mp * mp * mp
			- 2.961745380649e-06 * mp * mp * mp * mp * mp
			+ 2.164433737491e-08 * mp * mp * mp * mp * mp * mp
			- 6.424471873789e-11 * mp * mp * mp * mp * mp * mp * mp;
/*		x = 2252.8*(mp*mp*mp*mp*mp*mp)-6621.1*(mp*mp*mp*mp*mp)
			+7684*(mp*mp*mp*mp)-4493.3*(mp*mp*mp)
			+1404.7*(mp*mp)-236.65*mp+100;*/
		return x;
	}
	
	protected double mtom(double mp) {
		double x;
// зависимость % мол. в паре от % мол. в баке
		x = 1.234589531895e+00
			+ 9.285042487341e+00 * mp
			- 7.601202637658e-01 * mp * mp
			+ 3.444521563141e-02 * mp * mp * mp
			- 8.774226026331e-04 * mp * mp * mp * mp
			+ 1.252947859514e-05 * mp * mp * mp * mp * mp
			- 9.325991073677e-08 * mp * mp * mp * mp * mp * mp
			+ 2.811389881792e-10 * mp * mp * mp * mp * mp * mp * mp;
		return x;
	}
	
	protected void disteps() {
		double de=0.789, dw=1, mwe=46.0634, mww=18.0152,
				cpe=2.845, cpw=4.184, hve=761, hvw=2137;
		double ml_e, gr_e, mole_e, ml_w, gr_w, mole_w, mp_e;
// первая итерация
		ml_e = i_amount * d_s0 / 100; gr_e = ml_e * de; mole_e = gr_e / mwe;
		ml_w = i_amount - ml_e; gr_w = ml_w; mole_w = gr_w / mww;
		
	}

}
