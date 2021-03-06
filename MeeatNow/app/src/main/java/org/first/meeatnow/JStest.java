package org.first.meeatnow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JStest extends AppCompatActivity {
    WebView webView;
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jstest);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        // 자바스크립트인터페이스 연결
        // 이걸 통해 자바스크립트 내에서 자바함수에 접근할 수 있음.
        webView.addJavascriptInterface(new MyJavascriptInterface(), "Android");
        // 페이지가 모두 로드되었을 때, 작업 정의
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 자바스크립트 인터페이스로 연결되어 있는 getHTML를 실행
                // 자바스크립트 기본 메소드로 html 소스를 통째로 지정해서 인자로 넘김
                //view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);");
                view.loadUrl("javascript:window.load;");
            }
        });
        //지정한 URL을 웹 뷰로 접근하기
        webView.loadUrl("https://m.map.naver.com/search2/search.nhn?query=%EC%B9%98%ED%82%A8&siteLocation=&queryRank=&type=");
    }

    public class MyJavascriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            //위 자바스크립트가 호출되면 여기로 html이 반환됨
            source = html;
            Log.e("TATAT", source);
        }
    }
    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("https://m.map.naver.com/search2/search.nhn?query=%EC%B9%98%ED%82%A8&siteLocation=&queryRank=&type=").get();
                String url = doc.title();
                String urer = doc.baseUri();
                Log.e("TATTa", doc.toString());
                Log.e("TATTb", url);
                Log.e("TATTc", urer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //result.setText(a);

        }
    }
}
