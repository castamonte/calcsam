package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class dilution extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dilution);
    
        final EditText edtext = (EditText)findViewById(R.id.editText1);
        final Button button = (Button) findViewById(R.id.button);
    
        button.setOnClickListener(new OnClickListener() {
			@Override
		    public void onClick(View v) {
				edtext.setText("Здравствуй, друг");
				Toast.makeText(dilution.this, "ипануццо!", Toast.LENGTH_SHORT).show();
		    }

        });
    }	
}