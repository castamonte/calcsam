package org.dyndns.tooman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class t_adjust extends Activity implements OnClickListener {
	private EditText ar0, t0, t1;
	private TextView ar1, ro0, ro1;
	private Integer i_ar0, i;
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_adjust);
        
        ar0 = (EditText)findViewById(R.id.editText1);
/* пока без обработки изменения поля
        ar0.addTextChangedListener(new TextWatcher(){
        	public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
*/
        t0 = (EditText)findViewById(R.id.editText2);
        t1 = (EditText)findViewById(R.id.editText3);
        ar1 = (TextView)findViewById(R.id.textView5);
        ro0 = (TextView)findViewById(R.id.textView7);
        ro1 = (TextView)findViewById(R.id.textView9);
        i=0;
    }

    @Override
    public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
	    	recalc();
// больше кнопок нету
		}
	}
        
    private void recalc()
    {
    	i_ar0 = Integer.valueOf(ar0.toString());
//    	i_t0 = Integer.parseInt(t0.toString());
//    	i_t1 = Integer.parseInt(t1.toString());
//    	ar1.setText(String.valueOf(i_ar0));
/*
    	i++;
    	ar1.setText(String.valueOf(i));
*/
    }

}