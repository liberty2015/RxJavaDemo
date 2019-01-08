package com.behavior.liberty.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.behavior.liberty.rxjavademo.adapter.MenuAdapter;
import com.behavior.liberty.rxjavademo.bean.CookMenu;
import com.behavior.liberty.rxjavademo.bean.CookMenus;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuActivity extends AppCompatActivity {

    private static final String COOK_MENU = "http://apis.juhe.cn/cook/index?cid=%s&dtype=&pn=&rn=&format=&key=dffa366aa11e868a45b7788c60cf168d";
    private OkHttpClient client;
    private RecyclerView menuList;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        client = new OkHttpClient.Builder().build();
        menuList = findViewById(R.id.menu_list);
        menuList.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(this);

        String cid = getIntent().getExtras().getString("cid");
        getCookMenus(cid);
    }

    private void getCookMenus(String cid) {
        final String url = String.format(COOK_MENU, cid);
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request request = new Request.Builder().url(url).get().build();
                Response response = client.newCall(request).execute();
                e.onNext(response);
            }
        }).map(new Function<Response, CookMenus>() {
            @Override
            public CookMenus apply(Response response) throws Exception {
                return new Gson().fromJson(response.body().string(), CookMenus.class);
            }
        }).map(new Function<CookMenus, List<CookMenu>>() {
            @Override
            public List<CookMenu> apply(CookMenus cookMenus) throws Exception {
                return cookMenus.getResult().getData();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CookMenu>>() {
                    @Override
                    public void accept(List<CookMenu> cookMenus) throws Exception {
                        menuAdapter.addAll(cookMenus);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
