package com.behavior.liberty.rxjavademo;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);

        //create demo
        Log.e(TAG, "create demo");
        demoCreate();
        //map demo
        Log.e(TAG, "map demo");
        demoMap();
        //zip demo
        Log.e(TAG, "zip demo");
        demoZip();
        //concat demo
        Log.e(TAG, "concat demo");
        demoConcat();
        //flatMap demo
        Log.e(TAG, "flatMap demo");
        demoFlatMap();
        //concatMap demo
        Log.e(TAG, "concatMap demo");
        demoConcatMap();

    }

    public void click(View view) {
        Observable<String> observable = getObservable();
//        Observer<String > observer = getObserver();
//        observable.subscribe(observer);

        //简化写法
        observable.subscribe(
                //这里的Consumer相当于onNext
                new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }
//                ,
//                //这里的Consumer相当于onError
//                new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                },
//                //这里的Action相当于onComplete
//                new Action() {
//                    @Override
//                    public void run() throws Exception {
//
//                    }
//                },
//                //这里的Consumer相当于onSubscribe
//                new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//
//                    }
//                }
        );
    }

    int drawableRes = R.mipmap.img;

    public void clickGetImg(View view) {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Drawable> e) throws Exception {
                Drawable drawable = getResources().getDrawable(drawableRes);
                e.onNext(drawable);
                e.onComplete();
            }
        })
//        .subscribe(new Observer<Drawable>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull Drawable drawable) {
//                img.setImageDrawable(drawable);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        img.setImageDrawable(drawable);
                    }
                });
    }

    public Observable<String> getObservable() {
//        return Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//                e.onNext("");
//                e.onNext("");
//                e.onComplete();
////                e.onError(new IllegalStateException());
//            }
//        });
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        Observable.fromArray("", "", "");
        return Observable.just("", "", "", "");
    }

    public Observer<String> getObserver() {

        return new Observer<String>() {
            Disposable dd = null;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //判断是否订阅
                dd = d;
                Log.d(TAG, "onSubscribe:" + d.isDisposed());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext:" + s);
                if (dd.isDisposed()) {
                    dd.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError:" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }

    //------------------------------------------------------------------------------------------------

    /**
     * create：用于产生一个Observable被观察者
     */
    private void demoCreate() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                Log.e(TAG, "Observable emit 4" + "\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe:" + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: value:" + integer + "\n");
                i++;
                if (i == 2) {
                    mDisposable.dispose();
                    Log.e(TAG, "onNext:isDisposable:" + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError:value:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete" + "\n");
            }
        });
    }

    /**
     * map:对发射的每一个事件应用一个函数，使得每个事件按照指定的函数变化
     */
    private void demoMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept:" + s + "\n");
            }
        });
    }

    /**
     * zip：用于合并事件，该合并不是连接，而是两两配对，从各个发射器中取出一个事件来组合，
     * 并且每个事件只能被使用一次，组合的顺序是严格按照事件发送的顺序来进行的。
     * 最终接收器收到的事件数量和发送事件最少的发送器发送事件数目相同
     */
    private void demoZip() {
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "zip: accept:" + s + "\n");
            }
        });
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    Log.e(TAG, "String emit: A \n");
                    e.onNext("B");
                    Log.e(TAG, "String emit: B \n");
                    e.onNext("C");
                    Log.e(TAG, "String emit: C \n");
                }
            }
        });
    }

    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    Log.e(TAG, "Integer emit: 1 \n");
                    e.onNext(2);
                    Log.e(TAG, "Integer emit: 2 \n");
                    e.onNext(3);
                    Log.e(TAG, "Integer emit: 3 \n");
                    e.onNext(4);
                    Log.e(TAG, "Integer emit: 4 \n");
                    e.onNext(5);
                    Log.e(TAG, "Integer emit: 5 \n");
                }
            }
        });
    }

    /**
     * concat：连接多个Observable的输出，使它们像单个Observable一样，
     * 第一个Observable发出的所有项在第二个Observable发出的项之前发出
     */
    private void demoConcat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "concat: " + integer + "\n");
                    }
                });
    }

    /**
     * flatMap：
     */
    private void demoFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "flatMap: accept: " + s + " \n");
                    }
                });
    }

    /**
     * concatMap：
     */
    private void demoConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "concatMap: accept: " + s + " \n");
                    }
                });
    }

}