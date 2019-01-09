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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
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
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        client = new OkHttpClient.Builder().build();
        menuList = findViewById(R.id.menu_list);
        menuList.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(this);
        menuList.setAdapter(menuAdapter);
        String cid = getIntent().getExtras().getString("cid");
        compositeDisposable = new CompositeDisposable();
        getCookMenus(cid);
    }

    /**
     * IDE在不知道订阅在未处理的情况下可能产生的影响，它会将其视为不安全
     * 比如 Observable包含一些耗时操作，如果Activity在执行期间被废弃，则可能造成内存泄漏
     * 可以使用CompositeDisposable实例，将所有Disposables添加到CompositeDisposable，
     * 然后在onDestroy使用CompositeDisposable.dispose()
     * https://stackoverflow.com/questions/49522619/the-result-of-subscribe-is-not-used
     */
    private void getCookMenus(String cid) {
        final String url = String.format(COOK_MENU, cid);
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request request = new Request.Builder().url(url).get().build();
                Response response = client.newCall(request).execute();
                e.onNext(response);
                e.onComplete();
            }
        }).map(new Function<Response, CookMenus>() {
            @Override
            public CookMenus apply(Response response) throws Exception {
                try {
                    String res = response.body().string();
                    return new Gson().fromJson(res, CookMenus.class);
                } catch (Exception e) {
//                    throw new RXException(e);
                    throw Exceptions.propagate(e);
                }
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
        /**
         * dispose(): 本质上是说：「我已经不再需要它了，因此我不再需要处理之后的调用了」。
         * 这对于网络请求而言，就是将取消这个网络请求。如果您正在对按钮点击事件监听，
         * 这个操作意味着您不再想去接收按钮点击事件，这会在视图上对监听器执行 onSet 操作。
         */
//                .dispose();
            compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
