package org.dyndns.tooman;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;
import android.widget.AdapterView;

// done
public class Thanks extends Activity {
	private Spinner razdel;
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thanks);
		webView = (WebView) findViewById(R.id.webView);
		razdel = (Spinner) this.findViewById(R.id.spinner1);
		razdel.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					webView.loadUrl("file:///android_asset/thanks.html");
					break;
				case 1:
					webView.loadUrl("file:///android_asset/spirit.html");
					break;
				}

			}
//			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		finish();
		return false;
	}
}
