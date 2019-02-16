# CmProcess [![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)

更方便更简洁的Android进程通信方案，无需进行bindService()操作，不用定义Service，也不需要定义aidl。
支持IPC级的Callback，并且支持跨进程的事件总线。

## 接入
1. 在全局build里添加仓库
```groovy
    allprojects {
                repositories {
                    ......
                    maven { url 'https://jitpack.io' }
        }
    }
```
2. 在app的build里添加依赖:
```groovy
    dependencies {
        implementation 'com.github.zhangke3016:CmProcess:1.0.3'
    }
```

## 使用

1. 在Application的attachBaseContext方法中初始化:
```
  @Override
  protected void attachBaseContext(Context base) {
      super.attachBaseContext(base);
      VCore.init(base);
  }
```
2. 定义对外提供服务功能的接口和实现，
   如果注册本地服务，参数以及回调接口没有限制；
   如果注册远程服务，参数类型必须为基本数据类型或者可序列化类型(serializable/parcelable),并且异步回调接口需要使用提供的'IPCCallback`。
```
  public interface IPayManager {
     String pay(int count);
     //远程服务调用，异步回调接口需要使用提供的'IPCCallback`。
     String pay(int count, IPCCallback callBack);
  }
```
3. 注册或者反注册服务，可在任意进程调用；注册的进程本地服务需要在本进程取消注册。
```
  //注册本地 + 远程服务
  VCore.getCore().registerService(IPayManager.class, this);
  //取消注册本地 + 远程服务
  VCore.getCore().unregisterService(IPayManager.class);
  
  //注册本地服务
  VCore.getCore().registerLocalService(IPayManager.class, this);
  //取消注册本地服务
  VCore.getCore().unregisterLocalService(IPayManager.class);
```
4. 可在任意进程通过接口类型获取服务调用。
```
  IPayManager service = VCore.getCore().getService(IPayManager.class);
  //获取本地服务
  //IPayManager service = VCore.getCore().getLocalService(IPayManager.class);
  //注意：如果服务未注册时，service为null
  if(service != null){
    //同步调用.
    String message = service.pay(5000);
    
    //异步回调. 推荐使用提供的 BaseCallback 已将回调结果切换至主线程
    service.pay(5000, new BaseCallback() {
         @Override
         public void onSucceed(Bundle result) {
             //Main thread 回调结果在主线程
         }
    
         @Override
         public void onFailed(String reason) {
             //Main thread
         }
     });
     //或者
     service.pay(5000, new IPCCallback.Stub() {
         @Override
         public void onSuccess(Bundle bundle) throws RemoteException {
             // binder thread
         }
         
         @Override
         public void onFail(String s) throws RemoteException {
         
         }
     })
  }
```
5. 事件的发布与订阅
```java
    //订阅事件   可在任意进程的多个位置订阅
    VCore.getCore().subscribe("key", new EventCallback() {
        @Override
        public void onEventCallBack(Bundle event) {
            //main thread
            String name = event.getString("name");
            Log.e("TAG", "onEventCallBack: " + name + " " + (Looper.myLooper() == Looper.getMainLooper()));
        }
    });
    
    //发布事件  发布事件后 多个进程注册的EventCallback会同时回调:
    Bundle bundle = new Bundle();
    bundle.putString("name", "DoDo");
    VCore.getCore().post("key",bundle);
    
    //取消订阅 
    VCore.getCore().unsubscribe("key");
```

