package com.behavior.liberty.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.behavior.liberty.rxjavademo.adapter.CookAdapter;
import com.behavior.liberty.rxjavademo.bean.ItemWrapper;
import com.behavior.liberty.rxjavademo.bean.Menu;
import com.behavior.liberty.rxjavademo.bean.MenuTypes;
import com.google.gson.Gson;
import com.liberty.libertylibrary.adapter.base.OnRecyclerItemClickListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CookActivity extends AppCompatActivity {

    private static final String COOK_MENU = "http://apis.juhe.cn/cook/category?parentid=&dtype=&key=dffa366aa11e868a45b7788c60cf168d";
    private OkHttpClient client;
    private RecyclerView menuList;
    private CookAdapter cookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        client = new OkHttpClient.Builder().build();
        menuList = findViewById(R.id.menu_list);
        cookAdapter = new CookAdapter(this);
        menuList.setLayoutManager(new LinearLayoutManager(this));
        menuList.setAdapter(cookAdapter);
        getCookMenu();
        menuList.addOnItemTouchListener(new OnRecyclerItemClickListener(menuList) {
            @Override
            public void onItemClick(int position, RecyclerView.ViewHolder holder) {
                super.onItemClick(position, holder);
                ItemWrapper item = cookAdapter.getItem(position);
                if (item instanceof Menu) {
                    Menu menu = (Menu) item;
                    Intent intent = new Intent(CookActivity.this, MenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cid", menu.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
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
        }).map(new Function<Response, MenuTypes>() {
            @Override
            public MenuTypes apply(Response response) throws Exception {
                return new Gson().fromJson(response.body().string(), MenuTypes.class);
            }
        }).map(new Function<MenuTypes, List<MenuTypes.MenuType>>() {
            @Override
            public List<MenuTypes.MenuType> apply(MenuTypes menuTypes) throws Exception {
                return menuTypes.getResult();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MenuTypes.MenuType>>() {
                    @Override
                    public void accept(List<MenuTypes.MenuType> menuTypes) throws Exception {
                        cookAdapter.flatArray(menuTypes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

}
