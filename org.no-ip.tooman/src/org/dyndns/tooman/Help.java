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
			setTitle(R.string.title_activity_fruit);
			webView.loadUrl("file:///android_asset/help/fruit.html");
			break;
		case 1:
			setTitle(R.string.title_activity_realheat);
			webView.loadUrl("file:///android_asset/help/realheat.html");
			break;
		case 2:
			setTitle(R.string.title_activity_selection);
			webView.loadUrl("file:///android_asset/help/selection.html");
			break;
		case 3:
			setTitle(R.string.title_activity_steam);
			webView.loadUrl("file:///android_asset/help/steam.html");
			break;
		case 4:
			setTitle(R.string.title_activity_density);
			webView.loadUrl("file:///android_asset/help/density.html");
			break;
		case 5:
			setTitle(R.string.title_activity_dilution);
			webView.loadUrl("file:///android_asset/help/dilution.html");
			break;
		case 6:
			setTitle(R.string.title_activity_mixing);
			webView.loadUrl("file:///android_asset/help/mixing.html");
			break;
		case 7:
			setTitle(R.string.title_activity_condenser);
			webView.loadUrl("file:///android_asset/help/condenser.html");
			break;
		case 8:
			setTitle(R.string.title_activity_wastage);
			webView.loadUrl("file:///android_asset/help/wastage.html");
			break;
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        finish();
        return true;
    }
}
