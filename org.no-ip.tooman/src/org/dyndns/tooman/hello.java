package org.dyndns.tooman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class hello extends Activity {

	final String LOG_TAG = "myLogs";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);

		ListView myListView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.works, android.R.layout.simple_list_item_1);
		myListView.setAdapter(adapter);

		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "	+ id);
				Intent myIntent = null;

				if (position == 0) {myIntent = new Intent(view.getContext(), t_adjust.class);}
				if (position == 1) {myIntent = new Intent(view.getContext(), dilution.class);}
				if (position == 2) {myIntent = new Intent(view.getContext(), t_adjust.class);}
				if (position == 3) {myIntent = new Intent(view.getContext(), t_adjust.class);}
				if (position == 4) {myIntent = new Intent(view.getContext(), t_adjust.class);}
				if (position == 5) {myIntent = new Intent(view.getContext(), realheat.class);}

				startActivity(myIntent);

			}
		});

		myListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(LOG_TAG, "itemSelect: position = " + position + ", id = "
						+ id);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				Log.d(LOG_TAG, "itemSelect: nothing");
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(getApplication()).inflate(R.menu.menu, menu);
		return (super.onPrepareOptionsMenu(menu));
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.dilution:
			Toast.makeText(this, "Разведение", Toast.LENGTH_SHORT).show();
			intent.setClass(this, dilution.class);
			startActivity(intent);
			break;
		case R.id.mixing:
			Toast.makeText(this, "Смешение", Toast.LENGTH_SHORT).show();
			intent.setClass(this, mixing.class);
			startActivity(intent);
			// finish();
			break;
		case R.id.steam:
			Toast.makeText(this, "Пар", Toast.LENGTH_SHORT).show();
			intent.setClass(this, steam.class);
			startActivity(intent);
			break;
		case R.id.distillation:
			Toast.makeText(this, "Перегонка", Toast.LENGTH_SHORT).show();
			intent.setClass(this, distillation.class);
			startActivity(intent);
			break;
		case R.id.t_adjust:
			Toast.makeText(this, "Температурный пересчёт", Toast.LENGTH_SHORT)
					.show();
			intent.setClass(this, t_adjust.class);
			startActivity(intent);
			break;
		case R.id.density:
			Toast.makeText(this, "Плотность", Toast.LENGTH_SHORT).show();
			intent.setClass(this, density.class);
			startActivity(intent);
			break;
		}
		return (super.onOptionsItemSelected(item));
	}

}