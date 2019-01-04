package com.behavior.liberty.rxjavademo;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
        //single demo
        Log.e(TAG,"single demo");
        demoSingle();
        //distinct demo
        Log.e(TAG,"distinct demo");
        demoDistinct();
        //debounce demo
//        Log.e(TAG,"debounce demo");
//        demoDebounce();
        //defer demo
        Log.e(TAG,"defer demo");
        demoDefer();
        //last demo
        Log.e(TAG,"last demo");
        demoLast();
        //merge demo
        Log.e(TAG,"merge demo");
        demoMerge();
        //reduce demo
        Log.e(TAG,"reduce demo");
        demoReduce();
        //scan demo
        Log.e(TAG,"scan demo");
        demoScan();
        //distinct demo
        Log.e(TAG, "distinct demo");
        demoDistinct();
        //filter demo
        Log.e(TAG, "filter demo");
        demoFilter();
        //buffer demo
        Log.e(TAG, "buffer demo");
        demoBuffer();
        //timer demo
//        Log.e(TAG, "timer demo");
//        demoTimer();
        //interval demo
//        Log.e(TAG, "interval demo");
//        demoInterval();
        //skip demo
        Log.e(TAG, "skip demo");
        demoSkip();
        //take demo
        Log.e(TAG, "take demo");
        demoTake();
        //just demo
        Log.e(TAG, "just demo");
        demoJust();
        //window demo
        Log.e(TAG,"window demo");
        demoWindow();
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
     * flatMap：将一个Observable通过某种方法转换为多个Observable，然后将这些分散的Observable合并到一个单一的Observable
     * flatMap不能保证事件的顺序
     * 使用场景：化解循环嵌套；连续请求两个接口，第一个接口的返回值是第二个接口的请求参数
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
     * concatMap：和flatMap相似，区别在于concatMap用的时链接，保证了顺序
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

    /**
     *
     * single：只会接受一个参数，而SingleObserver只会调用onError()或者onSuccess()
     */
    private void demoSingle() {
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.e(TAG, "single : onSuccess : " + integer + "\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "single : onError : " + e.getMessage() + "\n");
                    }
                });
    }

    /**
     * distinct：去重操作符
     */
    private void demoDistinct() {
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "distinct : " + integer + "\n");
                    }
                });
    }

    /**
     * filter：过滤器
     */
    private void demoFilter() {
        Observable.just(1, 20, 65, -5, 7, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "filter: " + integer + "\n");
            }
        });
    }

    /**
     * buffer：buffer操作符接受两个参数：count和skip，用于将Observable中的数据按skip(步长)分成最大不超过count的buffer，
     * 然后生成一个Observable
     */
    private void demoBuffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.e(TAG, "buffer size: " + integers.size() + "\n");
                        Log.e(TAG, "buffer value: ");
                        for (Integer i :
                                integers) {
                            Log.e(TAG, i + "");
                        }
                        Log.e(TAG, "\n");
                    }
                });
    }

    /**
     * debounce：去除发送间隔时间小于指定时间的发射事件
     */
    private void demoDebounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(400);
                e.onNext(2);
                Thread.sleep(505);
                e.onNext(3);
                Thread.sleep(100);
                e.onNext(4);
                Thread.sleep(605);
                e.onNext(5);
                Thread.sleep(510);
                e.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "debouncd : " + Thread.currentThread().toString());
                        Log.e(TAG, "debounce : " + integer + "\n");
                        }
                });

    }

    /**
     * timer：定时任务，延迟指定时间后执行操作，默认在新线程
     */
    private void demoTimer() {
        Log.e(TAG, "timer start: " + new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.CHINA).format(new Date()));
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "timer: " + aLong + " at " +
                                new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.CHINA).format(new Date()));
                    }
                });
    }

    /**
     * defer：？
     */
    private void demoDefer() {
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "defer : " + integer + "\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "defer : onError : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "defer : onComplete\n");
            }
        });
    }

    /**
     * last：只取出可观察到的最后一个值或者满足条件的最后一项
     */
    private void demoLast() {
        //last的参数表示当源为空的时候默认发射的数据
//        Observable.<Integer>empty()
//                .last(4)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG,"last : "+integer+"\n");
//                    }
//                });

        Observable.just(1, 2, 3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "last : " + integer + "\n");
                    }
                });
    }

    /**
     * interval：定时器，接受三个参数：第一次延迟、间隔时间、时间单位
     */
    private void demoInterval() {
        Log.e(TAG, "interval start: " + new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.CHINA).format(new Date()));
        Observable.interval(3, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "interval :" + aLong + " at " + new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.CHINA).format(new Date()));
                    }
                });
    }

    /**
     * skip：接受一个long型的参数count，表示跳过count个数目开始接收
     */
    private void demoSkip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "skip: " + integer + "\n");
                    }
                });
    }


    /**
     * merge：将多个Observable结合起来，和concat的区别在于，不需要等到发射器A发送完所有时间再进行发射器B的发送
     */
    private void demoMerge() {
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "merge: accept: " + integer + "\n");
                    }
                });
    }

    /**
     * take：接受一个long型的参数count，表示最多接收count个数据
     */
    private void demoTake() {
        Flowable.fromArray(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "take: " + integer + "\n");
                    }
                });
    }


    /**
     * reduce：类似js的array的reduce方法，将每个数据累加起来，最终返回一个值
     */
    private void demoReduce() {
        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "reduce : accept : " + integer + "\n");
            }
        });
    }

    /**
     * scan：和reduce类似，区别在于reduce只输出最终结果，scan会在每次累加的时候调用accept
     */
    private void demoScan() {
        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                });
    }

    /**
     * just：将传入的数据转换为发射该数据的Observable，支持传入单个或多个数据
     */
    private void demoJust() {
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: onNext: " + s + "\n");
                    }
                });
    }


    private void demoDoOnNext() {
        Observable.just(1, 2, 3, 4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "doOnNext 保存 " + integer + "");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "scan : accept : " + integer + "\n");
                Log.e(TAG, "");
            }
        });
    }


    /**
     * ？
     */
    private void demoWindow(){
        Observable.interval(1,TimeUnit.SECONDS)
                .take(15)
                .window(3,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {
                        Log.e(TAG,"Window Sub Divide begin...\n");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.e(TAG,"Window Next: "+aLong+"\n");
                                    }
                                });
                    }
                });
    }
}
