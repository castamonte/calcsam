package org.dyndns.tooman;

import org.dyndns.tooman.ExternalDbOpenHelper;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class t_adjust extends Activity {
	private EditText ar0, t0, t1;
	private TextView ar1, ro0, ro1;
	private Integer i_ar0, i_t0, i_t1;
	private static final String DB_NAME = "alcotable.db";
    //Хорошей практикой является задание имен полей БД константами
	private static final String TABLE_NAME = "tempert";
	private static final String KEY_ID = "_id";
	private static final String CONC_NAME = "conc";
	private SQLiteDatabase database;
	
	TextView.OnEditorActionListener etListener = new TextView.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView exampleView, int actionId,
				KeyEvent event) {
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
		setContentView(R.layout.t_adjust);

		ar1 = (TextView) findViewById(R.id.textView5);
		ro0 = (TextView) findViewById(R.id.textView7);
		ro1 = (TextView) findViewById(R.id.textView9);

		ar0 = (EditText) findViewById(R.id.editText1);
		ar0.setOnEditorActionListener(etListener);

		t0 = (EditText) findViewById(R.id.editText2);
		t0.setOnEditorActionListener(etListener);

		t1 = (EditText) findViewById(R.id.editText3);
		t1.setOnEditorActionListener(etListener);
		
		//Наш ключевой хелпер
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        //Все, база открыта!
	}

	private void recalc() {
		// Log.i("KeyBoard", "recalc()");
		try {
			i_ar0 = (int) Integer.parseInt(ar0.getText().toString());
			if (i_ar0 < 0 || i_ar0 > 96) {
				Toast.makeText(this,
						"Спиритуозность должна быть в пределах 0..96 об.%",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			ar0.setText("96");
			ar0.requestFocus();
		}
		try {
			i_t0 = (int) Integer.parseInt(t0.getText().toString());
			if (i_t0 < 0 || i_t0 > 50) {
				Toast.makeText(this,
						"Температура должна быть в пределах 0..50 °С",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			t0.setText("20");
		}
		try {
			i_t1 = (int) Integer.parseInt(t1.getText().toString());
			if (i_t1 < 0 || i_t1 > 50) {
				Toast.makeText(this,
						"Температура должна быть в пределах 0..50 °С",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			t1.setText("20");
		}
		ar1.setText(String.valueOf(i_ar0));
		ro0.setText(String.valueOf(i_t0 * 0.01));
		ro1.setText(String.valueOf(i_t1 * 0.01));
	}

/*	пример из http://idev.by/android/145/
 * //Извлечение элментов из базы данных
	private void fillFreinds() {
		friends = new ArrayList<String>();
		Cursor friendCursor = database.query(TABLE_NAME,
	                                             new String[] {FRIEND_ID, FRIEND_NAME},
						     null, null,null,null,
	                                             FRIEND_NAME);
			friendCursor.moveToFirst();
			if(!friendCursor.isAfterLast()) {
				do {
					String name = friendCursor.getString(1);
					friends.add(name);
				} while (friendCursor.moveToNext());
			}
			friendCursor.close();
		}
	}*/
}