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

public class Fruit extends Activity {
	private Spinner fruit_name;
	private EditText sugar_percent, wish_spirit, juice_volume;
	private TextView real_spirit, add_sugar, add_water, braga_volume;
	private int i_wish_spirit, i_juice_volume, i_sugar_percent;
	private double d_real_spirit, d_add_sugar, d_add_water, d_braga_volume, out;
	private boolean from_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit);
		fruit_name = (Spinner) this.findViewById(R.id.spinner1);
		fruit_name.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				from_list = true;
				i_sugar_percent = getResources().getIntArray(R.array.fruit_sugar)[fruit_name.getSelectedItemPosition()];
				recalc();
			}
//			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		sugar_percent = (EditText) findViewById(R.id.editText1);
		sugar_percent.setOnEditorActionListener(etListener);
		wish_spirit = (EditText) findViewById(R.id.editText2);
		wish_spirit.setOnEditorActionListener(etListener);
		juice_volume = (EditText) findViewById(R.id.editText3);
		juice_volume.setOnEditorActionListener(etListener);
		real_spirit = (TextView) findViewById(R.id.textView3);
		add_sugar = (TextView) findViewById(R.id.textView7);
		add_water = (TextView) findViewById(R.id.textView9);
		braga_volume = (TextView) findViewById(R.id.textView11);
        
    }

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		// @Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT
					|| actionId == EditorInfo.IME_NULL
					|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER
					) {
				from_list = false;
				recalc();
				return true;
			}
			return false;
		}
	};

	private void recalc() {
		out=0.9;
		
		if (from_list) {
			i_sugar_percent = getResources().getIntArray(R.array.fruit_sugar)[fruit_name.getSelectedItemPosition()];
			sugar_percent.setText(String.valueOf(i_sugar_percent));
		} else {
// вводим сахаристость сока
			try {
				i_sugar_percent = (int) Integer.parseInt(sugar_percent.getText().toString());
			} catch (NumberFormatException ex) {
	            Context context = getApplicationContext();  
	            CharSequence text = "если сахара нет, то чему бродить?";  
	            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
				i_sugar_percent = getResources().getIntArray(R.array.fruit_sugar)[fruit_name.getSelectedItemPosition()];
				sugar_percent.setText(String.valueOf(i_sugar_percent));
			}
			i_sugar_percent = (int) Integer.parseInt(sugar_percent.getText().toString());
		}

// вводим желаемую крепость
		try {
			i_wish_spirit = (int) Integer.parseInt(wish_spirit.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "неужели компот будем делать?";  
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
            wish_spirit.setText("1");
		}
		i_wish_spirit = (int) Integer.parseInt(wish_spirit.getText().toString());

// вводим объём сока
		try {
			i_juice_volume = (int) Integer.parseInt(juice_volume.getText().toString());
		} catch (NumberFormatException ex) {
            Context context = getApplicationContext();  
            CharSequence text = "ну хоть литр надо отжать..";  
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();  
            juice_volume.setText("1");
		}
		i_juice_volume = (int) Integer.parseInt(juice_volume.getText().toString());
		
		d_real_spirit=0.6404*i_sugar_percent*out;
		if (i_wish_spirit>d_real_spirit) {
			d_add_sugar=(i_wish_spirit-d_real_spirit)*i_juice_volume*0.01562/out;
		} else {
			d_add_sugar=0;
		}
		d_add_water=d_real_spirit*i_juice_volume/i_wish_spirit-i_juice_volume;
		if (d_add_water<0) {
			d_add_water=0;
		}
		d_braga_volume=i_juice_volume+d_add_water+d_add_sugar*0.725;

		sugar_percent.setText(String.valueOf(i_sugar_percent));
		real_spirit.setText(String.format("%.1f", d_real_spirit));
		add_sugar.setText(String.format("%.2f", d_add_sugar));
		add_water.setText(String.format("%.2f", d_add_water));
		braga_volume.setText(String.format("%.2f", d_braga_volume));
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Fruit.this,Help.class);
		intent.putExtra("parentActivity", 5);
		startActivityForResult(intent, 0);
		return false;
	}

}
