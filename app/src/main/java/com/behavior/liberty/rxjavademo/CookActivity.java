package com.behavior.liberty.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CookActivity extends AppCompatActivity {

    private static final String COOK_MENU = "http://apis.juhe.cn/cook/category?parentid=&dtype=&key=dffa366aa11e868a45b7788c60cf168d";
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        client = new OkHttpClient.Builder().build();
    }

    private void getCookMenu() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request.Builder builder = new Request.Builder().url(COOK_MENU).get();
                Request request = builder.build();
                Call call = client.newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map();
    }

}
