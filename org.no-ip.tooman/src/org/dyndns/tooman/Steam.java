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

public class Steam extends Activity {
	private EditText temp, power, sel, fn;
	private TextView vol_sh, mass_sh, m_kgh, m_gs, v_lh, v_mls;
	private int i_temp, i_power;
	private double d_sel, d_fn, d_vol_sh, d_mass_sh, d_m_kgh, d_m_gs, d_v_lh, d_v_mls;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
//		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT
					|| actionId == EditorInfo.IME_NULL
					|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER
					) {
				recalc(v);
				return true;
			}
			return false;
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steam);
    }

    protected void recalc(TextView v) {
    	
    }
    
	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Steam.this, Help.class);
		intent.putExtra("parentActivity", 7);
		startActivityForResult(intent, 0);
		return false;
	}

	protected double ttos(int t) {
		double x;
// зависимость крепости браги от температуры кипения
		x = 1.340861289354e+04 
			- 4.209916659595e+02 * t
		    + 4.421702244267e+00 * t*t
		    - 1.552843491420e-02 * t*t*t;
		return x;		
	}
	
}