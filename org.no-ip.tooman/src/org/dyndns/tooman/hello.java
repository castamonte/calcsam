package org.dyndns.tooman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class hello extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        View t_Button = findViewById(R.id.t_button);
        t_Button.setOnClickListener((OnClickListener) this);

    }

    public void onClick(View v) {
		switch (v.getId()) {
		case R.id.t_button:
		Intent i = new Intent(this, t_adjust.class);
		startActivity(i);
//		break;
		// More buttons go here (if any) ...
		}
	}

    
    public boolean onCreateOptionsMenu(Menu menu) {
            new MenuInflater(getApplication())
                .inflate(R.menu.menu, menu);
            return(super.onPrepareOptionsMenu(menu));
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
//           finish();
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
             Toast.makeText(this, "Температурный пересчёт", Toast.LENGTH_SHORT).show();
             intent.setClass(this, t_adjust.class);
             startActivity(intent);
             break;
         case R.id.density:
             Toast.makeText(this, "Плотность", Toast.LENGTH_SHORT).show();
             intent.setClass(this, density.class);
             startActivity(intent);
             break;
       }
       return(super.onOptionsItemSelected(item));
     }

}