package org.dyndns.tooman;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class Help extends Activity {
	private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
		webView = (WebView) findViewById(R.id.webView1);
		int pa = getIntent().getExtras().getInt("parentActivity");
		switch (pa) {
		case 0:
			setTitle(R.string.title_activity_dilution);
			webView.loadUrl("file:///android_asset/help/dilution.html");
			break;
		case 1:
			setTitle(R.string.title_activity_mixing);
			webView.loadUrl("file:///android_asset/help/mixing.html");
			break;
		case 2:
			setTitle(R.string.title_activity_selection);
			webView.loadUrl("file:///android_asset/help/selection.html");
			break;
		case 3:
			setTitle(R.string.title_activity_realheat);
			webView.loadUrl("file:///android_asset/help/realheat.html");
			break;
		case 4:
			setTitle(R.string.title_activity_condenser);
			webView.loadUrl("file:///android_asset/help/condenser.html");
			break;
		case 5:
			setTitle(R.string.title_activity_fruit);
			webView.loadUrl("file:///android_asset/help/fruit.html");
			break;
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        finish();
        return true;
    }
}
