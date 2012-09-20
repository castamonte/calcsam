package org.dyndns.tooman;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

// done
public class Thanks extends Activity {
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thanks);
		webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl("file:///android_asset/thanks.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		finish();
		return false;
	}
}
