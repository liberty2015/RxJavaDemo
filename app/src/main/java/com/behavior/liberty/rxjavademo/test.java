package com.behavior.liberty.rxjavademo;


/**
 * Created by liberty on 2018/2/25.
 */

public class test {
    public static void main(String[] args) {

//        Observable.create(new ObservableOnSubscribe<Drawable>() {
//            @Override
//            public void subscribe(ObservableEmitter<Drawable> e) throws Exception {
//
//            }
//        })
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Consumer<Drawable>() {
//            @Override
//            public void accept(Drawable drawable) throws Exception {
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//
//            }
//        });
//
//        Observable.just("Hello world").subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                System.out.println("Observable emit 1");
//                e.onNext(1);
//                System.out.println("Observable emit 2");
//                e.onNext(2);
//                System.out.println("Observable emit 3");
//                e.onNext(3);
//                e.onComplete();
//                System.out.println("Observable emit 4");
//                e.onNext(4);
//            }
//        }).subscribe(new Observer<Integer>() {
//
//            private int i;
//            private Disposable mDisposable;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                mDisposable=d;
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                i++;
//                if (i==2){
//                    mDisposable.dispose();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError: value: "+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("onComplete");
//            }
//        });
//        SomeType instance=new SomeType();
//        Observable<String> stringObservable = instance.valueObservable();
//        instance.setValue("Some Value");
//        stringObservable.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });
    }

//    static class SomeType{
//        private String value;
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//
//        public Observable<String> valueObservable(){
//            return Observable.just(value);
//        }
//    }
}
