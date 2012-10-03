package org.dyndns.tooman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;

public class Density extends Activity {
	private CheckBox chx;
	private EditText density, volume, mass, temp;
	private TextView textvol, textmass, spirit;
	private boolean flag = false;
	private double d_density, d_volume=250, d_mass=238, d_temp, d_spirit;
	private double sprt0, sprt1;

	EditText.OnEditorActionListener etListener = new EditText.OnEditorActionListener() {
		// @Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT
					|| actionId == EditorInfo.IME_NULL
					|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
				recalc();
				return true;
			}
			return false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.density);
		density = (EditText) findViewById(R.id.editText1);
		density.setOnEditorActionListener(etListener);
		volume = (EditText) findViewById(R.id.editText2);
		volume.setOnEditorActionListener(etListener);
		mass = (EditText) findViewById(R.id.editText3);
		mass.setOnEditorActionListener(etListener);
		temp = (EditText) findViewById(R.id.editText4);
		temp.setOnEditorActionListener(etListener);
		textvol = (TextView) findViewById(R.id.textView2);
		textmass = (TextView) findViewById(R.id.textView3);
		spirit = (TextView) findViewById(R.id.textView6);
		chx = (CheckBox) findViewById(R.id.checkBox1);
		chx.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					textvol.setVisibility(View.GONE);
					textmass.setVisibility(View.GONE);
					volume.setVisibility(View.GONE);
					mass.setVisibility(View.GONE);
					density.setEnabled(true);
					chx.setText(getString(R.string.dens_areo));
					density.setText(String.valueOf(Math.round(sprt20gr(Double.parseDouble(density.getText().toString())))));
					flag = true;
				} else {
					textvol.setVisibility(View.VISIBLE);
					textmass.setVisibility(View.VISIBLE);
					volume.setVisibility(View.VISIBLE);
					mass.setVisibility(View.VISIBLE);
					density.setEnabled(false);
					chx.setText(getString(R.string.dens_density));
					density.setText(String.format("%.2f", 1000*d_mass/d_volume));
					flag = false;
				}
				recalc();
			}
		});

	}

	public void recalc() {
		if (flag) {
			// вводим показания ареометра
			try {
				d_density = dens20gr(Double.parseDouble(density.getText().toString()));
			} catch (NumberFormatException e) {
				density.setText("40");
			}
			if (d_density < 772 || d_density > 999) {
				Toast.makeText(this, "ареометр?", Toast.LENGTH_SHORT).show();
				density.setText("40");
			}
			d_density = dens20gr(Double.parseDouble(density.getText().toString()));
		} else {
			// высчитываем плотность через массу и объём
			try {
				d_volume = (double) Double.parseDouble(volume.getText().toString());
			} catch (NumberFormatException e) {
				volume.setText("250");
			}
			d_volume = (double) Double.parseDouble(volume.getText().toString());
			try {
				d_mass = (double) Double.parseDouble(mass.getText().toString());
			} catch (NumberFormatException e) {
				mass.setText("238");
			}
			d_mass = (double) Double.parseDouble(mass.getText().toString());
			// проверяем плотность
			d_density = 1000*d_mass/d_volume;
			if (d_density < 772 || d_density > 999) {
				volume.setText("250"); d_volume=250;
				mass.setText("238"); d_mass=238;
				d_density=951;
			}
			density.setText(String.format("%.2f", d_density));
		}
		// вводим температуру
		try {
			d_temp = Double.parseDouble(temp.getText().toString());
		} catch (NumberFormatException e) {
			temp.setText("20");
		}
		if (d_temp < 0 || d_temp > 40) {
			Toast.makeText(this, "температура?", Toast.LENGTH_SHORT).show();
			temp.setText("20");
		}
		d_temp = Double.parseDouble(temp.getText().toString());
		if (d_temp>0 && d_temp<=5) {
			sprt0=sprt0gr(d_density);
			sprt1=sprt5gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(5-d_temp)/5;
		}
		if (d_temp>5 && d_temp<=10) {
			sprt0=sprt5gr(d_density);
			sprt1=sprt10gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(10-d_temp)/5;
		}
		if (d_temp>10 && d_temp<=15) {
			sprt0=sprt10gr(d_density);
			sprt1=sprt15gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(15-d_temp)/5;
		}
		if (d_temp>15 && d_temp<=20) {
			sprt0=sprt15gr(d_density);
			sprt1=sprt20gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(20-d_temp)/5;
		}
		if (d_temp>20 && d_temp<=25) {
			sprt0=sprt20gr(d_density);
			sprt1=sprt25gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(25-d_temp)/5;
		}
		if (d_temp>25 && d_temp<=30) {
			sprt0=sprt25gr(d_density);
			sprt1=sprt30gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(30-d_temp)/5;
		}
		if (d_temp>30 && d_temp<=35) {
			sprt0=sprt35gr(d_density);
			sprt1=sprt40gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(35-d_temp)/5;
		}
		if (d_temp>35 && d_temp<=40) {
			sprt0=sprt35gr(d_density);
			sprt1=sprt40gr(d_density);
			d_spirit=sprt1-(sprt1-sprt0)*(40-d_temp)/5;
		}
//Toast.makeText(this, String.format("%.2f", sprt0), Toast.LENGTH_SHORT).show();
		spirit.setText(String.valueOf(Math.round(d_spirit)));
		
	}

// http://arachnoid.com/polysolve/
/*
Correlation coefficient (r^2) > 0.997
Standard error < 1.6	
*/
	public double sprt0gr(double dens) {
		// спиртуозность при 0°C в зависимости от плотности (г/л)
		double sprt=
				9.4660006831758728e+003
				-3.2490274316261612e+001*dens
				+3.7833010769115986e-002*dens*dens
				-1.4811249716819562e-005*dens*dens*dens;
		return sprt;
	}

	public double sprt5gr(double dens) {
		// спиртуозность при 5°C в зависимости от плотности (г/л)
		double sprt=
				8.0045888314279546e+003
				-2.7516392327169388e+001*dens
				+3.2201255463918790e-002*dens*dens
				-1.2692055035183658e-005*dens*dens*dens;
		return sprt;
	}

	public double sprt10gr(double dens) {
		// спиртуозность при 10°C в зависимости от плотности (г/л)
		double sprt=
				5.9505434853936331e+003
				-2.0528555407192883e+001*dens
				+2.4293820637331948e-002*dens*dens
				-9.7179056110183932e-006*dens*dens*dens;
		return sprt;
	}

	public double sprt15gr(double dens) {
		// спиртуозность при 15°C в зависимости от плотности (г/л)
		double sprt=
				4.7939629355497273e+003
				-1.6561189524523211e+001*dens
				+1.9767554601274547e-002*dens*dens
				-8.0027234091549166e-006*dens*dens*dens;
		return sprt;
	}

	public double sprt20gr(double dens) {
		// спиртуозность при 20°C в зависимости от плотности (г/л)
		double sprt=
				3.8759145289794733e+003
				-1.3397497247047635e+001*dens
				+1.6141719995962714e-002*dens*dens
				-6.6230789546780938e-006*dens*dens*dens;
		return sprt;
	}

	public double sprt25gr(double dens) {
		// спиртуозность при 25°C в зависимости от плотности (г/л)
		double sprt=
				3.1518362526544356e+003
				-1.0888312119841549e+001*dens
				+1.3250206378416904e-002*dens*dens
				-5.5174164929973381e-006*dens*dens*dens;
		return sprt;
	}

	public double sprt30gr(double dens) {
		// спиртуозность при 30°C в зависимости от плотности (г/л)
		double sprt=
				2.9510720183500862e+003
				-1.0150398724145409e+001*dens
				+1.2349507050740800e-002*dens*dens
				-5.1549651909939897e-006*dens*dens*dens;
		return sprt;
	}

	public double sprt35gr(double dens) {
		// спиртуозность при 35°C в зависимости от плотности (г/л)
		double sprt=
				2.1995035595251302e+003
				-7.5566817233207360e+000*dens
				+9.3745958182030464e-003*dens*dens
				-4.0231632630326795e-006*dens*dens*dens;
		return sprt;
	}

	public double sprt40gr(double dens) {
		// спиртуозность при 40°C в зависимости от плотности (г/л)
		double sprt=
				1.8367913742517046e+003
				-6.2787580798394744e+000*dens
				+7.8774235633883079e-003*dens*dens
				-3.4424261420505988e-006*dens*dens*dens;
		return sprt;
	}

	public double dens20gr(double areo) {
		// плотность (г/л) в зависимости от показаний ареометра при 20°C
		double dens=
				9.9677271786184656e+002
				-1.0637498940584493e+000*areo
				-1.0615009196346444e-003*areo*areo
				-8.8748661288637711e-005*areo*areo*areo;
		return dens;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		Intent intent = new Intent(Density.this, Help.class);
		intent.putExtra("parentActivity", 4);
		startActivityForResult(intent, 0);
		return false;
	}

}