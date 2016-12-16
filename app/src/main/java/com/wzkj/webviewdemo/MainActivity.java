package com.wzkj.webviewdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String url = null;
    private WebView webView;
    private EditText et_url;
    private Button btn_login;
    private Button btn_back;
    private Button btn_exit;
    private Button btn_forward;
    private Button btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        setProgressBarIndeterminate(true);
        Log.i("tag", String.valueOf(Environment.
                getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)));
        Log.i("tag",Environment.getExternalStorageDirectory().getPath()+"=====");
        init();
    }

    private void init() {

        //发现组件
        webView = (WebView) findViewById(R.id.webView);
        et_url = (EditText) findViewById(R.id.et_url);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_forward = (Button) findViewById(R.id.btn_forward);
        btn_menu = (Button) findViewById(R.id.btn_menu);

        // 对五个按钮添加点击监听事件
        btn_login.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_forward.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
    }

    public void startReadUrl(String url) {
        // webView加载web资源
        webView.loadUrl(url);

        // 覆盖webView默认通过系统或者第三方浏览器打开网页的行为
        // 如果为false调用系统或者第三方浏览器打开网页的行为
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // webView加载web资源
                view.loadUrl(url);
                return true;
            }
        });
        // 启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // web加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // 当打开页面时 显示进度条 页面打开完全时 隐藏进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            //http://news.sina.com.cn/
            public void onProgressChanged(WebView view, int newProgress) {
// TODO Auto-generated method stub
                setTitle("本页面已加载" + newProgress + "%");
                if (newProgress == 100) {
                    closeProgressBar();
                } else {
                    openProgressBar(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    // 打开进度条
    protected void openProgressBar(int x) {
// TODO Auto-generated method stub
        setProgressBarIndeterminateVisibility(true);
        setProgress(x);
    }

    // 关闭进度条
    protected void closeProgressBar() {
// TODO Auto-generated method stub
        setProgressBarIndeterminateVisibility(false);
    }

    // 改写物理按键 返回键的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
// 返回上一页面
                webView.goBack();
                return true;
            } else {
// 退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                url = "http://" + et_url.getText().toString();
                url = url.replace(" ", "");
                //加载url
                startReadUrl(url);
                break;
            case R.id.btn_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.btn_forward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_menu:
                startReadUrl("https://www.taobao.com/");
                break;
        }
    }
}



