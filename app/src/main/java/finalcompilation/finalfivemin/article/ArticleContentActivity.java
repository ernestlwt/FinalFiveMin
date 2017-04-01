package finalcompilation.finalfivemin.article;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import finalcompilation.finalfivemin.R;

public class ArticleContentActivity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.article_webview);
        wv = (WebView) findViewById(R.id.article_webview);
        wv.setWebViewClient(new WebViewClient());
        wv .getSettings().setJavaScriptEnabled(true);
        wv .getSettings().setDomStorageEnabled(true);
        Bundle bundle = getIntent().getExtras();
        wv.loadUrl(bundle.getString("link"));


    }
}
